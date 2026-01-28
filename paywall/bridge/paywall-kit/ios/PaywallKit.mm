#import "PaywallKit.h"
#import <paywall/paywall.h>

@implementation PaywallKit {
    PaywallPaywallDeciderRepository *_deciderRepository;
    PaywallPaywallDecider *_paywallDecider;
}

RCT_EXPORT_MODULE()

- (instancetype)init {
    self = [super init];
    if (self) {
        _deciderRepository = nil;  // or initialize it here
    }
    return self;
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativePaywallKitSpecJSI>(params);
}

- (nonnull NSDictionary *)decide:(nonnull NSString *)contentId
                    assignedGroup:(nonnull NSString *)assignedGroup
                    contentProperties:(NSDictionary *)contentProperties
                    userProperties:(NSDictionary *)userProperties{
    // Check if decider is initialized
    if (!_paywallDecider) {
        @throw [NSException exceptionWithName:@"NotInitialized"
                                       reason:@"PaywallDecider not initialized. Call getPaywallDeciderConfigByHost first to initialize the decider."
                                     userInfo:nil];
    }
    
  __block NSDictionary *result = @{};
  dispatch_semaphore_t semaphore = dispatch_semaphore_create(0);

  [_paywallDecider decideContentId:contentId assignedGroup:assignedGroup contentProperties:contentProperties userProperties:userProperties completionHandler:^(PaywallWallDecision * _Nullable decision, NSError * _Nullable error) {
      if (error) {
          NSLog(@"Error deciding paywall: %@", error.localizedDescription);
      } else {
          result = [self convertWallDecisionToDictionary:decision];
      }
      dispatch_semaphore_signal(semaphore);
  }];

  dispatch_semaphore_wait(semaphore, DISPATCH_TIME_FOREVER);
  return result;

}


- (NSDictionary *)convertWallDecisionToDictionary:(PaywallWallDecision *)decision {
    NSMutableDictionary *dict = [@{
        @"id": decision.id ?: @"",
        @"createdAt": decision.createdAt ?: @"",
        @"trace": decision.trace ?: @"",
        @"context": decision.context ?: @"",
        @"inputs": decision.inputs ?: @"",
        @"searchParams": decision.searchParams ?: @""
    } mutableCopy];
    
    // Convert outcome
    if (decision.outcome) {
        dict[@"outcome"] = @{
            @"wallType": decision.outcome.wallType ?: @"",
            @"wallTypeCode": decision.outcome.wallTypeCode ? @(decision.outcome.wallTypeCode.intValue): [NSNull null],
            @"wallVisibility": decision.outcome.wallVisibility ?: @"",
            @"wallVisibilityCode": @(decision.outcome.wallVisibilityCode)
        };
    }
    
    // Convert experiment
    if (decision.experiment) {
        dict[@"experiment"] = @{
            @"experimentId": decision.experiment.experimentId ?: @"",
            @"assignedGroup": decision.experiment.assignedGroup ?: @""
        };
    }
    
    return dict;
}

- (nonnull NSDictionary *)getPaywallDeciderConfigByHost:(nonnull NSString *)host settings:(nonnull NSDictionary *)settings {
    // Check if repository is initialized
    if (!_deciderRepository) {
        @throw [NSException exceptionWithName:@"NotInitialized"
                                       reason:@"PaywallDeciderRepository not initialized. Call initializePaywallDeciderRepository first."
                                     userInfo:nil];
    }

  _paywallDecider = [_deciderRepository
                    getOneByHostHost:host settings:settings];
    
    // Get config from repository
    PaywallPaywallDeciderConfig *config = [_deciderRepository
        getPaywallDeciderConfigByHostHost:host
                                  settings:settings];

    
    // Convert config to dictionary
    return @{
        @"host": config.host ?: @"",
        @"settings": config.settings ?: @{},
        @"userDimensions": [self convertUserDimensionsToDictionary:config.userDimensions],
        @"deviceDimensions": [self convertDeviceDimensionsToDictionary:config.deviceDimensions]
    };
}- (void)initializePaywallDeciderRepository:(JS::NativePaywallKit::UserDimensions &)userDimensions deviceDimensions:(JS::NativePaywallKit::DeviceDimensions &)deviceDimensions { 
    @try {
        // Convert C++ structs to native objects

        PaywallUserDimensions *nativeUserDims = [self convertToUserDimensions:userDimensions];
        PaywallDeviceDimensions *nativeDeviceDims = [self convertToDeviceDimensions:deviceDimensions];
      
        // Create repository using factory method with data
        _deciderRepository = [PaywallPaywallDeciderRepositoryCompanion.shared
            createNewFromDataUserDimensions:nativeUserDims
                           deviceDimensions:nativeDeviceDims];
    }
    @catch (NSException *exception) {
        NSLog(@"Error initializing PaywallDeciderRepository: %@", exception.reason);
    }
}

