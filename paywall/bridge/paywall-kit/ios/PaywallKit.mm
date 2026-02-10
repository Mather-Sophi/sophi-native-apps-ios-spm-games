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
    _deciderRepository = nil; // or initialize it here
  }
  return self;
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params {
  return std::make_shared<facebook::react::NativePaywallKitSpecJSI>(params);
}

- (void)
    initializePaywallDeciderRepository:
        (JS::NativePaywallKit::UserDimensions &)userDimensions
                      deviceDimensions:
                          (JS::NativePaywallKit::DeviceDimensions &)
                              deviceDimensions
                               resolve:(nonnull RCTPromiseResolveBlock)resolve
                                reject:(nonnull RCTPromiseRejectBlock)reject {
  @try {
    // Convert to native objects
    PaywallUserDimensions *nativeUserDims =
        [self convertToUserDimensions:userDimensions];
    PaywallDeviceDimensions *nativeDeviceDims =
        [self convertToDeviceDimensions:deviceDimensions];

    // Create repository using factory method with data
    _deciderRepository = [PaywallPaywallDeciderRepositoryCompanion.shared
        createNewFromDataUserDimensions:nativeUserDims
                       deviceDimensions:nativeDeviceDims];
    resolve(nil);
  } @catch (NSException *exception) {
    NSLog(@"Error initializing PaywallDeciderRepository: %@", exception.reason);
    reject(@"initialization_error", exception.reason, nil);
  }
}

- (void)getPaywallDeciderConfigByHost:(nonnull NSString *)host
                             settings:(nonnull NSDictionary *)settings
                              resolve:(nonnull RCTPromiseResolveBlock)resolve
                               reject:(nonnull RCTPromiseRejectBlock)reject {
  if (!_deciderRepository) {
    reject(@"NotInitialized",
           @"PaywallDeciderRepository not initialized. Call "
           @"initializePaywallDeciderRepository first.",
           nil);
    return;
  }

  _paywallDecider = [_deciderRepository getOneByHostHost:host
                                                settings:settings];

  PaywallPaywallDeciderConfig *config = _paywallDecider.config;

  resolve(@{
    @"host" : config.host ?: @"",
    @"settings" : config.settings ?: @{},
    @"userDimensions" :
        [self convertUserDimensionsToDictionary:config.userDimensions],
    @"deviceDimensions" :
        [self convertDeviceDimensionsToDictionary:config.deviceDimensions]
  });
}

- (void)decide:(nonnull NSString *)contentId
        assignedGroup:(nonnull NSString *)assignedGroup
    contentProperties:(nonnull NSDictionary *)contentProperties
       userProperties:(nonnull NSDictionary *)userProperties
              resolve:(nonnull RCTPromiseResolveBlock)resolve
               reject:(nonnull RCTPromiseRejectBlock)reject {
  if (!_paywallDecider) {
    reject(@"initialization_failure",
           @"PaywallDecider not initialized. Call "
           @"getPaywallDeciderConfigByHost first to initialize the decider.",
           nil);
    return;
  }

  // Call the decide method
  [_paywallDecider
        decideContentId:contentId
          assignedGroup:assignedGroup
      contentProperties:contentProperties
         userProperties:userProperties
      completionHandler:^(PaywallWallDecision *_Nullable decision,
                          NSError *_Nullable error) {
        if (error) {
          reject(
              @"decision_failure",
              [NSString stringWithFormat:@"Failed to get decision. Reason: %@",
                                         error.description],
              nil);
        } else {
          resolve([self convertWallDecisionToDictionary:decision]);
        }
      }];
}

- (void)updateDimensions:(JS::NativePaywallKit::UserDimensions &)userDimensions
        deviceDimensions:
            (JS::NativePaywallKit::DeviceDimensions &)deviceDimensions
                 resolve:(nonnull RCTPromiseResolveBlock)resolve
                  reject:(nonnull RCTPromiseRejectBlock)reject {
  if (!_paywallDecider) {
    reject(@"update_failure", @"PaywallDecider is not initialized.", nil);
    return;
  }

  NSLog(@"updating dimensions");
  @try {

    // Convert to native objects
    PaywallUserDimensions *nativeUserDims =
        [self convertToUserDimensions:userDimensions];
    PaywallDeviceDimensions *nativeDeviceDims =
        [self convertToDeviceDimensions:deviceDimensions];

    [_paywallDecider updateDimensionsUserDimensions:nativeUserDims
                                   deviceDimensions:nativeDeviceDims];
  } @catch (NSException *exception) {
    NSLog(@"Error while updating the dimensions: %@", exception.reason);
    reject(@"update_failure", @"Unable to update the dimension values.", nil);
  }

  resolve(nil);
}

