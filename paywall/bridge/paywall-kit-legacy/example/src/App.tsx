import {
  PaywallDecider,
  PaywallDeciderRepository,
  type ReferrerMedium,
  type ReferrerSource,
  type UserDimensions,
  type VisitorType,
  type ReferrerChannel,
  type WallDecision,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import { Button, ScrollView, Text, View, TouchableOpacity } from 'react-native';
import { useState, useMemo, useRef, useEffect } from 'react';
import { Platform } from 'react-native';
import { styles } from './styles';
import { CompactNumericInput, TextInputField, RadioButtonGroup } from './components/InputComponents';
import { MockUserDimensionRepository, MockDeviceDimensionRepository } from './repositories/MockRepositories';
import { UserDimensionsDisplay } from './components/UserDimensionsDisplay';
import { TestPage } from './pages/TestPage';

type AppPage = 'main' | 'test';

export default function App() {
  const [currentPage, setCurrentPage] = useState<AppPage>('main');
  const [decision, setDecision] = useState<WallDecision | null>(null);
  const [decider, setDecider] = useState<PaywallDecider | null>(null);
  const [result, setResult] = useState<string | undefined>();
  const [showSettings, setShowSettings] = useState(false);
  const [expandedSections, setExpandedSections] = useState<Record<string, boolean>>({
    today: true,
    sevenDay: false,
    twentyEightDay: false,
    visitor: false,
    referrer: false,
  });

  // Page Views - Today
  const [todayPageViews, setTodayPageViews] = useState(5);
  const [todayPageViewsByArticle, setTodayPageViewsByArticle] = useState(3);
  const [todayPageViewsByArticleWithPaywall, setTodayPageViewsByArticleWithPaywall] = useState(1);
  const [todayPageViewsByArticleWithRegwall, setTodayPageViewsByArticleWithRegwall] = useState(0);
  const [todayTopLevelSections, setTodayTopLevelSections] = useState(2);
  const [todayTopLevelSectionsByArticle, setTodayTopLevelSectionsByArticle] = useState(1);

  // Page Views - 7 Day
  const [sevenDayPageViews, setSevenDayPageViews] = useState(25);
  const [sevenDayPageViewsByArticle, setSevenDayPageViewsByArticle] = useState(15);
  const [sevenDayPageViewsByArticleWithPaywall, setSevenDayPageViewsByArticleWithPaywall] = useState(5);
  const [sevenDayPageViewsByArticleWithRegwall, setSevenDayPageViewsByArticleWithRegwall] = useState(2);
  const [sevenDayTopLevelSections, setSevenDayTopLevelSections] = useState(8);
  const [sevenDayTopLevelSectionsByArticle, setSevenDayTopLevelSectionsByArticle] = useState(6);
  const [sevenDayVisitCount, setSevenDayVisitCount] = useState(5);

  // Page Views - 28 Day
  const [twentyEightDayPageViews, setTwentyEightDayPageViews] = useState(100);
  const [twentyEightDayPageViewsByArticle, setTwentyEightDayPageViewsByArticle] = useState(60);
  const [twentyEightDayPageViewsByArticleWithPaywall, setTwentyEightDayPageViewsByArticleWithPaywall] = useState(20);
  const [twentyEightDayPageViewsByArticleWithRegwall, setTwentyEightDayPageViewsByArticleWithRegwall] = useState(10);
  const [twentyEightDayTopLevelSections, setTwentyEightDayTopLevelSections] = useState(15);
  const [twentyEightDayTopLevelSectionsByArticle, setTwentyEightDayTopLevelSectionsByArticle] = useState(12);
  const [twentyEightDayVisitCount, setTwentyEightDayVisitCount] = useState(18);

  // Other
  const [daysSinceLastVisit, setDaysSinceLastVisit] = useState(0);
  const [visitorType, setVisitorType] = useState<VisitorType>('anonymous');
  const [timeZone, setTimeZone] = useState('America/New_York');
  const [referrer, setReferrer] = useState('https://google.com');
  const [referrerMedium, setReferrerMedium] = useState<ReferrerMedium>('search');
  const [referrerSource, setReferrerSource] = useState<ReferrerSource>('google');
  const [referrerChannel, setReferrerChannel] = useState<ReferrerChannel>('search');
  const [countryCode, setCountryCode] = useState('');

  // Create UserDimensions object from current state - memoized for performance
  const userDimensions = useMemo<UserDimensions>(() => ({
    todayPageViews,
    todayPageViewsByArticle,
    todayPageViewsByArticleWithPaywall,
    todayPageViewsByArticleWithRegwall,
    todayTopLevelSections,
    todayTopLevelSectionsByArticle,
    sevenDayPageViews,
    sevenDayPageViewsByArticle,
    sevenDayPageViewsByArticleWithPaywall,
    sevenDayPageViewsByArticleWithRegwall,
    sevenDayTopLevelSections,
    sevenDayTopLevelSectionsByArticle,
    sevenDayVisitCount,
    twentyEightDayPageViews,
    twentyEightDayPageViewsByArticle,
    twentyEightDayPageViewsByArticleWithPaywall,
    twentyEightDayPageViewsByArticleWithRegwall,
    twentyEightDayTopLevelSections,
    twentyEightDayTopLevelSectionsByArticle,
    twentyEightDayVisitCount,
    daysSinceLastVisit,
    visitorType,
    visitor: {
      session: {
        campaignName: 'SpringSale',
        referrerDomain: 'google.com',
        referrerMedium: referrerMedium,
        referrerSource: referrerSource,
        referrerChannel: referrerChannel,
        timestamp: new Date().toISOString(),
      },
    },
    timeZone,
    referrer,
    referrerData: {
      medium: referrerMedium,
      source: referrerSource,
      channel: referrerChannel,
    },
    countryCode: countryCode || null,
  }), [
    todayPageViews, todayPageViewsByArticle, todayPageViewsByArticleWithPaywall,
    todayPageViewsByArticleWithRegwall, todayTopLevelSections, todayTopLevelSectionsByArticle,
    sevenDayPageViews, sevenDayPageViewsByArticle, sevenDayPageViewsByArticleWithPaywall,
    sevenDayPageViewsByArticleWithRegwall, sevenDayTopLevelSections, sevenDayTopLevelSectionsByArticle,
    sevenDayVisitCount, twentyEightDayPageViews, twentyEightDayPageViewsByArticle,
    twentyEightDayPageViewsByArticleWithPaywall, twentyEightDayPageViewsByArticleWithRegwall,
    twentyEightDayTopLevelSections, twentyEightDayTopLevelSectionsByArticle, twentyEightDayVisitCount,
    daysSinceLastVisit, visitorType, timeZone, referrer, referrerMedium, referrerSource,
    referrerChannel, countryCode
  ]);
  // Keep a ref to always expose the latest dimensions to repositories created once
  const latestUserDimensionsRef = useRef<UserDimensions>(userDimensions);
  useEffect(() => {
    latestUserDimensionsRef.current = userDimensions;
  }, [userDimensions]);

  const userRepo: MockUserDimensionRepository = useMemo(
    () => new MockUserDimensionRepository(() => latestUserDimensionsRef.current),
    []
  );
  const handleInitialize = () => {
    const startTime = Date.now();
    // Create fresh repositories with a provider function that returns current dimensions
    const deviceRepo = new MockDeviceDimensionRepository();
    const repository = PaywallDeciderRepository.createNew(userRepo, deviceRepo);

    repository
      .getOneByHost('www.statesman.com', {
        apiTimeoutInMilliSeconds: '500',
        paywallApiBaseUrl: 'https://paywall.sophi.codes',
        onDeviceModelRepositoryUrl: (Platform.OS === 'android' ? 'http://10.0.2.2:8000' : 'http://localhost:8000')
      })
      .then((decider) => {
        const duration = Date.now() - startTime;
        setDecider(decider);
        setResult(
          `✅ PaywallDecider initialized successfully (${duration}ms)`
        );
      })
      .catch((error) => {
        const duration = Date.now() - startTime;
        setDecision(null);
        setResult(`❌ Error initializing: ${error} (${duration}ms)`);
      });
  };

  const handleDecide = () => {
    if (!decider) {
      setResult('❌ Please initialize the decider first (Button 1)');
      return;
    }
    setResult('⏳ Making decision...');
    const startTime = Date.now();
    decider
      .decide('test-content-123', 'control')
      .then((wallDecision) => {
        const duration = Date.now() - startTime;
        setDecision(wallDecision);
        setResult(`✅ Decision made successfully (${duration}ms)`);
      })
      .catch((error) => {
        const duration = Date.now() - startTime;
        setDecision(null);
        setResult(`❌ Error making decision: ${error} (${duration}ms)`);
      });

    
  };

  // Render TestPage if selected
  if (currentPage === 'test') {
    return <TestPage onNavigateBack={() => setCurrentPage('main')} />;
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.section}>
        <Text style={styles.title}>PaywallKit Integration Tests</Text>
        <Text style={styles.subtitle}>
          Testing native integration for {Platform.OS}
        </Text>
      </View>

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Test Methods:</Text>

        <Button title="Initialize Decider" onPress={handleInitialize} />

        <View style={styles.buttonSpacer} />

        <Button 
          title={showSettings ? 'Hide User Dimensions Settings' : 'Show User Dimensions Settings'} 
          onPress={() => setShowSettings(!showSettings)} 
        />

        <View style={styles.buttonSpacer} />

        <Button 
          title="Run Test Suite 🧪" 
          onPress={() => setCurrentPage('test')} 
          color="#FF9500"
        />
      </View>

      {showSettings && (
        <View style={styles.settingsSection}>
          <Text style={styles.settingsSectionTitle}>User Dimensions Configuration</Text>

          <TouchableOpacity 
            style={styles.collapsibleHeader}
            onPress={() => setExpandedSections(prev => ({ ...prev, today: !prev.today }))}
          >
            <Text style={styles.collapsibleTitle}>📅 Today's Metrics</Text>
            <Text style={styles.collapsibleIcon}>{expandedSections.today ? '▼' : '▶'}</Text>
          </TouchableOpacity>
          {expandedSections.today && (
            <View style={styles.compactGrid}>
              <CompactNumericInput label="Page Views" value={todayPageViews} onValueChange={setTodayPageViews} max={50} />
              <CompactNumericInput label="PV by Article" value={todayPageViewsByArticle} onValueChange={setTodayPageViewsByArticle} max={30} />
              <CompactNumericInput label="PV Article+Paywall" value={todayPageViewsByArticleWithPaywall} onValueChange={setTodayPageViewsByArticleWithPaywall} max={20} />
              <CompactNumericInput label="PV Article+Regwall" value={todayPageViewsByArticleWithRegwall} onValueChange={setTodayPageViewsByArticleWithRegwall} max={20} />
              <CompactNumericInput label="Top Sections" value={todayTopLevelSections} onValueChange={setTodayTopLevelSections} max={10} />
              <CompactNumericInput label="Top Sections by Article" value={todayTopLevelSectionsByArticle} onValueChange={setTodayTopLevelSectionsByArticle} max={10} />
            </View>
          )}

          <TouchableOpacity 
            style={styles.collapsibleHeader}
            onPress={() => setExpandedSections(prev => ({ ...prev, sevenDay: !prev.sevenDay }))}
          >
            <Text style={styles.collapsibleTitle}>📊 7-Day Metrics</Text>
            <Text style={styles.collapsibleIcon}>{expandedSections.sevenDay ? '▼' : '▶'}</Text>
          </TouchableOpacity>
          {expandedSections.sevenDay && (
            <View style={styles.compactGrid}>
              <CompactNumericInput label="Page Views" value={sevenDayPageViews} onValueChange={setSevenDayPageViews} max={100} />
              <CompactNumericInput label="PV by Article" value={sevenDayPageViewsByArticle} onValueChange={setSevenDayPageViewsByArticle} max={100} />
              <CompactNumericInput label="PV Article+Paywall" value={sevenDayPageViewsByArticleWithPaywall} onValueChange={setSevenDayPageViewsByArticleWithPaywall} max={50} />
              <CompactNumericInput label="PV Article+Regwall" value={sevenDayPageViewsByArticleWithRegwall} onValueChange={setSevenDayPageViewsByArticleWithRegwall} max={50} />
              <CompactNumericInput label="Top Sections" value={sevenDayTopLevelSections} onValueChange={setSevenDayTopLevelSections} max={30} />
              <CompactNumericInput label="Top Sections by Article" value={sevenDayTopLevelSectionsByArticle} onValueChange={setSevenDayTopLevelSectionsByArticle} max={30} />
              <CompactNumericInput label="Visit Count" value={sevenDayVisitCount} onValueChange={setSevenDayVisitCount} max={30} />
            </View>
          )}

          <TouchableOpacity 
            style={styles.collapsibleHeader}
            onPress={() => setExpandedSections(prev => ({ ...prev, twentyEightDay: !prev.twentyEightDay }))}
          >
            <Text style={styles.collapsibleTitle}>📈 28-Day Metrics</Text>
            <Text style={styles.collapsibleIcon}>{expandedSections.twentyEightDay ? '▼' : '▶'}</Text>
          </TouchableOpacity>
          {expandedSections.twentyEightDay && (
            <View style={styles.compactGrid}>
              <CompactNumericInput label="Page Views" value={twentyEightDayPageViews} onValueChange={setTwentyEightDayPageViews} max={300} />
              <CompactNumericInput label="PV by Article" value={twentyEightDayPageViewsByArticle} onValueChange={setTwentyEightDayPageViewsByArticle} max={200} />
              <CompactNumericInput label="PV Article+Paywall" value={twentyEightDayPageViewsByArticleWithPaywall} onValueChange={setTwentyEightDayPageViewsByArticleWithPaywall} max={100} />
              <CompactNumericInput label="PV Article+Regwall" value={twentyEightDayPageViewsByArticleWithRegwall} onValueChange={setTwentyEightDayPageViewsByArticleWithRegwall} max={100} />
              <CompactNumericInput label="Top Sections" value={twentyEightDayTopLevelSections} onValueChange={setTwentyEightDayTopLevelSections} max={50} />
              <CompactNumericInput label="Top Sections by Article" value={twentyEightDayTopLevelSectionsByArticle} onValueChange={setTwentyEightDayTopLevelSectionsByArticle} max={50} />
              <CompactNumericInput label="Visit Count" value={twentyEightDayVisitCount} onValueChange={setTwentyEightDayVisitCount} max={50} />
            </View>
          )}

          <TouchableOpacity 
            style={styles.collapsibleHeader}
            onPress={() => setExpandedSections(prev => ({ ...prev, visitor: !prev.visitor }))}
          >
            <Text style={styles.collapsibleTitle}>👤 Visitor & Location</Text>
            <Text style={styles.collapsibleIcon}>{expandedSections.visitor ? '▼' : '▶'}</Text>
          </TouchableOpacity>
          {expandedSections.visitor && (
            <View style={styles.settingSubsection}>
              <CompactNumericInput label="Days Since Last Visit" value={daysSinceLastVisit} onValueChange={setDaysSinceLastVisit} max={365} />
              <RadioButtonGroup 
                label="Visitor Type" 
                options={['anonymous', 'registered']} 
                value={visitorType} 
                onSelect={(val) => setVisitorType(val as VisitorType)}
              />
              <TextInputField label="Time Zone" value={timeZone} onChangeText={setTimeZone} placeholder="e.g., America/New_York" />
              <TextInputField label="Country Code" value={countryCode} onChangeText={setCountryCode} placeholder="e.g., US" />
            </View>
          )}

          <TouchableOpacity 
            style={styles.collapsibleHeader}
            onPress={() => setExpandedSections(prev => ({ ...prev, referrer: !prev.referrer }))}
          >
            <Text style={styles.collapsibleTitle}>🔗 Referrer Information</Text>
            <Text style={styles.collapsibleIcon}>{expandedSections.referrer ? '▼' : '▶'}</Text>
          </TouchableOpacity>
          {expandedSections.referrer && (
            <View style={styles.settingSubsection}>
              <TextInputField label="Referrer URL" value={referrer} onChangeText={setReferrer} placeholder="e.g., https://google.com" />
              <RadioButtonGroup 
                label="Referrer Medium" 
                options={['campaign', 'direct', 'internal', 'search', 'social', 'other']} 
                value={referrerMedium} 
                onSelect={(val) => setReferrerMedium(val as ReferrerMedium)}
              />
              <RadioButtonGroup 
                label="Referrer Source" 
                options={['google', 'yahoo', 'duckduckgo', 'bing', 'facebook', 'instagram', 'x', 't', 'linkedin', 'reddit', 'newsletter']} 
                value={referrerSource} 
                onSelect={(val) => setReferrerSource(val as ReferrerSource)}
              />
              <RadioButtonGroup 
                label="Referrer Channel" 
                options={['search', 'news', 'discover']} 
                value={referrerChannel} 
                onSelect={(val) => setReferrerChannel(val as ReferrerChannel)}
              />
            </View>
          )}
        </View>
      )}



      {showSettings && <UserDimensionsDisplay dimensions={userDimensions} decision={decision} />}

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Result:</Text>
        <Text style={styles.result}>{result}</Text>
      </View>

      <View style={styles.buttonSpacer} />
      
      <Button title="Make Decision" onPress={handleDecide} />

      <View style={styles.buttonSpacer} />

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
              Experiment: {decision.experimentCode}
            </Text>
            <Text style={styles.resultText}>
              Search Params: {decision.searchParams}
            </Text>
          </View>
        </View>
      )}

      <View style={styles.section}>
        <Text style={styles.info}>
          ℹ️ These tests call the native methods via native-bridge. If they
          pass, the bridge is properly connected.
        </Text>
      </View>
    </ScrollView>
  );
}