// #pragma mark - Helper Methods

- (PaywallUserDimensions *)convertToUserDimensions:(const JS::NativePaywallKit::UserDimensions &)dims {
    // Convert visitor if present
    PaywallUserDimensionsVisitor *visitor = nil;
    std::optional<JS::NativePaywallKit::Visitor> visitorOpt = dims.visitor();
    if (visitorOpt.has_value()) {
        JS::NativePaywallKit::Visitor visitorValue = visitorOpt.value();
        PaywallUserDimensionsSession *session = nil;
        
        std::optional<JS::NativePaywallKit::Session> sessionOpt = visitorValue.session();
        if (sessionOpt.has_value()) {
            JS::NativePaywallKit::Session sessionValue = sessionOpt.value();
            
            // Extract optional string fields
            std::optional<NSString *> campaignNameOpt = sessionValue.campaignName();
            NSString *campaignName = campaignNameOpt.has_value() ? campaignNameOpt.value() : nil;
            
            std::optional<NSString *> referrerDomainOpt = sessionValue.referrerDomain();
            NSString *referrerDomain = referrerDomainOpt.has_value() ? referrerDomainOpt.value() : nil;
            
            std::optional<NSString *> referrerMediumOpt = sessionValue.referrerMedium();
            NSString *referrerMedium = referrerMediumOpt.has_value() ? referrerMediumOpt.value() : nil;
            
            std::optional<NSString *> referrerSourceOpt = sessionValue.referrerSource();
            NSString *referrerSource = referrerSourceOpt.has_value() ? referrerSourceOpt.value() : nil;
            
            std::optional<NSString *> referrerChannelOpt = sessionValue.referrerChannel();
            NSString *referrerChannel = referrerChannelOpt.has_value() ? referrerChannelOpt.value() : nil;
            
            std::optional<NSString *> timestampOpt = sessionValue.timestamp();
            NSString *timestamp = timestampOpt.has_value() ? timestampOpt.value() : nil;
            
            session = [[PaywallUserDimensionsSession alloc]
                initWithCampaignName:campaignName
                      referrerDomain:referrerDomain
                      referrerMedium:referrerMedium
                      referrerSource:referrerSource
                     referrerChannel:referrerChannel
                           timestamp:timestamp];
        }
        
        visitor = [[PaywallUserDimensionsVisitor alloc] initWithSession:session];
    }
    
    // Convert referrer data if present
    PaywallUserDimensionsReferrerData *referrerData = nil;
    std::optional<JS::NativePaywallKit::ReferrerData> refDataOpt = dims.referrerData();
    if (refDataOpt.has_value()) {
        JS::NativePaywallKit::ReferrerData refData = refDataOpt.value();
        
        // Extract string values from optional fields
        std::optional<NSString *> mediumOpt = refData.medium();
        NSString *medium = mediumOpt.has_value() ? mediumOpt.value() : nil;
        
        std::optional<NSString *> sourceOpt = refData.source();
        NSString *source = sourceOpt.has_value() ? sourceOpt.value() : nil;
        
        std::optional<NSString *> channelOpt = refData.channel();
        NSString *channel = channelOpt.has_value() ? channelOpt.value() : nil;
        
        referrerData = [[PaywallUserDimensionsReferrerData alloc]
            initWithMedium:medium
                    source:source
                   channel:channel];
    
    }
    
    std::optional<NSString *> timeZoneOpt = dims.timeZone();
    std::optional<NSString *> referrerOpt = dims.referrer();
    std::optional<NSString *> countryCodeOpt = dims.countryCode();
    
    return [[PaywallUserDimensions alloc]
        initWithTodayPageViews:(int32_t)dims.todayPageViews()
        todayPageViewsByArticle:(int32_t)dims.todayPageViewsByArticle()
        todayPageViewsByArticleWithPaywall:(int32_t)dims.todayPageViewsByArticleWithPaywall()
        todayPageViewsByArticleWithRegwall:(int32_t)dims.todayPageViewsByArticleWithRegwall()
        todayTopLevelSections:(int32_t)dims.todayTopLevelSections()
        todayTopLevelSectionsByArticle:(int32_t)dims.todayTopLevelSectionsByArticle()
        sevenDayPageViews:(int32_t)dims.sevenDayPageViews()
        sevenDayPageViewsByArticle:(int32_t)dims.sevenDayPageViewsByArticle()
        sevenDayPageViewsByArticleWithPaywall:(int32_t)dims.sevenDayPageViewsByArticleWithPaywall()
        sevenDayPageViewsByArticleWithRegwall:(int32_t)dims.sevenDayPageViewsByArticleWithRegwall()
        sevenDayTopLevelSections:(int32_t)dims.sevenDayTopLevelSections()
        sevenDayTopLevelSectionsByArticle:(int32_t)dims.sevenDayTopLevelSectionsByArticle()
        sevenDayVisitCount:(int32_t)dims.sevenDayVisitCount()
        twentyEightDayPageViews:(int32_t)dims.twentyEightDayPageViews()
        twentyEightDayPageViewsByArticle:(int32_t)dims.twentyEightDayPageViewsByArticle()
        twentyEightDayPageViewsByArticleWithPaywall:(int32_t)dims.twentyEightDayPageViewsByArticleWithPaywall()
        twentyEightDayPageViewsByArticleWithRegwall:(int32_t)dims.twentyEightDayPageViewsByArticleWithRegwall()
        twentyEightDayTopLevelSections:(int32_t)dims.twentyEightDayTopLevelSections()
        twentyEightDayTopLevelSectionsByArticle:(int32_t)dims.twentyEightDayTopLevelSectionsByArticle()
        twentyEightDayVisitCount:(int32_t)dims.twentyEightDayVisitCount()
        daysSinceLastVisit:(int32_t)dims.daysSinceLastVisit()
        visitorType:dims.visitorType()
        visitor:visitor
        timeZone:timeZoneOpt.has_value() ? timeZoneOpt.value() : nil
        referrer:referrerOpt.has_value() ? referrerOpt.value() : nil
        referrerData:referrerData
        countryCode:countryCodeOpt.has_value() ? countryCodeOpt.value() : nil];
}

