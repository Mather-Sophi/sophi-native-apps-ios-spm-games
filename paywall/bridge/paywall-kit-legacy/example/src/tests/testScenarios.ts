import type {
  ReferrerMedium,
  ReferrerSource,
  ReferrerChannel,
  VisitorType,
  UserDimensions,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import { expectedCodesForScenario, type ScenarioLike } from './codeMappings';
import {
  REFERRER_MEDIUM_OPTIONS,
  REFERRER_SOURCE_OPTIONS,
  REFERRER_CHANNEL_OPTIONS,
  VISITOR_TYPE_OPTIONS,
} from '../constants/constants';

export interface TestScenario {
  name: string;
  visitorType: VisitorType;
  referrerMedium: ReferrerMedium;
  referrerSource: ReferrerSource;
  referrerChannel: ReferrerChannel;
  userDimensions: Partial<UserDimensions>;
  expectedMappings?: {
    contextCodes?: string[];
    inputCodes?: string[];
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
  VISITOR_TYPE_OPTIONS.forEach((visitorType) => {
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
        referrer: 'search',
      },
    });
  });

  // Test each ReferrerMedium once
  REFERRER_MEDIUM_OPTIONS.forEach((medium) => {
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
        referrer: 'search',
      },
    });
  });

  // Test each ReferrerSource once
  REFERRER_SOURCE_OPTIONS.forEach((source) => {
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
        referrer: 'search',
      },
    });
  });

  // Test each ReferrerChannel once
  REFERRER_CHANNEL_OPTIONS.forEach((channel) => {
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
        referrer: 'search',
      },
    });
  });

  // Partial data scenarios
  const partialDataScenarios: Omit<TestScenario, 'expectedMappings'>[] = [
    {
      name: 'Partial: Minimal Page Views (Today only)',
      visitorType: 'anonymous',
      referrerMedium: 'search',
      referrerSource: 'google',
      referrerChannel: 'search',
      userDimensions: {
        todayPageViews: 1,
        daysSinceLastVisit: 0,
        visitorType: 'anonymous',
      },
    },
    {
      name: 'Partial: Today + 7 Day metrics only',
      visitorType: 'registered',
      referrerMedium: 'direct',
      referrerSource: 'facebook',
      referrerChannel: 'news',
      userDimensions: {
        todayPageViews: 5,
        todayPageViewsByArticle: 3,
        sevenDayPageViews: 5,
        sevenDayPageViewsByArticle: 15,
        sevenDayVisitCount: 5,
        daysSinceLastVisit: 2,
        visitorType: 'registered',
      },
    },
    {
      name: 'Partial: Zero-filled dimensions',
      visitorType: 'anonymous',
      referrerMedium: 'social',
      referrerSource: 'instagram',
      referrerChannel: 'discover',
      userDimensions: {
        todayPageViews: 0,
        todayPageViewsByArticle: 0,
        sevenDayPageViews: 0,
        sevenDayPageViewsByArticle: 0,
        twentyEightDayPageViews: 0,
        daysSinceLastVisit: 30,
        visitorType: 'anonymous',
      },
    },
  ];

  partialDataScenarios.forEach(pushScenario);

  return scenarios;
}
