import { useState } from 'react';
import { Text, View, StyleSheet, ScrollView, Button, Platform } from 'react-native';
import {
  type DeviceDimensions,
  PaywallDeciderRepository,
  PaywallDecider,
  type UserDimensions,
  type WallDecision,
  type UserDimensionRepository,
  type DeviceDimensionRepository
} from '@mather-sophi/sophi-react-native-paywall-kit';


// Mock repositories
class MockUserDimensionRepository implements UserDimensionRepository {
  getAll(): UserDimensions {
    return {
      todayPageViews: 5,
      todayPageViewsByArticle: 3,
      todayPageViewsByArticleWithPaywall: 1,
      todayPageViewsByArticleWithRegwall: 0,
      todayTopLevelSections: 2,
      todayTopLevelSectionsByArticle: 1,
      sevenDayPageViews: 25,
      sevenDayPageViewsByArticle: 15,
      sevenDayPageViewsByArticleWithPaywall: 5,
      sevenDayPageViewsByArticleWithRegwall: 2,
      sevenDayTopLevelSections: 8,
      sevenDayTopLevelSectionsByArticle: 6,
      sevenDayVisitCount: 5,
      twentyEightDayPageViews: 100,
      twentyEightDayPageViewsByArticle: 60,
      twentyEightDayPageViewsByArticleWithPaywall: 20,
      twentyEightDayPageViewsByArticleWithRegwall: 10,
      twentyEightDayTopLevelSections: 15,
      twentyEightDayTopLevelSectionsByArticle: 12,
      twentyEightDayVisitCount: 18,
      daysSinceLastVisit: 0,
      visitorType: 'registered',
      visitor: null,
      timeZone: 'America/New_York',
      referrer: 'search',
      referrerData: {
        medium: 'search',
        source: 'google',
        channel: 'discover',
        utmParameters: null
      }
    };
  }
}


class MockDeviceDimensionRepository implements DeviceDimensionRepository {
  getAll(): DeviceDimensions {
    return {
      hourOfDay: new Date().getHours(),
      os: Platform.OS === 'ios' ? 'ios' : 'android',
      type: 'native',
      viewer: 'paywall-kit-example-1.0.0',
    };
  }
}

export default function App() {
  const [result, setResult] = useState<string>('No test run yet');
  const [decision, setDecision] = useState<WallDecision | null>(null);
  const [decider, setDecider] = useState<PaywallDecider | null>(null);


  const handleInitialize = () => {
    const startTime = Date.now();
    const userRepo = new MockUserDimensionRepository();
    const deviceRepo = new MockDeviceDimensionRepository();
    const repository = PaywallDeciderRepository.createNew(userRepo, deviceRepo);
    
    repository.getOneByHost('test.sophi.codes', 
      {        
        apiTimeoutInMilliSeconds: '1500',
        paywallApiBaseUrl: 'https://paywall.sophi.codes',
        onDeviceModelRepositoryUrl: (Platform.OS === 'android' ? 'http://10.0.2.2:8000' : 'http://localhost:8000')
      }
    ).then((decider: PaywallDecider) => {
      const duration = Date.now() - startTime;
      setDecider(decider);
      setResult(
        `✅ PaywallDecider initialized successfully (${duration}ms)`
      );
    })
    .catch((error: any) => {
      const duration = Date.now() - startTime;
      setDecision(null);
      setResult(`❌ Error initializing: ${error} (${duration}ms)`);
    });
  };

  const handleDecide = async () => {
    try {
      if (!decider) {
        setResult('❌ Please initialize the decider first (Button 1)');
        return;
      }
      const wallDecision = await decider.decide('test-content-123', 'control');
      setDecision(wallDecision);
      setResult('✅ Decision received - see details below');
    } catch (error) {
      setResult(`❌ Error making decision: ${error}`);
      setDecision(null);
    }
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.section}>
        <Text style={styles.title}>PaywallKit Integration Tests</Text>
        <Text style={styles.subtitle}>
          Testing native XCFramework bridge integration
        </Text>
      </View>

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Test Methods:</Text>

        <Button title="1. Initialize Decider" onPress={handleInitialize} />

        <View style={styles.buttonSpacer} />

        <Button title="2. Make Decision" onPress={handleDecide} />
      </View>

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Result:</Text>
        <Text style={styles.result}>{result}</Text>
      </View>

      {decision && (
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Decision Details:</Text>
          <View style={styles.resultBox}>
            <Text style={styles.resultText}>ID: {decision.id || 'N/A'}</Text>
            <Text style={styles.resultText}>Created: {decision.createdAt || 'N/A'}</Text>
            <Text style={styles.resultText}>Trace: {decision.trace || 'N/A'}</Text>
            <Text style={styles.resultText}>Context: {decision.context || 'N/A'}</Text>
            <Text style={styles.resultText}>Inputs: {decision.inputs || 'N/A'}</Text>
            <Text style={styles.resultText}>
              Wall Type: {decision.outcome?.wallType || 'N/A'} (code:{' '}
              {decision.outcome?.wallTypeCode || 'N/A'})
            </Text>
            <Text style={styles.resultText}>
              Visibility: {decision.outcome?.wallVisibility || 'N/A'} (code:{' '}
              {decision.outcome?.wallVisibilityCode || 'N/A'})
            </Text>
            <Text style={styles.resultText}>
              Experiment Codes: {decision.experimentCode || 'N/A'}
            </Text>
            <Text style={styles.resultText}>Search Params: {decision.searchParams || 'N/A'}</Text>
            <Text style={styles.resultText}>Paywall Score: {decision.paywallScore || 'N/A'}</Text>
            <Text style={styles.resultText}>
              {JSON.stringify(decision, null, 2)}
            </Text>
          </View>
        </View>
      )}

      <View style={styles.section}>
        <Text style={styles.info}>
          ℹ️ These tests call the native XCFramework methods directly. If they
          pass, the bridge is properly connected.
        </Text>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  section: {
    padding: 20,
    backgroundColor: 'white',
    marginTop: 10,
    marginHorizontal: 10,
    borderRadius: 8,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 14,
    color: '#666',
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 12,
  },
  result: {
    fontSize: 13,
    color: '#333',
    fontFamily: 'Courier',
    backgroundColor: '#f9f9f9',
    padding: 12,
    borderRadius: 4,
  },
  buttonSpacer: {
    height: 12,
  },
  resultBox: {
    backgroundColor: '#f0f0f0',
    padding: 12,
    borderRadius: 4,
  },
  resultText: {
    fontSize: 12,
    fontFamily: 'Courier',
    marginBottom: 4,
  },
  info: {
    fontSize: 12,
    color: '#666',
    fontStyle: 'italic',
  },
});
