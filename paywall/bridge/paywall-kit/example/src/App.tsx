import { useState, useMemo } from 'react';
import { Text, View, StyleSheet, ScrollView, Button } from 'react-native';
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
      referrer: 'https://google.com',
      referrerData: {
        medium: 'search',
        source: 'google',
        channel: 'discover',
      },
    };
  }
}


class MockDeviceDimensionRepository implements DeviceDimensionRepository {
  getAll(): DeviceDimensions {
    return {
      hourOfDay: new Date().getHours(),
      os: 'iOS',
      type: 'mobile',
      viewer: 'paywall-kit-example-2.0.0',
    };
  }
}

export default function App() {
  const [result, setResult] = useState<string>('No test run yet');
  const [decision, setDecision] = useState<WallDecision | null>(null);
  const [decider, setDecider] = useState<PaywallDecider | null>(null);

  // Create repository
  const repository = useMemo(() => {
    const userRepo = new MockUserDimensionRepository();
    const deviceRepo = new MockDeviceDimensionRepository();
    return PaywallDeciderRepository.createNew(userRepo, deviceRepo);
  }, []);

  const handleInitialize = () => {
    try {
      const newDecider = repository.getOneByHost('www.statesman.com', {
        apiTimeoutInMilliSeconds: '5000',
      });
      setDecider(newDecider);
      setResult(`✅ PaywallDecider initialized successfully: ${JSON.stringify(newDecider)}`);
      setDecision(null);
    } catch (error) {
      setResult(`❌ Error initializing: ${error}`);
    }
  };

  const handleDecide = () => {
    try {
      if (!decider) {
        setResult('❌ Please initialize the decider first (Button 1)');
        return;
      }
      const wallDecision = decider.decide('test-content-123', 'control');
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
            <Text style={styles.resultText}>ID: {decision.id}</Text>
            <Text style={styles.resultText}>Created: {decision.createdAt}</Text>
            <Text style={styles.resultText}>Trace: {decision.trace}</Text>
            <Text style={styles.resultText}>Context: {decision.context}</Text>
            <Text style={styles.resultText}>Inputs: {decision.inputs}</Text>
            <Text style={styles.resultText}>
              Wall Type: {decision.outcome.wallType} (code:{' '}
              {decision.outcome.wallTypeCode})
            </Text>
            <Text style={styles.resultText}>
              Visibility: {decision.outcome.wallVisibility} (code:{' '}
              {decision.outcome.wallVisibilityCode})
            </Text>
            <Text style={styles.resultText}>
              Experiment: {decision.experiment.experimentId} /{' '}
              {decision.experiment.assignedGroup}
            </Text>
            <Text style={styles.resultText}>Search Params: {decision.searchParams}</Text>
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
