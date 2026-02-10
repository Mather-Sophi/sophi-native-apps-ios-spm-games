import { Platform } from 'react-native';
import type {
  PaywallDeciderRepository,
  UserDimensions,
  WallDecision,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import type { TestScenario } from './testScenarios';

export interface TestResult {
  testName: string;
  passed: boolean;
  duration: number;
  error?: string;
  decision?: WallDecision;
}

export async function runTestScenarios(
  scenarios: TestScenario[],
  deciderRepository: PaywallDeciderRepository,
  userDimensionsRef: React.MutableRefObject<UserDimensions>,
  onProgress?: (results: TestResult[]) => void
): Promise<TestResult[]> {
  const results: TestResult[] = [];

  let decider;
  try {
    decider = await deciderRepository.getOneByHost('test.sophi.io', {
      apiTimeoutInMilliSeconds: '1500',
      onDeviceModelRepositoryUrl:
        Platform.OS === 'android'
          ? 'http://10.0.2.2:8000'
          : 'http://localhost:8000',
    });
  } catch (error) {
    return [
      {
        testName: 'Decider Initialization',
        passed: false,
        duration: 0,
        error: `Failed to get decider: ${String(error)}`,
      },
    ];
  }

  // Run tests sequentially
  for (let i = 0; i < scenarios.length; i++) {
    const scenario = scenarios[i]!;
    const startTime = Date.now();

    try {
      // Update dimensions in the ref for the repository to use
      userDimensionsRef.current = {
        ...userDimensionsRef.current,
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
      };

      const decision = await decider.decide(
        'test-content',
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

    // Call progress callback if provided
    if (onProgress) {
      onProgress([...results]);
    }
  }

  return results;
}
