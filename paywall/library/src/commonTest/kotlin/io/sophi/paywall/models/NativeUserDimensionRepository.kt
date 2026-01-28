package io.sophi.paywall.models

import io.sophi.paywall.enums.ReferrerChannel
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.ReferrerSource
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.repository.UserDimensionRepository

class NativeUserDimensionRepository : UserDimensionRepository {
    var userDimension = UserDimensions(
        todayPageViews = 3,
        todayPageViewsByArticle = 2,
        todayPageViewsByArticleWithPaywall = 1,
        todayPageViewsByArticleWithRegwall = 0,
        todayTopLevelSections = 2,
        todayTopLevelSectionsByArticle = 1,
        sevenDayPageViews = 7,
        sevenDayPageViewsByArticle = 5,
        sevenDayPageViewsByArticleWithPaywall = 2,
        sevenDayPageViewsByArticleWithRegwall = 1,
        sevenDayTopLevelSections = 4,
        sevenDayTopLevelSectionsByArticle = 3,
        sevenDayVisitCount = 5,
        twentyEightDayPageViews = 15,
        twentyEightDayPageViewsByArticle = 10,
        twentyEightDayPageViewsByArticleWithPaywall = 5,
        twentyEightDayPageViewsByArticleWithRegwall = 2,
        twentyEightDayTopLevelSections = 8,
        twentyEightDayTopLevelSectionsByArticle = 6,
        twentyEightDayVisitCount = 12,
        daysSinceLastVisit = 2,
        visitorType = VisitorType.REGISTERED,
        visitor = UserDimensions.Visitor(
            session = UserDimensions.Session(
                campaignName = "SpringSale",
                referrerDomain = "google.com",
                referrerMedium = ReferrerMedium.SEARCH,
                referrerSource = ReferrerSource.GOOGLE,
                referrerChannel = ReferrerChannel.SEARCH,
                timestamp = "2024-06-01T12:00:00Z"
            )
        ),
        timeZone = "America/New_York",
        referrer = ReferrerMedium.SEARCH,
        referrerData = UserDimensions.ReferrerData(
            medium = ReferrerMedium.SEARCH,
            source = ReferrerSource.REDDIT,
            channel = ReferrerChannel.SEARCH
        )
    )

    override fun getAll(): UserDimensions {
        return userDimension
    }

    fun update(
        todayPageViews: Int = userDimension.todayPageViews,
        todayPageViewsByArticle: Int = userDimension.todayPageViewsByArticle,
        todayPageViewsByArticleWithPaywall: Int = userDimension.todayPageViewsByArticleWithPaywall,
        todayPageViewsByArticleWithRegwall: Int = userDimension.todayPageViewsByArticleWithRegwall,
        todayTopLevelSections: Int = userDimension.todayTopLevelSections,
        todayTopLevelSectionsByArticle: Int = userDimension.todayTopLevelSectionsByArticle,
        sevenDayPageViews: Int = userDimension.sevenDayPageViews,
        sevenDayPageViewsByArticle: Int = userDimension.sevenDayPageViewsByArticle,
        sevenDayPageViewsByArticleWithPaywall: Int = userDimension.sevenDayPageViewsByArticleWithPaywall,
        sevenDayPageViewsByArticleWithRegwall: Int = userDimension.sevenDayPageViewsByArticleWithRegwall,
        sevenDayTopLevelSections: Int = userDimension.sevenDayTopLevelSections,
        sevenDayTopLevelSectionsByArticle: Int = userDimension.sevenDayTopLevelSectionsByArticle,
        sevenDayVisitCount: Int = userDimension.sevenDayVisitCount,
        twentyEightDayPageViews: Int = userDimension.twentyEightDayPageViews,
        twentyEightDayPageViewsByArticle: Int = userDimension.twentyEightDayPageViewsByArticle,
        twentyEightDayPageViewsByArticleWithPaywall: Int = userDimension.twentyEightDayPageViewsByArticleWithPaywall,
        twentyEightDayPageViewsByArticleWithRegwall: Int = userDimension.twentyEightDayPageViewsByArticleWithRegwall,
        twentyEightDayTopLevelSections: Int = userDimension.twentyEightDayTopLevelSections,
        twentyEightDayTopLevelSectionsByArticle: Int = userDimension.twentyEightDayTopLevelSectionsByArticle,
        twentyEightDayVisitCount: Int = userDimension.twentyEightDayVisitCount,
        daysSinceLastVisit: Int = userDimension.daysSinceLastVisit,
        visitorType: VisitorType = userDimension.visitorType,
        visitor: UserDimensions.Visitor? = userDimension.visitor,
        timeZone: String? = userDimension.timeZone,
        referrer: ReferrerMedium? = userDimension.referrer,
        referrerData: UserDimensions.ReferrerData? = userDimension.referrerData

    ) {
        userDimension.todayPageViews = todayPageViews
        userDimension.todayPageViewsByArticle = todayPageViewsByArticle
        userDimension.todayPageViewsByArticleWithPaywall = todayPageViewsByArticleWithPaywall
        userDimension.todayPageViewsByArticleWithRegwall = todayPageViewsByArticleWithRegwall
        userDimension.todayTopLevelSections = todayTopLevelSections
        userDimension.todayTopLevelSectionsByArticle = todayTopLevelSectionsByArticle
        userDimension.sevenDayPageViews = sevenDayPageViews
        userDimension.sevenDayPageViewsByArticle = sevenDayPageViewsByArticle
        userDimension.sevenDayPageViewsByArticleWithPaywall = sevenDayPageViewsByArticleWithPaywall
        userDimension.sevenDayPageViewsByArticleWithRegwall = sevenDayPageViewsByArticleWithRegwall
        userDimension.sevenDayTopLevelSections = sevenDayTopLevelSections
        userDimension.sevenDayTopLevelSectionsByArticle = sevenDayTopLevelSectionsByArticle
        userDimension.sevenDayVisitCount = sevenDayVisitCount
        userDimension.twentyEightDayPageViews = twentyEightDayPageViews
        userDimension.twentyEightDayPageViewsByArticle = twentyEightDayPageViewsByArticle
        userDimension.twentyEightDayPageViewsByArticleWithPaywall = twentyEightDayPageViewsByArticleWithPaywall
        userDimension.twentyEightDayPageViewsByArticleWithRegwall = twentyEightDayPageViewsByArticleWithRegwall
        userDimension.twentyEightDayTopLevelSections = twentyEightDayTopLevelSections
        userDimension.twentyEightDayTopLevelSectionsByArticle = twentyEightDayTopLevelSectionsByArticle
        userDimension.twentyEightDayVisitCount = twentyEightDayVisitCount
        userDimension.daysSinceLastVisit = daysSinceLastVisit
        userDimension.visitorType = visitorType
        userDimension.visitor = visitor
        userDimension.timeZone = timeZone
        userDimension.referrer = referrer
        userDimension.referrerData = referrerData
    }

}