- (PaywallDeviceDimensions *)convertToDeviceDimensions:(const JS::NativePaywallKit::DeviceDimensions &)dims {
    std::optional<NSString *> osOpt = dims.os();
    NSString *os = osOpt.has_value() ? osOpt.value() : nil;
    
    std::optional<NSString *> typeOpt = dims.type();
    NSString *type = typeOpt.has_value() ? typeOpt.value() : nil;
    
    std::optional<NSString *> viewerOpt = dims.viewer();
    NSString *viewer = viewerOpt.has_value() ? viewerOpt.value() : nil;
    
    return [[PaywallDeviceDimensions alloc]
        initWithHourOfDay:(int32_t)dims.hourOfDay()
                       os:os
                     type:type
                   viewer:viewer];
}

- (NSDictionary *)convertUserDimensionsToDictionary:(PaywallUserDimensions *)dimensions {
    NSMutableDictionary *dict = [@{
        @"todayPageViews": @(dimensions.todayPageViews),
        @"todayPageViewsByArticle": @(dimensions.todayPageViewsByArticle),
        @"todayPageViewsByArticleWithPaywall": @(dimensions.todayPageViewsByArticleWithPaywall),
        @"todayPageViewsByArticleWithRegwall": @(dimensions.todayPageViewsByArticleWithRegwall),
        @"todayTopLevelSections": @(dimensions.todayTopLevelSections),
        @"todayTopLevelSectionsByArticle": @(dimensions.todayTopLevelSectionsByArticle),
        @"sevenDayPageViews": @(dimensions.sevenDayPageViews),
        @"sevenDayPageViewsByArticle": @(dimensions.sevenDayPageViewsByArticle),
        @"sevenDayPageViewsByArticleWithPaywall": @(dimensions.sevenDayPageViewsByArticleWithPaywall),
        @"sevenDayPageViewsByArticleWithRegwall": @(dimensions.sevenDayPageViewsByArticleWithRegwall),
        @"sevenDayTopLevelSections": @(dimensions.sevenDayTopLevelSections),
        @"sevenDayTopLevelSectionsByArticle": @(dimensions.sevenDayTopLevelSectionsByArticle),
        @"sevenDayVisitCount": @(dimensions.sevenDayVisitCount),
        @"twentyEightDayPageViews": @(dimensions.twentyEightDayPageViews),
        @"twentyEightDayPageViewsByArticle": @(dimensions.twentyEightDayPageViewsByArticle),
        @"twentyEightDayPageViewsByArticleWithPaywall": @(dimensions.twentyEightDayPageViewsByArticleWithPaywall),
        @"twentyEightDayPageViewsByArticleWithRegwall": @(dimensions.twentyEightDayPageViewsByArticleWithRegwall),
        @"twentyEightDayTopLevelSections": @(dimensions.twentyEightDayTopLevelSections),
        @"twentyEightDayTopLevelSectionsByArticle": @(dimensions.twentyEightDayTopLevelSectionsByArticle),
        @"twentyEightDayVisitCount": @(dimensions.twentyEightDayVisitCount),
        @"daysSinceLastVisit": @(dimensions.daysSinceLastVisit),
        @"visitorType": dimensions.visitorType ?: @"anonymous"
    } mutableCopy];
    
    if (dimensions.timeZone) {
        dict[@"timeZone"] = dimensions.timeZone;
    }
    if (dimensions.referrer) {
        dict[@"referrer"] = dimensions.referrer;
    }
    if (dimensions.referrerData) {
        dict[@"referrerData"] = @{
            @"medium": dimensions.referrerData.medium ?: @"",
            @"source": dimensions.referrerData.source ?: [NSNull null],
            @"channel": dimensions.referrerData.channel ?: [NSNull null]
        };
    }
    if (dimensions.visitor && dimensions.visitor.session) {
        PaywallUserDimensionsSession *session = dimensions.visitor.session;
        dict[@"visitor"] = @{
            @"session": @{
                @"campaignName": session.campaignName ?: [NSNull null],
                @"referrerDomain": session.referrerDomain ?: [NSNull null],
                @"referrerMedium": session.referrerMedium ?: [NSNull null],
                @"referrerSource": session.referrerSource ?: [NSNull null],
                @"referrerChannel": session.referrerChannel ?: [NSNull null],
                @"timestamp": session.timestamp ?: [NSNull null]
            }
        };
    }
    
    return dict;
}

- (NSDictionary *)convertDeviceDimensionsToDictionary:(PaywallDeviceDimensions *)dimensions {
    return @{
        @"hourOfDay": @(dimensions.hourOfDay),
        @"os": dimensions.os ?: [NSNull null],
        @"type": dimensions.type ?: [NSNull null],
        @"viewer": dimensions.viewer ?: [NSNull null]
    };
}


@end
