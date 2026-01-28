import Foundation

import Paywall

class NativeDeviceDimensionRepository: DeviceDimensionRepository {
    func getAll() -> DeviceDimensions {
        return DeviceDimensions(
            hourOfDay: 2, os: "iOS", type: "mobile", viewer: "WebKit"
        )
    }
}


class NativeUserDimensionRepository: UserDimensionRepository {
    func getAll() -> UserDimensions {
        
        let dummyUserDimensions = UserDimensions(
            todayPageViews: 5,
            todayPageViewsByArticle: 2,
            todayPageViewsByArticleWithPaywall: 1,
            todayPageViewsByArticleWithRegwall: 1,
            todayTopLevelSections: 3,
            todayTopLevelSectionsByArticle: 2,
            sevenDayPageViews: 2,
            sevenDayPageViewsByArticle: 8,
            sevenDayPageViewsByArticleWithPaywall: 3,
            sevenDayPageViewsByArticleWithRegwall: 2,
            sevenDayTopLevelSections: 6,
            sevenDayTopLevelSectionsByArticle: 4,
            sevenDayVisitCount: 7,
            twentyEightDayPageViews: 8,
            twentyEightDayPageViewsByArticle: 3,
            twentyEightDayPageViewsByArticleWithPaywall: 1,
            twentyEightDayPageViewsByArticleWithRegwall: 5,
            twentyEightDayTopLevelSections: 12,
            twentyEightDayTopLevelSectionsByArticle: 8,
            twentyEightDayVisitCount: 7,
            daysSinceLastVisit: 2,
            visitorType: VisitorType.registered,
            visitor: UserDimensions.Visitor(
                session: UserDimensions.Session(
                    campaignName: "demo",
                    referrerDomain: "www.google.com",
                    referrerMedium: ReferrerMedium.search,
                    referrerSource: ReferrerSource.google,
                    referrerChannel: ReferrerChannel.search,
                    timestamp: "2024-06-01T12:00:00Z"
                )
            ),
            timeZone: "America/Toronto",
            referrer: "google.com",
            referrerData: UserDimensions.ReferrerData(
                medium: ReferrerMedium.search,
                source: ReferrerSource.google,
                channel: ReferrerChannel.search
            )
        )
        return dummyUserDimensions
    }
    
    
}
