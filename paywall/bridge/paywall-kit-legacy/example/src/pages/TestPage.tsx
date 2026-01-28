import React, { useState, useRef, useEffect } from 'react';
import {
  View,
  ScrollView,
  Button,
  Text,
  TouchableOpacity,
  ActivityIndicator,
} from 'react-native';
import {
  PaywallDeciderRepository,
  type ReferrerMedium,
  type ReferrerSource,
  type ReferrerChannel,
  type VisitorType,
  type UserDimensions,
  type WallDecision,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import { Platform } from 'react-native';
import { MockUserDimensionRepository, MockDeviceDimensionRepository } from '../repositories/MockRepositories';
import { styles } from '../styles';
import { expectedCodesForScenario, type ScenarioLike } from '../utils/codeMappings';

export interface TestResult {
  testName: string;
  passed: boolean;
  duration: number;
  error?: string;
  decision?: WallDecision;
}

const VISITOR_TYPES: VisitorType[] = ['anonymous', 'registered'];
const REFERRER_MEDIUMS: ReferrerMedium[] = [
  'campaign',
  'direct',
  'internal',
  'search',
  'social',
  'other',
];
const REFERRER_SOURCES: ReferrerSource[] = [
  'google',
  'yahoo',
  'duckduckgo',
  'bing',
  'facebook',
  'instagram',
  'x',
  't',
  'linkedin',
  'reddit',
  'newsletter',
];
const REFERRER_CHANNELS: ReferrerChannel[] = ['search', 'news', 'discover'];

interface TestScenario {
  name: string;
  visitorType: VisitorType;
  referrerMedium: ReferrerMedium;
  referrerSource: ReferrerSource;
  referrerChannel: ReferrerChannel;
  userDimensions: Partial<UserDimensions>;
  expectedMappings?: {
    contextCodes?: string[];  // e.g., ['AA05', 'BA25', 'DA00']
    inputCodes?: string[];    // e.g., ['FA:anonymous', 'DE:search', 'EA:ios']
  };
}

export function generateTestScenarios(): TestScenario[] {
  const scenarios: TestScenario[] = [];

  const pushScenario = (base: Omit<TestScenario, 'expectedMappings'>) => {
    const scenario: TestScenario = {
      ...base,
      expectedMappings: expectedCodesForScenario({
        userDimensions: base.userDimensions,
        visitorType: base.visitorType,
        referrerMedium: base.referrerMedium,
        referrerSource: base.referrerSource,
        referrerChannel: base.referrerChannel,
      } as ScenarioLike, { inputFormat: 'index' }),
    };
    scenarios.push(scenario);
  };

  // Test each VisitorType once
  VISITOR_TYPES.forEach((visitorType) => {
    pushScenario({
      name: `Enum Test: VisitorType = ${visitorType}`,
      visitorType,
      referrerMedium: 'search',
      referrerSource: 'google',
      referrerChannel: 'search',
      userDimensions: {
        todayPageViews: 5,
        todayPageViewsByArticle: 3,
        sevenDayPageViews: 5,
        twentyEightDayPageViews: 10,
        daysSinceLastVisit: 0,
        visitorType,
        timeZone: 'America/New_York',
        referrer: 'https://example.com',
        countryCode: 'US',
      },
    });
  });

  // Test each ReferrerMedium once
  REFERRER_MEDIUMS.forEach((medium) => {
    pushScenario({
      name: `Enum Test: ReferrerMedium = ${medium}`,
      visitorType: 'anonymous',
      referrerMedium: medium,
      referrerSource: 'google',
      referrerChannel: 'search',
      userDimensions: {
        todayPageViews: 5,
        todayPageViewsByArticle: 3,
        sevenDayPageViews: 5,
        twentyEightDayPageViews: 10,
        daysSinceLastVisit: 0,
        visitorType: 'anonymous',
        timeZone: 'America/New_York',
        referrer: 'https://example.com',
        countryCode: 'US',
      },
    });
  });

  // Test each ReferrerSource once
  REFERRER_SOURCES.forEach((source) => {
    pushScenario({
      name: `Enum Test: ReferrerSource = ${source}`,
      visitorType: 'anonymous',
      referrerMedium: 'search',
      referrerSource: source,
      referrerChannel: 'search',
      userDimensions: {
        todayPageViews: 5,
        todayPageViewsByArticle: 3,
        sevenDayPageViews: 5,
        twentyEightDayPageViews: 10,
        daysSinceLastVisit: 0,
        visitorType: 'anonymous',
        timeZone: 'America/New_York',
        referrer: 'https://example.com',
        countryCode: 'US',
      },
    });
  });

  // Test each ReferrerChannel once
  REFERRER_CHANNELS.forEach((channel) => {
    pushScenario({
      name: `Enum Test: ReferrerChannel = ${channel}`,
      visitorType: 'anonymous',
      referrerMedium: 'search',
      referrerSource: 'google',
      referrerChannel: channel,
      userDimensions: {
        todayPageViews: 5,
        todayPageViewsByArticle: 3,
        sevenDayPageViews: 5,
        twentyEightDayPageViews: 10,
        daysSinceLastVisit: 0,
        visitorType: 'anonymous',
        timeZone: 'America/New_York',
        referrer: 'https://example.com',
        countryCode: 'US',
      },
    });
  });

  // Partial data scenarios - Testing with incomplete datasets
  const partialDataScenarios = [
    {
      name: 'Partial: Minimal Page Views (Today only)',
      visitorType: 'anonymous' as VisitorType,
      referrerMedium: 'search' as ReferrerMedium,
      referrerSource: 'google' as ReferrerSource,
      referrerChannel: 'search' as ReferrerChannel,
      userDimensions: {
        todayPageViews: 1,
        daysSinceLastVisit: 0,
        visitorType: 'anonymous' as VisitorType,
      },
    },
    {
      name: 'Partial: Today + 7 Day metrics only',
      visitorType: 'registered' as VisitorType,
      referrerMedium: 'direct' as ReferrerMedium,
      referrerSource: 'facebook' as ReferrerSource,
      referrerChannel: 'news' as ReferrerChannel,
      userDimensions: {
        todayPageViews: 5,
        todayPageViewsByArticle: 3,
        sevenDayPageViews: 5,
        sevenDayPageViewsByArticle: 15,
        sevenDayVisitCount: 5,
        daysSinceLastVisit: 2,
        visitorType: 'registered' as VisitorType,
      },
    },
    {
      name: 'Partial: Zero-filled dimensions',
      visitorType: 'anonymous' as VisitorType,
      referrerMedium: 'social' as ReferrerMedium,
      referrerSource: 'instagram' as ReferrerSource,
      referrerChannel: 'discover' as ReferrerChannel,
      userDimensions: {
        todayPageViews: 0,
        todayPageViewsByArticle: 0,
        sevenDayPageViews: 0,
        sevenDayPageViewsByArticle: 0,
        twentyEightDayPageViews: 0,
        daysSinceLastVisit: 30,
        visitorType: 'anonymous' as VisitorType,
      },
    },
    {
      name: 'Partial: High-value metrics',
      visitorType: 'registered' as VisitorType,
      referrerMedium: 'campaign' as ReferrerMedium,
      referrerSource: 'newsletter' as ReferrerSource,
      referrerChannel: 'news' as ReferrerChannel,
      userDimensions: {
        todayPageViews: 50,
        todayPageViewsByArticle: 30,
        todayPageViewsByArticleWithPaywall: 15,
        sevenDayPageViews: 200,
        sevenDayPageViewsByArticle: 120,
        sevenDayPageViewsByArticleWithPaywall: 50,
        twentyEightDayPageViews: 500,
        twentyEightDayPageViewsByArticle: 300,
        twentyEightDayPageViewsByArticleWithPaywall: 100,
        daysSinceLastVisit: 0,
        visitorType: 'registered' as VisitorType,
      },
    },
  ];

  partialDataScenarios.forEach((base) => pushScenario(base as Omit<TestScenario, 'expectedMappings'>));

  return scenarios;
}

interface TestPageProps {
  onNavigateBack?: () => void;
}

export const TestPage: React.FC<TestPageProps> = ({ onNavigateBack }) => {
  const [testResults, setTestResults] = useState<TestResult[]>([]);
  const [isRunning, setIsRunning] = useState(false);
  const [expandedResults, setExpandedResults] = useState<Record<number, boolean>>({});

  // Shared repository instances
  const latestUserDimensionsRef = useRef<Partial<UserDimensions>>({});
  const userRepoRef = useRef<MockUserDimensionRepository | null>(null);
  const deviceRepoRef = useRef<MockDeviceDimensionRepository | null>(null);
  const deciderRepoRef = useRef<PaywallDeciderRepository | null>(null);

  // Initialize shared repositories once
  useEffect(() => {
    const defaultDimensions: Partial<UserDimensions> = {
      todayPageViews: 0,
      todayPageViewsByArticle: 0,
      todayPageViewsByArticleWithPaywall: 0,
      todayPageViewsByArticleWithRegwall: 0,
      todayTopLevelSections: 0,
      todayTopLevelSectionsByArticle: 0,
      sevenDayPageViews: 0,
      sevenDayPageViewsByArticle: 0,
      sevenDayPageViewsByArticleWithPaywall: 0,
      sevenDayPageViewsByArticleWithRegwall: 0,
      sevenDayTopLevelSections: 0,
      sevenDayTopLevelSectionsByArticle: 0,
      sevenDayVisitCount: 0,
      twentyEightDayPageViews: 0,
      twentyEightDayPageViewsByArticle: 0,
      twentyEightDayPageViewsByArticleWithPaywall: 0,
      twentyEightDayPageViewsByArticleWithRegwall: 0,
      twentyEightDayTopLevelSections: 0,
      twentyEightDayTopLevelSectionsByArticle: 0,
      twentyEightDayVisitCount: 0,
      daysSinceLastVisit: 0,
      visitorType: 'anonymous' as VisitorType,
      timeZone: null,
      referrer: null,
      referrerData: null,
      countryCode: null,
      visitor: null,
    };

    latestUserDimensionsRef.current = defaultDimensions;

    // Create shared repositories
    userRepoRef.current = new MockUserDimensionRepository(
      () => latestUserDimensionsRef.current as UserDimensions
    );
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
        // Update dimensions in the ref for the repository to use
        latestUserDimensionsRef.current = {
          ...latestUserDimensionsRef.current,
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
          },
        };

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

      <View style={styles.section}>
        <Text style={styles.info}>
          ℹ️ This test suite runs {generateTestScenarios().length} test scenarios that cover:
          {'\n'}• Each enum value tested once (VisitorType, ReferrerMedium, ReferrerSource, ReferrerChannel)
          {'\n'}• Validates encoded codes in decision.context (AA, BA, CA, DA codes)
          {'\n'}• Validates encoded codes in decision.inputs (FA, DE, DF, DG codes)
          {'\n'}• Partial dataset scenarios (minimal, mixed, zero-filled, high-value)
          {'\n'}• Uses shared repository instances for all tests
        </Text>
      </View>

      {onNavigateBack && (
        <View style={styles.section}>
          <Button title="Back to Main" onPress={onNavigateBack} />
        </View>
      )}

      <View style={styles.buttonSpacer} />
    </ScrollView>
  );
};