- (NSDictionary *)convertWallDecisionToDictionary:
    (PaywallWallDecision *)decision {
  NSMutableDictionary *dict = [@{
    @"id" : decision.id ?: @"",
    @"createdAt" : decision.createdAt ?: @"",
    @"trace" : decision.trace ?: [NSNull null],
    @"context" : decision.context ?: [NSNull null],
    @"inputs" : decision.inputs ?: [NSNull null],
    @"searchParams" : decision.searchParams ?: [NSNull null],
    @"experimentsCode" : decision.experimentsCode ?: [NSNull null],
    @"paywallScore" : decision.experimentsCode ?: [NSNull null],
    @"userProperties" : decision.userProperties ?: [NSNull null],
    @"contentProperties" : decision.contentProperties ?: [NSNull null]

  } mutableCopy];

  // Convert outcome
  if (decision.outcome) {
    dict[@"outcome"] = @{
      @"wallType" : decision.outcome.wallType ?: [NSNull null],
      @"wallTypeCode" : decision.outcome.wallTypeCode
          ? @(decision.outcome.wallTypeCode.intValue)
          : [NSNull null],
      @"wallVisibility" : decision.outcome.wallVisibility ?: @"",
      @"wallVisibilityCode" : @(decision.outcome.wallVisibilityCode)
    };
  }

  return dict;
}

// #pragma mark - Helper Methods

