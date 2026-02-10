import {
  PaywallDecider,
  PaywallDeciderRepository,
  type ReferrerMedium,
  type ReferrerSource,
  type VisitorType,
  type ReferrerChannel,
  type WallDecision,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import { Button, ScrollView, Text, View, TouchableOpacity } from 'react-native';
import { useState } from 'react';
import { Platform } from 'react-native';
import { styles } from '../styles';
import { CompactNumericInput, TextInputField, RadioButtonGroup } from '../components/InputComponents';
import { MockUserDimensionRepository, MockDeviceDimensionRepository } from '../repositories/MockRepositories';
import { UserDimensionsDisplay } from '../components/UserDimensionsDisplay';
import {
  REFERRER_MEDIUM_OPTIONS,
  REFERRER_SOURCE_OPTIONS,
  REFERRER_CHANNEL_OPTIONS,
  VISITOR_TYPE_OPTIONS,
} from '../constants/constants';

type HomePageProps = {
  onNavigateToTest: () => void;
};

function buildProperties(key1: string, value1: string, key2: string, value2: string): Record<string, any> | null {
  const props: Record<string, any> = {};
  if (key1 && value1) {
    props[key1] = value1;
  }
  if (key2 && value2) {
    props[key2] = value2;
  }
  return Object.keys(props).length > 0 ? props : null;
}

export function HomePage({ onNavigateToTest }: HomePageProps) {
  const [decision, setDecision] = useState<WallDecision | null>(null);
  const [decider, setDecider] = useState<PaywallDecider | null>(null);
  const [result, setResult] = useState<string | undefined>();
  const [showSettings, setShowSettings] = useState(false);
  const [assignedGroup, setAssignedGroup] = useState<string>('control');
  const [expandedSections, setExpandedSections] = useState<Record<string, boolean>>({
    today: true,
    sevenDay: false,
    twentyEightDay: false,
    visitor: false,
    referrer: false,
    decideInputs: false,
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
  const [referrer, setReferrer] = useState<ReferrerMedium>('search');
  const [referrerMedium, setReferrerMedium] = useState<ReferrerMedium>('search');
  const [referrerSource, setReferrerSource] = useState<ReferrerSource>('google');
  const [referrerChannel, setReferrerChannel] = useState<ReferrerChannel>('search');
  const [contentId, setContentId] = useState('test-content-123');
  
  // User Properties (key-value pairs)
  const [userPropKey1, setUserPropKey1] = useState('');
  const [userPropValue1, setUserPropValue1] = useState('');
  const [userPropKey2, setUserPropKey2] = useState('');
  const [userPropValue2, setUserPropValue2] = useState('');
  
  // Content Properties (key-value pairs)
  const [contentPropKey1, setContentPropKey1] = useState('category');
  const [contentPropValue1, setContentPropValue1] = useState('sports');
  const [contentPropKey2, setContentPropKey2] = useState('');
  const [contentPropValue2, setContentPropValue2] = useState('');

  const [userRepo] = useState(() => new MockUserDimensionRepository());

  const getCurrentUserDimensions = () => ({
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
      utmParameters: null
    },
  });

  const handleInitialize = () => {
    const startTime = Date.now();
    // Update the mock repository with current dimensions
    userRepo.update(getCurrentUserDimensions());
    
    const deviceRepo = new MockDeviceDimensionRepository();
    const repository = PaywallDeciderRepository.createNew(userRepo, deviceRepo);

    repository
      .getOneByHost('test.sophi.codes', {
        apiTimeoutInMilliSeconds: '1500',
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
    // Update dimensions before making decision
    userRepo.update(getCurrentUserDimensions());
    
    setResult('⏳ Making decision...');
    const startTime = Date.now();
    decider
      .decide(contentId, assignedGroup)
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

  return (
    <ScrollView style={styles.container}>
      <View style={styles.section}>
        <Text style={styles.title}>PaywallKit Integration Tests</Text>
        <Text style={styles.subtitle}>
          Testing native integration for {Platform.OS}
        </Text>
      </View>

      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Navigation</Text>
        <Button 
          title="Run Test Suite 🧪" 
          onPress={onNavigateToTest} 
          color="#FF9500"
        />
      </View>

      <View style={styles.section}>
        <View style={{ flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10 }}>
          <Text style={styles.sectionTitle}>Custom Test</Text>
          <TouchableOpacity 
            onPress={() => setShowSettings(!showSettings)}
            style={{ padding: 8 }}
          >
            <Text style={{ fontSize: 24 }}>⚙️</Text>
          </TouchableOpacity>
        </View>
        <Button title="Initialize Decider" onPress={handleInitialize} />
        <View style={styles.buttonSpacer} />
        <Button title="Make Decision" onPress={handleDecide} />
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
                options={VISITOR_TYPE_OPTIONS} 
                value={visitorType} 
                onSelect={(val) => setVisitorType(val as VisitorType)}
              />
              <TextInputField label="Time Zone" value={timeZone} onChangeText={setTimeZone} placeholder="e.g., America/New_York" />
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
                options={REFERRER_MEDIUM_OPTIONS} 
                value={referrerMedium} 
                onSelect={(val) => setReferrerMedium(val as ReferrerMedium)}
              />
              <RadioButtonGroup 
                label="Referrer Source" 
                options={REFERRER_SOURCE_OPTIONS} 
                value={referrerSource} 
                onSelect={(val) => setReferrerSource(val as ReferrerSource)}
              />
              <RadioButtonGroup 
                label="Referrer Channel" 
                options={REFERRER_CHANNEL_OPTIONS} 
                value={referrerChannel} 
                onSelect={(val) => setReferrerChannel(val as ReferrerChannel)}
              />
            </View>
          )}

          <TouchableOpacity 
            style={styles.collapsibleHeader}
            onPress={() => setExpandedSections(prev => ({ ...prev, decideInputs: !prev.decideInputs }))}
          >
            <Text style={styles.collapsibleTitle}>🎯 Decide Inputs</Text>
            <Text style={styles.collapsibleIcon}>{expandedSections.decideInputs ? '▼' : '▶'}</Text>
          </TouchableOpacity>
          {expandedSections.decideInputs && (
            <View style={styles.settingSubsection}>
              <TextInputField 
                label="Content ID" 
                value={contentId} 
                onChangeText={setContentId} 
                placeholder="e.g., article-123" 
              />
              <TextInputField 
                label="Assigned Group" 
                value={assignedGroup} 
                onChangeText={setAssignedGroup} 
                placeholder="e.g., control" 
              />
              
              <Text style={styles.dimensionLabel}>User Properties</Text>
              <View style={{ flexDirection: 'row', gap: 10, marginBottom: 10 }}>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Key 1" 
                    value={userPropKey1} 
                    onChangeText={setUserPropKey1} 
                    placeholder="Key" 
                  />
                </View>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Value 1" 
                    value={userPropValue1} 
                    onChangeText={setUserPropValue1} 
                    placeholder="Value" 
                  />
                </View>
              </View>
              <View style={{ flexDirection: 'row', gap: 10, marginBottom: 10 }}>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Key 2" 
                    value={userPropKey2} 
                    onChangeText={setUserPropKey2} 
                    placeholder="Key" 
                  />
                </View>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Value 2" 
                    value={userPropValue2} 
                    onChangeText={setUserPropValue2} 
                    placeholder="Value" 
                  />
                </View>
              </View>
              
              <Text style={styles.dimensionLabel}>Content Properties</Text>
              <View style={{ flexDirection: 'row', gap: 10, marginBottom: 10 }}>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Key 1" 
                    value={contentPropKey1} 
                    onChangeText={setContentPropKey1} 
                    placeholder="Key" 
                  />
                </View>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Value 1" 
                    value={contentPropValue1} 
                    onChangeText={setContentPropValue1} 
                    placeholder="Value" 
                  />
                </View>
              </View>
              <View style={{ flexDirection: 'row', gap: 10 }}>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Key 2" 
                    value={contentPropKey2} 
                    onChangeText={setContentPropKey2} 
                    placeholder="Key" 
                  />
                </View>
                <View style={{ flex: 1 }}>
                  <TextInputField 
                    label="Value 2" 
                    value={contentPropValue2} 
                    onChangeText={setContentPropValue2} 
                    placeholder="Value" 
                  />
                </View>
              </View>
            </View>
          )}
        </View>
      )}

      {showSettings && <UserDimensionsDisplay dimensions={getCurrentUserDimensions()} assignedGroup={assignedGroup} userProperties={buildProperties(userPropKey1, userPropValue1, userPropKey2, userPropValue2)} contentProperties={buildProperties(contentPropKey1, contentPropValue1, contentPropKey2, contentPropValue2)} />}

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
