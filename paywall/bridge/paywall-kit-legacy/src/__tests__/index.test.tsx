import {
  type UserDimensionRepository,
  type DeviceDimensionRepository,
  type UserDimensions,
  type DeviceDimensions,
} from '@mather-sophi/sophi-react-native-paywall-kit';

class NativeUserDimensionRepository implements UserDimensionRepository {
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
      visitor: {
        session: {
          campaignName: 'campaign-1',
          referrerDomain: 'www.example.com',
          referrerMedium: 'search',
          referrerSource: 'google',
          referrerChannel: 'discover',
          timestamp: '2024-06-01T12:00:00Z',
        },
      },
      timeZone: 'America/New_York',
      referrer: 'https://google.com',
      referrerData: {
        medium: 'search',
        source: 'google',
        channel: 'discover',
      },
      countryCode: null,
    };
  }
}

class NativeDeviceDimensionRepository implements DeviceDimensionRepository {
  private getHourOfDay(): number {
    return new Date().getHours();
  }

  getAll(): DeviceDimensions {
    return {
      hourOfDay: this.getHourOfDay(),
      os: 'iOS',
      type: 'mobile',
      viewer: 'test-app-1.0.0',
    };
  }
}

describe('Dimension Repository', () => {
  it('Should return the user dimension', async () => {
    const userDimensionRepository = new NativeUserDimensionRepository();
    const dimensions = userDimensionRepository.getAll();
    expect(dimensions.todayPageViews).toBe(5);
    expect(dimensions.todayPageViewsByArticle).toBe(3);
    expect(dimensions.todayPageViewsByArticleWithPaywall).toBe(1);
    expect(dimensions.todayPageViewsByArticleWithRegwall).toBe(0);
    expect(dimensions.todayTopLevelSections).toBe(2);
    expect(dimensions.todayTopLevelSectionsByArticle).toBe(1);
    expect(dimensions.sevenDayPageViews).toBe(25);
    expect(dimensions.sevenDayPageViewsByArticle).toBe(15);
    expect(dimensions.sevenDayPageViewsByArticleWithPaywall).toBe(5);
    expect(dimensions.sevenDayPageViewsByArticleWithRegwall).toBe(2);
    expect(dimensions.sevenDayTopLevelSections).toBe(8);
    expect(dimensions.sevenDayTopLevelSectionsByArticle).toBe(6);
    expect(dimensions.sevenDayVisitCount).toBe(5);
    expect(dimensions.twentyEightDayPageViews).toBe(100);
    expect(dimensions.twentyEightDayPageViewsByArticle).toBe(60);
    expect(dimensions.twentyEightDayPageViewsByArticleWithPaywall).toBe(20);
    expect(dimensions.twentyEightDayPageViewsByArticleWithRegwall).toBe(10);
    expect(dimensions.twentyEightDayTopLevelSections).toBe(15);
    expect(dimensions.twentyEightDayTopLevelSectionsByArticle).toBe(12);
    expect(dimensions.twentyEightDayVisitCount).toBe(18);
    expect(dimensions.daysSinceLastVisit).toBe(0);
    expect(dimensions.visitorType).toBe('registered');
    expect(dimensions.visitor?.session?.campaignName).toBe('campaign-1');
    expect(dimensions.visitor?.session?.referrerDomain).toBe('www.example.com');
    expect(dimensions.visitor?.session?.referrerMedium).toBe('search');
    expect(dimensions.visitor?.session?.referrerSource).toBe('google');
    expect(dimensions.visitor?.session?.referrerChannel).toBe('discover');
    expect(dimensions.visitor?.session?.timestamp).toBe('2024-06-01T12:00:00Z');
    expect(dimensions.timeZone).toBe('America/New_York');
    expect(dimensions.referrer).toBe('https://google.com');
    expect(dimensions.referrerData?.channel).toBe('discover');
    expect(dimensions.referrerData?.source).toBe('google');
    expect(dimensions.referrerData?.medium).toBe('search');
    expect(dimensions.countryCode).toBeNull();
  });

  it('Should return the device dimension', async () => {
    const deviceDimensionRepository = new NativeDeviceDimensionRepository();
    const dimensions = deviceDimensionRepository.getAll();

    expect(dimensions.hourOfDay).toBe(new Date().getHours());
    expect(dimensions.os).toBe('iOS');
    expect(dimensions.type).toBe('mobile');
    expect(dimensions.viewer).toBe('test-app-1.0.0');
  });
});

// TODO: Move this test to example for integration testing.
// describe('Test Deciders', () => {
//   it('Should return the decision when initialized correctly', async () => {
//     const deviceDimensionRepository = new NativeDeviceDimensionRepository();
//     const userDimensionRepository = new NativeUserDimensionRepository();
//     const paywallDeciderRepository = PaywallDeciderRepository.createNew(
//       userDimensionRepository,
//       deviceDimensionRepository
//     );
//
//     const decider = await paywallDeciderRepository.getOneByHost(
//       'https://api.example.com',
//       {
//         apiTimeoutInMilliSeconds: '5000',
//       }
//     );
//     expect(decider).toBeDefined();
//
//     const decision = await decider.decide('test-content-123', 'control');
//
//     expect(decision).toBeDefined();
//   });
// });
