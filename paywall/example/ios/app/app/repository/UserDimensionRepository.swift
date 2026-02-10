import Foundation
import Paywall

class NativeUserDimensionRepository: UserDimensionRepository {
    private var dimension = UserDimensions(
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
        visitorType: .anonymous,
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
        referrer: .search,
        referrerData: UserDimensions.ReferrerData(
            medium: .search,
            source: .google,
            channel: .search,
            utmParams: nil
        )
    )

    func getAll() -> UserDimensions {
        return dimension
    }

    func update(
        todayPageViews: Int32? = nil,
        todayPageViewsByArticle: Int32? = nil,
        todayPageViewsByArticleWithPaywall: Int32? = nil,
        todayPageViewsByArticleWithRegwall: Int32? = nil,
        todayTopLevelSections: Int32? = nil,
        todayTopLevelSectionsByArticle: Int32? = nil,
        sevenDayPageViews: Int32? = nil,
        sevenDayPageViewsByArticle: Int32? = nil,
        sevenDayPageViewsByArticleWithPaywall: Int32? = nil,
        sevenDayPageViewsByArticleWithRegwall: Int32? = nil,
        sevenDayTopLevelSections: Int32? = nil,
        sevenDayTopLevelSectionsByArticle: Int32? = nil,
        sevenDayVisitCount: Int32? = nil,
        twentyEightDayPageViews: Int32? = nil,
        twentyEightDayPageViewsByArticle: Int32? = nil,
        twentyEightDayPageViewsByArticleWithPaywall: Int32? = nil,
        twentyEightDayPageViewsByArticleWithRegwall: Int32? = nil,
        twentyEightDayTopLevelSections: Int32? = nil,
        twentyEightDayTopLevelSectionsByArticle: Int32? = nil,
        twentyEightDayVisitCount: Int32? = nil,
        daysSinceLastVisit: Int32? = nil,
        visitorType: VisitorType? = nil
    ) {
        if let todayPageViews {
            self.dimension.todayPageViews = todayPageViews
        }

        if let todayPageViewsByArticle {
            self.dimension.todayPageViewsByArticle = todayPageViewsByArticle
        }

        if let todayPageViewsByArticleWithPaywall {
            self.dimension.todayPageViewsByArticleWithPaywall =
                todayPageViewsByArticleWithPaywall
        }

        if let todayPageViewsByArticleWithRegwall {
            self.dimension.todayPageViewsByArticleWithRegwall =
                todayPageViewsByArticleWithRegwall
        }

        if let todayTopLevelSections {
            self.dimension.todayTopLevelSections = todayTopLevelSections
        }

        if let todayTopLevelSectionsByArticle {
            self.dimension.todayTopLevelSectionsByArticle =
                todayTopLevelSectionsByArticle
        }

        if let sevenDayPageViews {
            self.dimension.sevenDayPageViews = sevenDayPageViews
        }

        if let sevenDayPageViewsByArticle {
            self.dimension.sevenDayPageViewsByArticle =
                sevenDayPageViewsByArticle
        }

        if let sevenDayPageViewsByArticleWithPaywall {
            self.dimension.sevenDayPageViewsByArticleWithPaywall =
                sevenDayPageViewsByArticleWithPaywall
        }

        if let sevenDayPageViewsByArticleWithRegwall {
            self.dimension.sevenDayPageViewsByArticleWithRegwall =
                sevenDayPageViewsByArticleWithRegwall
        }

        if let sevenDayTopLevelSections {
            self.dimension.sevenDayTopLevelSections = sevenDayTopLevelSections
        }

        if let sevenDayTopLevelSectionsByArticle {
            self.dimension.sevenDayTopLevelSectionsByArticle =
                sevenDayTopLevelSectionsByArticle
        }

        if let sevenDayVisitCount {
            self.dimension.sevenDayVisitCount = sevenDayVisitCount
        }

        if let twentyEightDayPageViews {
            self.dimension.twentyEightDayPageViews = twentyEightDayPageViews
        }

        if let twentyEightDayPageViewsByArticle {
            self.dimension.twentyEightDayPageViewsByArticle =
                twentyEightDayPageViewsByArticle
        }

        if let twentyEightDayPageViewsByArticleWithPaywall {
            self.dimension.twentyEightDayPageViewsByArticleWithPaywall =
                twentyEightDayPageViewsByArticleWithPaywall
        }

        if let twentyEightDayPageViewsByArticleWithRegwall {
            self.dimension.twentyEightDayPageViewsByArticleWithRegwall =
                twentyEightDayPageViewsByArticleWithRegwall
        }

        if let twentyEightDayTopLevelSections {
            self.dimension.twentyEightDayTopLevelSections =
                twentyEightDayTopLevelSections
        }

        if let twentyEightDayTopLevelSectionsByArticle {
            self.dimension.twentyEightDayTopLevelSectionsByArticle =
                twentyEightDayTopLevelSectionsByArticle
        }

        if let twentyEightDayVisitCount {
            self.dimension.twentyEightDayVisitCount = twentyEightDayVisitCount
        }

        if let daysSinceLastVisit {
            self.dimension.daysSinceLastVisit = daysSinceLastVisit
        }

        if let visitorType {
            self.dimension.visitorType = visitorType
        }

    }

}

let userDimensions = NativeUserDimensionRepository()

func login() {
    logger.info("User Logged In")
    userDimensions.update(visitorType: .registered)

}

func logout() {
    logger.info("User Logged Out")
    userDimensions.update(visitorType: .anonymous)
}

func isUserLoggedIn() -> Bool {
    return userDimensions.getAll().visitorType == .registered
}