- (PaywallUserDimensions *)convertToUserDimensions:
    (const JS::NativePaywallKit::UserDimensions &)dims {

  // Convert visitor if present
  PaywallUserDimensionsVisitor *visitor = nil;
  std::optional<JS::NativePaywallKit::Visitor> visitorOpt = dims.visitor();
  if (visitorOpt.has_value()) {
    JS::NativePaywallKit::Visitor visitorValue = visitorOpt.value();
    PaywallUserDimensionsSession *session = nil;

    std::optional<JS::NativePaywallKit::Session> sessionOpt =
        visitorValue.session();
    if (sessionOpt.has_value()) {
      JS::NativePaywallKit::Session sessionValue = sessionOpt.value();

      // Extract optional string fields
      std::optional<NSString *> campaignNameOpt = sessionValue.campaignName();
      NSString *campaignName =
          campaignNameOpt.has_value() ? campaignNameOpt.value() : nil;

      std::optional<NSString *> referrerDomainOpt =
          sessionValue.referrerDomain();
      NSString *referrerDomain =
          referrerDomainOpt.has_value() ? referrerDomainOpt.value() : nil;

      std::optional<NSString *> referrerMediumOpt =
          sessionValue.referrerMedium();
      NSString *referrerMediumStr =
          referrerMediumOpt.has_value() ? referrerMediumOpt.value() : nil;
      PaywallReferrerMedium *referrerMedium =
          [self getPaywallReferrerMediumAsEnum:referrerMediumStr];

      std::optional<NSString *> referrerSourceOpt =
          sessionValue.referrerSource();
      NSString *referrerSourceStr =
          referrerSourceOpt.has_value() ? referrerSourceOpt.value() : nil;
      PaywallReferrerSource *referrerSource =
          [self getPaywallReferrerSourceAsEnum:referrerSourceStr];

      std::optional<NSString *> referrerChannelOpt =
          sessionValue.referrerChannel();
      NSString *referrerChannelStr =
          referrerChannelOpt.has_value() ? referrerChannelOpt.value() : nil;
      PaywallReferrerChannel *referrerChannel =
          [self getPaywallReferrerChannelAsEnum:referrerChannelStr];

      std::optional<NSString *> timestampOpt = sessionValue.timestamp();
      NSString *timestamp =
          timestampOpt.has_value() ? timestampOpt.value() : nil;

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
  std::optional<JS::NativePaywallKit::ReferrerData> refDataOpt =
      dims.referrerData();
  if (refDataOpt.has_value()) {
    JS::NativePaywallKit::ReferrerData refData = refDataOpt.value();

    // Extract and convert enum values
    std::optional<NSString *> mediumOpt = refData.medium();
    NSString *mediumStr = mediumOpt.has_value() ? mediumOpt.value() : nil;
    PaywallReferrerMedium *medium =
        [self getPaywallReferrerMediumAsEnum:mediumStr];
    if (!medium) {
      medium = [PaywallReferrerMedium other];
    }

    std::optional<NSString *> sourceOpt = refData.source();
    NSString *sourceStr = sourceOpt.has_value() ? sourceOpt.value() : nil;
    PaywallReferrerSource *source =
        [self getPaywallReferrerSourceAsEnum:sourceStr];

    std::optional<NSString *> channelOpt = refData.channel();
    NSString *channelStr = channelOpt.has_value() ? channelOpt.value() : nil;
    PaywallReferrerChannel *channel =
        [self getPaywallReferrerChannelAsEnum:channelStr];

    referrerData =
        [[PaywallUserDimensionsReferrerData alloc] initWithMedium:medium
                                                           source:source
                                                          channel:channel
                                                        utmParams:nil];
  }

  std::optional<NSString *> timeZoneOpt = dims.timeZone();
  std::optional<NSString *> referrerOpt = dims.referrer();
  NSString *referrerStr = referrerOpt.has_value() ? referrerOpt.value() : nil;
  PaywallReferrerMedium *referrer =
      [self getPaywallReferrerMediumAsEnum:referrerStr];

  if (!referrer) {
    NSLog(@"Warning: Referrer cannot be null or invalid, using default");
    referrer = [PaywallReferrerMedium other];
  }

  return [[PaywallUserDimensions alloc]
                           initWithTodayPageViews:(int32_t)dims.todayPageViews()
                          todayPageViewsByArticle:(int32_t)dims
                                                      .todayPageViewsByArticle()
               todayPageViewsByArticleWithPaywall:
                   (int32_t)dims.todayPageViewsByArticleWithPaywall()
               todayPageViewsByArticleWithRegwall:
                   (int32_t)dims.todayPageViewsByArticleWithRegwall()
                            todayTopLevelSections:(int32_t)dims
                                                      .todayTopLevelSections()
                   todayTopLevelSectionsByArticle:
                       (int32_t)dims.todayTopLevelSectionsByArticle()
                                sevenDayPageViews:(int32_t)
                                                      dims.sevenDayPageViews()
                       sevenDayPageViewsByArticle:
                           (int32_t)dims.sevenDayPageViewsByArticle()
            sevenDayPageViewsByArticleWithPaywall:
                (int32_t)dims.sevenDayPageViewsByArticleWithPaywall()
            sevenDayPageViewsByArticleWithRegwall:
                (int32_t)dims.sevenDayPageViewsByArticleWithRegwall()
                         sevenDayTopLevelSections:
                             (int32_t)dims.sevenDayTopLevelSections()
                sevenDayTopLevelSectionsByArticle:
                    (int32_t)dims.sevenDayTopLevelSectionsByArticle()
                               sevenDayVisitCount:(int32_t)
                                                      dims.sevenDayVisitCount()
                          twentyEightDayPageViews:(int32_t)dims
                                                      .twentyEightDayPageViews()
                 twentyEightDayPageViewsByArticle:
                     (int32_t)dims.twentyEightDayPageViewsByArticle()
      twentyEightDayPageViewsByArticleWithPaywall:
          (int32_t)dims.twentyEightDayPageViewsByArticleWithPaywall()
      twentyEightDayPageViewsByArticleWithRegwall:
          (int32_t)dims.twentyEightDayPageViewsByArticleWithRegwall()
                   twentyEightDayTopLevelSections:
                       (int32_t)dims.twentyEightDayTopLevelSections()
          twentyEightDayTopLevelSectionsByArticle:
              (int32_t)dims.twentyEightDayTopLevelSectionsByArticle()
                         twentyEightDayVisitCount:
                             (int32_t)dims.twentyEightDayVisitCount()
                               daysSinceLastVisit:(int32_t)
                                                      dims.daysSinceLastVisit()
                                      visitorType:[self getVisitorTypeAsEnum:
                                                            dims.visitorType()]
                                          visitor:visitor
                                         timeZone:timeZoneOpt.has_value()
                                                      ? timeZoneOpt.value()
                                                      : nil
                                         referrer:referrer
                                     referrerData:referrerData];
}

- (PaywallVisitorType *)getVisitorTypeAsEnum:(NSString *)value {
  if (!value) {
    return nil;
  }

  NSString *lowercaseValue = [value lowercaseString];
  if ([lowercaseValue isEqualToString:@"registered"]) {
    return [PaywallVisitorType registered];
  } else if ([lowercaseValue isEqualToString:@"anonymous"]) {
    return [PaywallVisitorType anonymous];
  } else {
    return nil;
  }
}

- (PaywallReferrerSource *)getPaywallReferrerSourceAsEnum:(NSString *)value {
  if (!value) {
    return nil;
  }

  NSString *lowercaseValue = [value lowercaseString];
  if ([lowercaseValue isEqualToString:@"google"]) {
    return [PaywallReferrerSource google];
  } else if ([lowercaseValue isEqualToString:@"yahoo"]) {
    return [PaywallReferrerSource yahoo];
  } else if ([lowercaseValue isEqualToString:@"duckduckgo"]) {
    return [PaywallReferrerSource duckduckgo];
  } else if ([lowercaseValue isEqualToString:@"bing"]) {
    return [PaywallReferrerSource bing];
  } else if ([lowercaseValue isEqualToString:@"facebook"]) {
    return [PaywallReferrerSource facebook];
  } else if ([lowercaseValue isEqualToString:@"instagram"]) {
    return [PaywallReferrerSource instagram];
  } else if ([lowercaseValue isEqualToString:@"x"]) {
    return [PaywallReferrerSource x];
  } else if ([lowercaseValue isEqualToString:@"t"]) {
    return [PaywallReferrerSource t];
  } else if ([lowercaseValue isEqualToString:@"linkedin"]) {
    return [PaywallReferrerSource linkedin];
  } else if ([lowercaseValue isEqualToString:@"reddit"]) {
    return [PaywallReferrerSource reddit];
  } else if ([lowercaseValue isEqualToString:@"newsletter"]) {
    return [PaywallReferrerSource newsletter];
  } else {
    return nil;
  }
}

- (PaywallReferrerMedium *)getPaywallReferrerMediumAsEnum:(NSString *)value {
  if (!value) {
    return nil;
  }

  NSString *lowercaseValue = [value lowercaseString];
  if ([lowercaseValue isEqualToString:@"campaign"]) {
    return [PaywallReferrerMedium campaign];
  } else if ([lowercaseValue isEqualToString:@"direct"]) {
    return [PaywallReferrerMedium direct];
  } else if ([lowercaseValue isEqualToString:@"internal"]) {
    return [PaywallReferrerMedium internal];
  } else if ([lowercaseValue isEqualToString:@"search"]) {
    return [PaywallReferrerMedium search];
  } else if ([lowercaseValue isEqualToString:@"social"]) {
    return [PaywallReferrerMedium social];
  } else if ([lowercaseValue isEqualToString:@"other"]) {
    return [PaywallReferrerMedium other];
  } else {
    return nil;
  }
}

- (PaywallReferrerChannel *)getPaywallReferrerChannelAsEnum:(NSString *)value {
  if (!value) {
    return nil;
  }

  NSString *lowercaseValue = [value lowercaseString];
  if ([lowercaseValue isEqualToString:@"search"]) {
    return [PaywallReferrerChannel search];
  } else if ([lowercaseValue isEqualToString:@"news"]) {
    return [PaywallReferrerChannel news];
  } else if ([lowercaseValue isEqualToString:@"discover"]) {
    return [PaywallReferrerChannel discover];
  } else {
    return nil;
  }
}

- (PaywallDeviceType *)getPaywallDeviceTypeAsEnum:(NSString *)value {
  if (!value) {
    return nil;
  }

  NSString *lowercaseValue = [value lowercaseString];
  if ([lowercaseValue isEqualToString:@"native"]) {
    return [PaywallDeviceType native];
  } else {
    return nil;
  }
}

- (PaywallOSType *)getPaywallOSTypeAsEnum:(NSString *)value {
  if (!value) {
    return nil;
  }

  NSString *lowercaseValue = [value lowercaseString];
  if ([lowercaseValue isEqualToString:@"ios"]) {
    return [PaywallOSType ios];
  } else if ([lowercaseValue isEqualToString:@"android"]) {
    return [PaywallOSType android];
  } else {
    return nil;
  }
}

- (PaywallDeviceDimensions *)convertToDeviceDimensions:
    (const JS::NativePaywallKit::DeviceDimensions &)dims {
  std::optional<NSString *> osOpt = dims.os();
  NSString *osStr = osOpt.has_value() ? osOpt.value() : nil;
  PaywallOSType *os = [self getPaywallOSTypeAsEnum:osStr];

  std::optional<NSString *> typeOpt = dims.type();
  NSString *typeStr = typeOpt.has_value() ? typeOpt.value() : nil;
  PaywallDeviceType *type = [self getPaywallDeviceTypeAsEnum:typeStr];

  std::optional<NSString *> viewerOpt = dims.viewer();
  NSString *viewer = viewerOpt.has_value() ? viewerOpt.value() : nil;

  return [[PaywallDeviceDimensions alloc]
      initWithHourOfDay:(int32_t)dims.hourOfDay()
                     os:os
                   type:type
                 viewer:viewer];
}

- (NSDictionary *)convertUserDimensionsToDictionary:
    (PaywallUserDimensions *)dimensions {
  NSMutableDictionary *dict = [@{
    @"todayPageViews" : @(dimensions.todayPageViews),
    @"todayPageViewsByArticle" : @(dimensions.todayPageViewsByArticle),
    @"todayPageViewsByArticleWithPaywall" :
        @(dimensions.todayPageViewsByArticleWithPaywall),
    @"todayPageViewsByArticleWithRegwall" :
        @(dimensions.todayPageViewsByArticleWithRegwall),
    @"todayTopLevelSections" : @(dimensions.todayTopLevelSections),
    @"todayTopLevelSectionsByArticle" :
        @(dimensions.todayTopLevelSectionsByArticle),
    @"sevenDayPageViews" : @(dimensions.sevenDayPageViews),
    @"sevenDayPageViewsByArticle" : @(dimensions.sevenDayPageViewsByArticle),
    @"sevenDayPageViewsByArticleWithPaywall" :
        @(dimensions.sevenDayPageViewsByArticleWithPaywall),
    @"sevenDayPageViewsByArticleWithRegwall" :
        @(dimensions.sevenDayPageViewsByArticleWithRegwall),
    @"sevenDayTopLevelSections" : @(dimensions.sevenDayTopLevelSections),
    @"sevenDayTopLevelSectionsByArticle" :
        @(dimensions.sevenDayTopLevelSectionsByArticle),
    @"sevenDayVisitCount" : @(dimensions.sevenDayVisitCount),
    @"twentyEightDayPageViews" : @(dimensions.twentyEightDayPageViews),
    @"twentyEightDayPageViewsByArticle" :
        @(dimensions.twentyEightDayPageViewsByArticle),
    @"twentyEightDayPageViewsByArticleWithPaywall" :
        @(dimensions.twentyEightDayPageViewsByArticleWithPaywall),
    @"twentyEightDayPageViewsByArticleWithRegwall" :
        @(dimensions.twentyEightDayPageViewsByArticleWithRegwall),
    @"twentyEightDayTopLevelSections" :
        @(dimensions.twentyEightDayTopLevelSections),
    @"twentyEightDayTopLevelSectionsByArticle" :
        @(dimensions.twentyEightDayTopLevelSectionsByArticle),
    @"twentyEightDayVisitCount" : @(dimensions.twentyEightDayVisitCount),
    @"daysSinceLastVisit" : @(dimensions.daysSinceLastVisit),
    @"visitorType" : dimensions.visitorType ?: @"anonymous"
  } mutableCopy];

  if (dimensions.timeZone) {
    dict[@"timeZone"] = dimensions.timeZone;
  }
  if (dimensions.referrer) {
    dict[@"referrer"] = dimensions.referrer;
  }
  if (dimensions.referrerData) {
    dict[@"referrerData"] = @{
      @"medium" : dimensions.referrerData.medium ?: @"",
      @"source" : dimensions.referrerData.source ?: [NSNull null],
      @"channel" : dimensions.referrerData.channel ?: [NSNull null]
    };
  }
  if (dimensions.visitor && dimensions.visitor.session) {
    PaywallUserDimensionsSession *session = dimensions.visitor.session;
    dict[@"visitor"] = @{
      @"session" : @{
        @"campaignName" : session.campaignName ?: [NSNull null],
        @"referrerDomain" : session.referrerDomain ?: [NSNull null],
        @"referrerMedium" : session.referrerMedium ?: [NSNull null],
        @"referrerSource" : session.referrerSource ?: [NSNull null],
        @"referrerChannel" : session.referrerChannel ?: [NSNull null],
        @"timestamp" : session.timestamp ?: [NSNull null]
      }
    };
  }

  return dict;
}

- (NSDictionary *)convertDeviceDimensionsToDictionary:
    (PaywallDeviceDimensions *)dimensions {
  return @{
    @"hourOfDay" : @(dimensions.hourOfDay),
    @"os" : dimensions.os.value ?: [NSNull null],
    @"type" : dimensions.type.value ?: [NSNull null],
    @"viewer" : dimensions.viewer ?: [NSNull null]
  };
}

@end
