import { Platform } from 'react-native';
import {
  type DeviceDimensionRepository,
  type DeviceDimensions,
  type UserDimensionRepository,
  type UserDimensions,
} from '@mather-sophi/sophi-react-native-paywall-kit';

export class MockUserDimensionRepository implements UserDimensionRepository {
  private dimensions: UserDimensions = {
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
    visitorType: 'anonymous',
    visitor: {
      session: {
        campaignName: 'SpringSale',
        referrerDomain: 'google.com',
        referrerMedium: 'search',
        referrerSource: 'google',
        referrerChannel: 'search',
        timestamp: new Date().toISOString(),
      },
    },
    timeZone: 'America/New_York',
    referrer: 'search',
    referrerData: {
      medium: 'search',
      source: 'google',
      channel: 'search',
      utmParameters: null
    },
  };

  getAll(): UserDimensions {
    return this.dimensions;
  }

  update(dimensions: Partial<UserDimensions>): void {
    this.dimensions = { ...this.dimensions, ...dimensions };
  }
}

export class MockDeviceDimensionRepository implements DeviceDimensionRepository {
  getAll(): DeviceDimensions {
    return {
      hourOfDay: new Date().getHours(),
      os: Platform.OS === 'ios' ? 'ios' : 'android',
      type: 'native',
      viewer: 'paywall-kit-example-2.0.0',
    };
  }
}
