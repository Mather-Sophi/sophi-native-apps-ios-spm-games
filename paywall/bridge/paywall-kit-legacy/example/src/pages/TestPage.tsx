import React, { useState, useRef, useEffect } from 'react';
import {
  View,
  ScrollView,
  Button,
  Text,
  TouchableOpacity,
  ActivityIndicator,
  Platform,
} from 'react-native';
import {
  PaywallDeciderRepository,
  type UserDimensions,
  type VisitorType,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import { MockUserDimensionRepository, MockDeviceDimensionRepository } from '../repositories/MockRepositories';
import { styles } from '../styles';
import { generateTestScenarios } from '../tests/testScenarios';
import { type TestResult } from '../tests/testRunner';

interface TestPageProps {
  onNavigateBack: () => void;
}

export const TestPage: React.FC<TestPageProps> = ({ onNavigateBack }) => {
  const [testResults, setTestResults] = useState<TestResult[]>([]);
  const [isRunning, setIsRunning] = useState(false);
  const [expandedResults, setExpandedResults] = useState<Record<number, boolean>>({});

  // Shared repository instances
  const userRepoRef = useRef<MockUserDimensionRepository | null>(null);
  const deviceRepoRef = useRef<MockDeviceDimensionRepository | null>(null);
  const deciderRepoRef = useRef<PaywallDeciderRepository | null>(null);

  // Initialize shared repositories once
  useEffect(() => {
    // Create shared repositories
    userRepoRef.current = new MockUserDimensionRepository();
    deviceRepoRef.current = new MockDeviceDimensionRepository();

    try {
      deciderRepoRef.current = PaywallDeciderRepository.createNew(
        userRepoRef.current,
        deviceRepoRef.current
      );
    } catch (error) {
      console.error('Failed to initialize PaywallDeciderRepository:', error);
    }
  }, []);

  const runTests = async () => {
    if (!deciderRepoRef.current) {
      setTestResults([
        {
          testName: 'Initialization',
          passed: false,
          duration: 0,
          error: 'PaywallDeciderRepository not initialized',
        },
      ]);
      return;
    }

    setIsRunning(true);
    setTestResults([]);

    const scenarios = generateTestScenarios();
    const results: TestResult[] = [];

    let decider;
    try {
      decider = await deciderRepoRef.current.getOneByHost('www.statesman.com', {
        apiTimeoutInMilliSeconds: '500',
        onDeviceModelRepositoryUrl:
          Platform.OS === 'android'
            ? 'http://10.0.2.2:8000'
            : 'http://localhost:8000',
      });
    } catch (error) {
      setTestResults([
        {
          testName: 'Decider Initialization',
          passed: false,
          duration: 0,
          error: `Failed to get decider: ${String(error)}`,
        },
      ]);
      setIsRunning(false);
      return;
    }

    // Run tests sequentially
    for (let i = 0; i < scenarios.length; i++) {
      const scenario = scenarios[i]!;
      const startTime = Date.now();

      try {
        // Update dimensions using the repository's update method
        if (userRepoRef.current) {
          userRepoRef.current.update({
            ...scenario.userDimensions,
            visitorType: scenario.visitorType,
            visitor: {
              session: {
                campaignName: 'TestCampaign',
                referrerDomain: 'example.com',
                referrerMedium: scenario.referrerMedium,
                referrerSource: scenario.referrerSource,
                referrerChannel: scenario.referrerChannel,
                timestamp: new Date().toISOString(),
              },
            },
            referrerData: {
              medium: scenario.referrerMedium,
              source: scenario.referrerSource,
              channel: scenario.referrerChannel,
              utmParameters: {
                utm_source: scenario.referrerSource,
                utm_medium: scenario.referrerMedium,
                utm_campaign: 'TestCampaign',
              },
            },
          });
        }

        const decision = await decider.decide(
          `test-content`,
          'control',
          undefined,
          undefined
        );

        const duration = Date.now() - startTime;

        // Validate encoded code mappings in context and inputs
        const contextStr = decision?.context || '';
        const inputsStr = decision?.inputs || '';
        
        let codeMappingValid = true;
        let missingCodes: string[] = [];
        
        if (scenario.expectedMappings) {
          const mappings = scenario.expectedMappings;
          
          // Check context codes
          if (mappings.contextCodes) {
            mappings.contextCodes.forEach(code => {
              if (!contextStr.includes(code)) {
                missingCodes.push(`[context]${code}`);
              }
            });
          }
          
          // Check input codes
          if (mappings.inputCodes) {
            mappings.inputCodes.forEach(code => {
              if (!inputsStr.includes(code)) {
                missingCodes.push(`[inputs]${code}`);
              }
            });
          }
          
          if (missingCodes.length > 0) {
            codeMappingValid = false;
          }
        }

        const error = missingCodes.length > 0 ? `Missing codes: ${missingCodes.join(', ')}` : undefined;

        results.push({
          testName: scenario.name,
          passed: !!decision && !!decision.outcome && codeMappingValid,
          duration,
          decision,
          error,
        });
      } catch (error) {
        const duration = Date.now() - startTime;
        results.push({
          testName: scenario.name,
          passed: false,
          duration,
          error: String(error),
        });
      }

      // Update UI with results so far
      setTestResults([...results]);
    }

    setIsRunning(false);
  };

  const passedCount = testResults.filter((r) => r.passed).length;
  const failedCount = testResults.filter((r) => !r.passed).length;
  const totalTests = testResults.length;

  return (
    <ScrollView style={styles.container}>
      <View style={styles.section}>
        <Text style={styles.title}>Paywall Decision Test Suite</Text>
        <Text style={styles.subtitle}>
          Test decision logic with all enum combinations and partial datasets
        </Text>
      </View>

      <View style={styles.section}>
        <Button
          title={isRunning ? 'Running Tests...' : 'Run All Tests'}
          onPress={runTests}
          disabled={isRunning}
        />
        {isRunning && (
          <View style={{ marginTop: 16, alignItems: 'center' }}>
            <ActivityIndicator size="large" color="#0066cc" />
            <Text style={{ marginTop: 8 }}>
              Running test {Math.min(testResults.length + 1, generateTestScenarios().length)} of{' '}
              {generateTestScenarios().length}...
            </Text>
          </View>
        )}
      </View>

      {totalTests > 0 && (
        <>
          <View style={styles.section}>
            <View style={styles.summaryBox}>
              <Text style={styles.summaryTitle}>Test Summary</Text>
              <Text style={[styles.summaryText, { color: '#22c55e' }]}>
                ✅ Passed: {passedCount}
              </Text>
              <Text style={[styles.summaryText, { color: '#ef4444' }]}>
                ❌ Failed: {failedCount}
              </Text>
              <Text style={styles.summaryText}>Total: {totalTests}</Text>
              <Text style={styles.summaryText}>
                Success Rate: {totalTests > 0 ? ((passedCount / totalTests) * 100).toFixed(1) : 0}%
              </Text>
            </View>
          </View>

          <View style={styles.section}>
            <Text style={styles.sectionTitle}>Test Results:</Text>
            {testResults.map((result, index) => (
              <TouchableOpacity
                key={index}
                style={[
                  styles.testResultCard,
                  result.passed
                    ? { borderLeftColor: '#22c55e' }
                    : { borderLeftColor: '#ef4444' },
                ]}
                onPress={() =>
                  setExpandedResults((prev) => ({
                    ...prev,
                    [index]: !prev[index],
                  }))
                }
              >
                <View style={styles.testResultHeader}>
                  <Text style={styles.testResultTitle}>
                    {result.passed ? '✅' : '❌'} {result.testName}
                  </Text>
                  <Text style={styles.testResultDuration}>{result.duration}ms</Text>
                </View>
                {expandedResults[index] && (
                  <View style={styles.testResultDetails}>
                    {result.error && (
                      <Text style={styles.testResultError}>Error: {result.error}</Text>
                    )}
                    {result.decision && (
                      <>
                        <Text style={styles.testResultDetail}>
                          Wall Type: {result.decision.outcome.wallType || 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Visibility: {result.decision.outcome.wallVisibility}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Trace: {result.decision.trace || 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Context: {result.decision.context || 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Inputs: {result.decision.inputs || 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Experiment Code: {result.decision.experimentCode || 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Paywall Score: {result.decision.paywallScore ?? 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          User Properties: {result.decision.userProperties || 'N/A'}
                        </Text>
                        <Text style={styles.testResultDetail}>
                          Content Properties: {result.decision.contentProperties || 'N/A'}
                        </Text>
                      </>
                    )}
                  </View>
                )}
              </TouchableOpacity>
            ))}
          </View>
        </>
      )}

      {onNavigateBack && (
        <View style={styles.section}>
          <Button title="Back to Main" onPress={onNavigateBack} />
        </View>
      )}

      <View style={styles.buttonSpacer} />
    </ScrollView>
  );
};
