#import "SophiReactNativePaywallKitLegacy.h"
#import <Paywall/Paywall.h>

@implementation SophiReactNativePaywallKitLegacy {
  PaywallPaywallDeciderRepository *_deciderRepository;
  PaywallPaywallDecider *_paywallDecider;
}
RCT_EXPORT_MODULE()


RCT_EXPORT_METHOD(initializePaywallDeciderRepository:(NSDictionary *)userDimensions deviceDimensions:(NSDictionary *)deviceDimensions){
  @try {
    // Convert C++ structs to native objects
    PaywallUserDimensions *userDimensionsData = [PaywallUserDimensionsFactory.shared fromMapData:userDimensions];
    
    PaywallDeviceDimensions *deviceDimensionsData = [PaywallDeviceDimensionsFactory.shared fromMapData:deviceDimensions];
    
    // Create repository using factory method with data
    _deciderRepository = [PaywallPaywallDeciderRepositoryCompanion.shared
                          createNewFromDataUserDimensions:userDimensionsData
                          deviceDimensions:deviceDimensionsData];
  }
  @catch (NSException *exception) {
    NSLog(@"Error initializing PaywallDeciderRepository: %@", exception.reason);
  }
}

RCT_EXPORT_METHOD(getPaywallDeciderConfigByHost:(nonnull NSString *)host
                  settings:(nonnull NSDictionary *)settings
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject){
  if (!_deciderRepository) {
    @throw [NSException exceptionWithName:@"NotInitialized"
                                   reason:@"PaywallDeciderRepository not initialized. Call initializePaywallDeciderRepository first."
                                 userInfo:nil];
  }
  
  _paywallDecider = [_deciderRepository
                     getOneByHostHost:host settings:settings];
  

  PaywallPaywallDeciderConfig *config = _paywallDecider.config;
  
  resolve (@{
    @"host": config.host ?: @"",
    @"settings": config.settings ?: @{},
    @"userDimensions": [self convertUserDimensionsToDictionary:config.userDimensions],
    @"deviceDimensions": [self convertDeviceDimensionsToDictionary:config.deviceDimensions]
  });
}

RCT_EXPORT_METHOD(decide:(nonnull NSString *)contentId
                  assignedGroup:(nonnull NSString *)assignedGroup
                  contentProperties:(NSDictionary *)contentProperties
                  userProperties:(NSDictionary *)userProperties
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject){
  
  if (!_paywallDecider) {
    reject(
           @"initialization_failure",
           @"PaywallDecider not initialized. Call getPaywallDeciderConfigByHost first to initialize the decider.",
           nil
    );
  }

    
  // Call the decide method
  [_paywallDecider decideContentId:contentId assignedGroup:assignedGroup contentProperties:contentProperties userProperties:userProperties completionHandler:^(PaywallWallDecision * _Nullable decision, NSError * _Nullable error) {
    if(error){
      reject(
             @"decision_failure",
             [NSString stringWithFormat:@"Failed to get decision. Reason: %@", error.description], nil
             );
    } else {
      resolve([self convertWallDecisionToDictionary:decision]);
    }
    
  }];
}


RCT_EXPORT_METHOD(updateDimensions:(NSDictionary *)userDimensions
                  deviceDimensions:(NSDictionary *)deviceDimensions
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject){
  
  if (!_paywallDecider) {
    reject(
           @"update_failure",
           @"PaywallDecider is not initialized.",
           nil
    );
  }
  
  NSLog(@"updating dimensions");
  @try{
    PaywallUserDimensions *userDimensionsData = [PaywallUserDimensionsFactory.shared fromMapData:userDimensions];
    PaywallDeviceDimensions *deviceDimensionsData = [PaywallDeviceDimensionsFactory.shared fromMapData:deviceDimensions];
    
    [_paywallDecider updateDimensionsUserDimensions:userDimensionsData deviceDimensions:deviceDimensionsData];
  }
  @catch(NSException *exception) {
    NSLog(@"Error while updating the dimensions: %@", exception.reason);
    reject(
           @"update_failure",
           @"Unable to update the dimension values.",
           nil
    );
  }
  
  resolve(nil);

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

- (NSDictionary *)convertWallDecisionToDictionary:(PaywallWallDecision *)decision {
  NSMutableDictionary *dict = [@{
    @"id": decision.id ?: @"",
    @"createdAt": decision.createdAt ?: @"",
    @"trace": decision.trace ?: [NSNull null],
    @"context": decision.context ?: [NSNull null],
    @"inputs": decision.inputs ?: [NSNull null],
    @"searchParams": decision.searchParams ?: [NSNull null],
    @"experimentsCode": decision.experimentsCode ?: [NSNull null],
    @"paywallScore": decision.experimentsCode ?: [NSNull null],
    @"userProperties": decision.userProperties ?: [NSNull null],
    @"contentProperties": decision.contentProperties ?: [NSNull null]
    
  } mutableCopy];
  
  // Convert outcome
  if (decision.outcome) {
    dict[@"outcome"] = @{
      @"wallType": decision.outcome.wallType ?: [NSNull null],
      @"wallTypeCode": decision.outcome.wallTypeCode ? @(decision.outcome.wallTypeCode.intValue): [NSNull null],
      @"wallVisibility": decision.outcome.wallVisibility ?: @"",
      @"wallVisibilityCode": @(decision.outcome.wallVisibilityCode)
    };
  }
  
  return dict;
}

@end
