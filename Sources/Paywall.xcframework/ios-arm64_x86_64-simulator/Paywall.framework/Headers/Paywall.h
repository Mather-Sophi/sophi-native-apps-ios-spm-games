#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class PaywallUserDimensionsReferrerData, PaywallVisitorType, PaywallUserDimensionsVisitor, PaywallContextFactory, PaywallContext, PaywallContentDimensions, PaywallUserDimensions, PaywallDeviceDimensions, PaywallDeciderSettings, PaywallWallDecision, PaywallPaywallThresholdsCompanion, PaywallUserThresholds, PaywallPaywallThresholds, PaywallUserThresholdsCompanion, PaywallOutcome, PaywallKotlinx_serialization_jsonJsonElement, PaywallResponseCompanion, PaywallResponse, PaywallKotlinEnumCompanion, PaywallKotlinEnum<E>, PaywallExperimentGroup, PaywallKotlinArray<T>, PaywallExperimentPhase, PaywallReferrerChannel, PaywallReferrerMedium, PaywallReferrerSource, PaywallReferrerType, PaywallWallTypeCompanion, PaywallWallType, PaywallWallVisibilityCompanion, PaywallWallVisibility, PaywallDecision, PaywallDeviceDimensionsFactory, PaywallExperimentCompanion, PaywallExperiment, PaywallOutcomeCompanion, PaywallUserDimensionsFactory, PaywallUserDimensionsReferrerDataFactory, PaywallUserDimensionsSessionFactory, PaywallUserDimensionsSession, PaywallUserDimensionsVisitorFactory, PaywallWallDecisionCompanion, PaywallDeciderSettingsCompanion, PaywallPaywallDeciderConfigCompanion, PaywallPaywallDeciderConfig, PaywallPaywallDeciderRepositoryCompanion, PaywallPaywallDecider, PaywallPaywallDeciderRepository, PaywallDeviceType, PaywallIOSNetworkManager, PaywallKotlinThrowable, PaywallKotlinException, PaywallKotlinRuntimeException, PaywallKotlinIllegalStateException, PaywallKotlinx_serialization_jsonJsonElementCompanion, PaywallKotlinx_serialization_coreSerializersModule, PaywallKotlinx_serialization_coreSerialKind, PaywallKotlinNothing;

@protocol PaywallKotlinx_serialization_coreKSerializer, PaywallKotlinComparable, PaywallNetworkManager, PaywallUserDimensionRepository, PaywallDeviceDimensionRepository, PaywallDeviceInfo, PaywallKotlinx_serialization_coreEncoder, PaywallKotlinx_serialization_coreSerialDescriptor, PaywallKotlinx_serialization_coreSerializationStrategy, PaywallKotlinx_serialization_coreDecoder, PaywallKotlinx_serialization_coreDeserializationStrategy, PaywallKotlinIterator, PaywallKotlinx_serialization_coreCompositeEncoder, PaywallKotlinAnnotation, PaywallKotlinx_serialization_coreCompositeDecoder, PaywallKotlinx_serialization_coreSerializersModuleCollector, PaywallKotlinKClass, PaywallKotlinKDeclarationContainer, PaywallKotlinKAnnotatedElement, PaywallKotlinKClassifier;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface PaywallBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface PaywallBase (PaywallBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface PaywallMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface PaywallMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorPaywallKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface PaywallNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end

__attribute__((swift_name("KotlinByte")))
@interface PaywallByte : PaywallNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface PaywallUByte : PaywallNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface PaywallShort : PaywallNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface PaywallUShort : PaywallNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface PaywallInt : PaywallNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface PaywallUInt : PaywallNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface PaywallLong : PaywallNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface PaywallULong : PaywallNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface PaywallFloat : PaywallNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface PaywallDouble : PaywallNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface PaywallBoolean : PaywallNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Context")))
@interface PaywallContext : PaywallBase
- (instancetype)initWithTodayPageViews:(int32_t)todayPageViews todayPageViewsByArticle:(int32_t)todayPageViewsByArticle todayPageViewsByArticleWithPaywall:(int32_t)todayPageViewsByArticleWithPaywall todayPageViewsByArticleWithRegwall:(int32_t)todayPageViewsByArticleWithRegwall todayTopLevelSections:(int32_t)todayTopLevelSections todayTopLevelSectionsByArticle:(int32_t)todayTopLevelSectionsByArticle sevenDayPageViews:(int32_t)sevenDayPageViews sevenDayPageViewsByArticle:(int32_t)sevenDayPageViewsByArticle sevenDayPageViewsByArticleWithPaywall:(int32_t)sevenDayPageViewsByArticleWithPaywall sevenDayPageViewsByArticleWithRegwall:(int32_t)sevenDayPageViewsByArticleWithRegwall sevenDayTopLevelSections:(int32_t)sevenDayTopLevelSections sevenDayTopLevelSectionsByArticle:(int32_t)sevenDayTopLevelSectionsByArticle sevenDayVisitCount:(int32_t)sevenDayVisitCount twentyEightDayPageViews:(int32_t)twentyEightDayPageViews twentyEightDayPageViewsByArticle:(int32_t)twentyEightDayPageViewsByArticle twentyEightDayPageViewsByArticleWithPaywall:(int32_t)twentyEightDayPageViewsByArticleWithPaywall twentyEightDayPageViewsByArticleWithRegwall:(int32_t)twentyEightDayPageViewsByArticleWithRegwall twentyEightDayTopLevelSections:(int32_t)twentyEightDayTopLevelSections twentyEightDayTopLevelSectionsByArticle:(int32_t)twentyEightDayTopLevelSectionsByArticle twentyEightDayVisitCount:(int32_t)twentyEightDayVisitCount daysSinceLastVisit:(int32_t)daysSinceLastVisit timeZone:(NSString *)timeZone referrer:(NSString *)referrer referrerData:(PaywallUserDimensionsReferrerData * _Nullable)referrerData hourOfDay:(int32_t)hourOfDay os:(NSString * _Nullable)os type:(NSString * _Nullable)type viewer:(NSString * _Nullable)viewer visitorType:(PaywallVisitorType *)visitorType visitor:(PaywallUserDimensionsVisitor * _Nullable)visitor assignedGroup:(NSString * _Nullable)assignedGroup userProperties:(NSDictionary<NSString *, id> * _Nullable)userProperties contentProperties:(NSDictionary<NSString *, id> * _Nullable)contentProperties __attribute__((swift_name("init(todayPageViews:todayPageViewsByArticle:todayPageViewsByArticleWithPaywall:todayPageViewsByArticleWithRegwall:todayTopLevelSections:todayTopLevelSectionsByArticle:sevenDayPageViews:sevenDayPageViewsByArticle:sevenDayPageViewsByArticleWithPaywall:sevenDayPageViewsByArticleWithRegwall:sevenDayTopLevelSections:sevenDayTopLevelSectionsByArticle:sevenDayVisitCount:twentyEightDayPageViews:twentyEightDayPageViewsByArticle:twentyEightDayPageViewsByArticleWithPaywall:twentyEightDayPageViewsByArticleWithRegwall:twentyEightDayTopLevelSections:twentyEightDayTopLevelSectionsByArticle:twentyEightDayVisitCount:daysSinceLastVisit:timeZone:referrer:referrerData:hourOfDay:os:type:viewer:visitorType:visitor:assignedGroup:userProperties:contentProperties:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallContextFactory *companion __attribute__((swift_name("companion")));
- (NSString *)getContext __attribute__((swift_name("getContext()")));
- (NSString *)getInputs __attribute__((swift_name("getInputs()")));
@property NSString * _Nullable assignedGroup __attribute__((swift_name("assignedGroup")));
@property (readonly) NSDictionary<NSString *, id> * _Nullable contentProperties __attribute__((swift_name("contentProperties")));
@property int32_t daysSinceLastVisit __attribute__((swift_name("daysSinceLastVisit")));
@property int32_t hourOfDay __attribute__((swift_name("hourOfDay")));
@property NSString * _Nullable os __attribute__((swift_name("os")));
@property NSString *referrer __attribute__((swift_name("referrer")));
@property PaywallUserDimensionsReferrerData * _Nullable referrerData __attribute__((swift_name("referrerData")));
@property int32_t sevenDayPageViews __attribute__((swift_name("sevenDayPageViews")));
@property int32_t sevenDayPageViewsByArticle __attribute__((swift_name("sevenDayPageViewsByArticle")));
@property int32_t sevenDayPageViewsByArticleWithPaywall __attribute__((swift_name("sevenDayPageViewsByArticleWithPaywall")));
@property int32_t sevenDayPageViewsByArticleWithRegwall __attribute__((swift_name("sevenDayPageViewsByArticleWithRegwall")));
@property int32_t sevenDayTopLevelSections __attribute__((swift_name("sevenDayTopLevelSections")));
@property int32_t sevenDayTopLevelSectionsByArticle __attribute__((swift_name("sevenDayTopLevelSectionsByArticle")));
@property int32_t sevenDayVisitCount __attribute__((swift_name("sevenDayVisitCount")));
@property NSString *timeZone __attribute__((swift_name("timeZone")));
@property int32_t todayPageViews __attribute__((swift_name("todayPageViews")));
@property int32_t todayPageViewsByArticle __attribute__((swift_name("todayPageViewsByArticle")));
@property int32_t todayPageViewsByArticleWithPaywall __attribute__((swift_name("todayPageViewsByArticleWithPaywall")));
@property int32_t todayPageViewsByArticleWithRegwall __attribute__((swift_name("todayPageViewsByArticleWithRegwall")));
@property int32_t todayTopLevelSections __attribute__((swift_name("todayTopLevelSections")));
@property int32_t todayTopLevelSectionsByArticle __attribute__((swift_name("todayTopLevelSectionsByArticle")));
@property int32_t twentyEightDayPageViews __attribute__((swift_name("twentyEightDayPageViews")));
@property int32_t twentyEightDayPageViewsByArticle __attribute__((swift_name("twentyEightDayPageViewsByArticle")));
@property int32_t twentyEightDayPageViewsByArticleWithPaywall __attribute__((swift_name("twentyEightDayPageViewsByArticleWithPaywall")));
@property int32_t twentyEightDayPageViewsByArticleWithRegwall __attribute__((swift_name("twentyEightDayPageViewsByArticleWithRegwall")));
@property int32_t twentyEightDayTopLevelSections __attribute__((swift_name("twentyEightDayTopLevelSections")));
@property int32_t twentyEightDayTopLevelSectionsByArticle __attribute__((swift_name("twentyEightDayTopLevelSectionsByArticle")));
@property int32_t twentyEightDayVisitCount __attribute__((swift_name("twentyEightDayVisitCount")));
@property NSString * _Nullable type __attribute__((swift_name("type")));
@property NSDictionary<NSString *, id> * _Nullable userProperties __attribute__((swift_name("userProperties")));
@property NSString * _Nullable viewer __attribute__((swift_name("viewer")));
@property PaywallUserDimensionsVisitor * _Nullable visitor __attribute__((swift_name("visitor")));
@property PaywallVisitorType *visitorType __attribute__((swift_name("visitorType")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Context.Factory")))
@interface PaywallContextFactory : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)factory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallContextFactory *shared __attribute__((swift_name("shared")));
- (PaywallContext *)doNewFromUserAndDeviceDimensionsContentDimensions:(PaywallContentDimensions *)contentDimensions userDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions userProperties:(NSDictionary<NSString *, id> * _Nullable)userProperties assignedGroup:(NSString * _Nullable)assignedGroup __attribute__((swift_name("doNewFromUserAndDeviceDimensions(contentDimensions:userDimensions:deviceDimensions:userProperties:assignedGroup:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("OnDeviceClient")))
@interface PaywallOnDeviceClient : PaywallBase
- (instancetype)initWithHost:(NSString *)host settings:(PaywallDeciderSettings *)settings __attribute__((swift_name("init(host:settings:)"))) __attribute__((objc_designated_initializer));
- (PaywallWallDecision *)getDecisionUserDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions contentDimensions:(PaywallContentDimensions *)contentDimensions userProperties:(NSDictionary<NSString *, id> * _Nullable)userProperties assignedGroup:(NSString * _Nullable)assignedGroup __attribute__((swift_name("getDecision(userDimensions:deviceDimensions:contentDimensions:userProperties:assignedGroup:)")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallThresholds")))
@interface PaywallPaywallThresholds : PaywallBase
- (instancetype)initWithAnonymous:(PaywallDouble * _Nullable)anonymous registered:(PaywallDouble * _Nullable)registered __attribute__((swift_name("init(anonymous:registered:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallPaywallThresholdsCompanion *companion __attribute__((swift_name("companion")));
@property (readonly) PaywallDouble * _Nullable anonymous __attribute__((swift_name("anonymous")));
@property (readonly) PaywallDouble * _Nullable registered __attribute__((swift_name("registered")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallThresholds.Companion")))
@interface PaywallPaywallThresholdsCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallPaywallThresholdsCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Policy")))
@interface PaywallPolicy : PaywallBase
- (instancetype)initWithThresholds:(NSDictionary<NSString *, PaywallUserThresholds *> *)thresholds __attribute__((swift_name("init(thresholds:)"))) __attribute__((objc_designated_initializer));
- (PaywallUserThresholds * _Nullable)getThresholdExperimentGroup:(NSString *)experimentGroup __attribute__((swift_name("getThreshold(experimentGroup:)")));
@property (readonly) NSDictionary<NSString *, PaywallUserThresholds *> *thresholds __attribute__((swift_name("thresholds")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserThresholds")))
@interface PaywallUserThresholds : PaywallBase
- (instancetype)initWithPaywall:(PaywallPaywallThresholds *)paywall regwall:(PaywallDouble * _Nullable)regwall __attribute__((swift_name("init(paywall:regwall:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallUserThresholdsCompanion *companion __attribute__((swift_name("companion")));
@property (readonly) PaywallPaywallThresholds *paywall __attribute__((swift_name("paywall")));
@property (readonly) PaywallDouble * _Nullable regwall __attribute__((swift_name("regwall")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserThresholds.Companion")))
@interface PaywallUserThresholdsCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallUserThresholdsCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallApiClient")))
@interface PaywallPaywallApiClient : PaywallBase
- (instancetype)initWithHost:(NSString *)host settings:(PaywallDeciderSettings *)settings __attribute__((swift_name("init(host:settings:)"))) __attribute__((objc_designated_initializer));
- (void)close __attribute__((swift_name("close()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)getDecisionContentDimensions:(PaywallContentDimensions *)contentDimensions userDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions assignedGroup:(NSString * _Nullable)assignedGroup onDeviceTrace:(NSString * _Nullable)onDeviceTrace userProperties:(NSDictionary<NSString *, id> * _Nullable)userProperties completionHandler:(void (^)(PaywallWallDecision * _Nullable_result, NSError * _Nullable))completionHandler __attribute__((swift_name("getDecision(contentDimensions:userDimensions:deviceDimensions:assignedGroup:onDeviceTrace:userProperties:completionHandler:)")));
@property (readonly) NSString *host __attribute__((swift_name("host")));
@property (readonly) PaywallDeciderSettings *settings __attribute__((swift_name("settings")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Response")))
@interface PaywallResponse : PaywallBase
- (instancetype)initWithId:(NSString *)id outcome:(PaywallOutcome *)outcome trace:(NSString *)trace createdAt:(NSString *)createdAt jsonContext:(NSDictionary<NSString *, PaywallKotlinx_serialization_jsonJsonElement *> * _Nullable)jsonContext __attribute__((swift_name("init(id:outcome:trace:createdAt:jsonContext:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallResponseCompanion *companion __attribute__((swift_name("companion")));
- (PaywallResponse *)doCopyId:(NSString *)id outcome:(PaywallOutcome *)outcome trace:(NSString *)trace createdAt:(NSString *)createdAt jsonContext:(NSDictionary<NSString *, PaywallKotlinx_serialization_jsonJsonElement *> * _Nullable)jsonContext __attribute__((swift_name("doCopy(id:outcome:trace:createdAt:jsonContext:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSString *)getContextAsQueryString __attribute__((swift_name("getContextAsQueryString()")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSDictionary<NSString *, NSString *> *context __attribute__((swift_name("context")));
@property (readonly) NSString *createdAt __attribute__((swift_name("createdAt")));
@property (readonly) NSString *id __attribute__((swift_name("id")));

/**
 * @note annotations
 *   kotlinx.serialization.SerialName(value="context")
*/
@property (readonly) NSDictionary<NSString *, PaywallKotlinx_serialization_jsonJsonElement *> * _Nullable jsonContext __attribute__((swift_name("jsonContext")));
@property (readonly) PaywallOutcome *outcome __attribute__((swift_name("outcome")));
@property (readonly) NSString *trace __attribute__((swift_name("trace")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Response.Companion")))
@interface PaywallResponseCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallResponseCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((swift_name("KotlinComparable")))
@protocol PaywallKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end

__attribute__((swift_name("KotlinEnum")))
@interface PaywallKotlinEnum<E> : PaywallBase <PaywallKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallKotlinEnumCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExperimentGroup")))
@interface PaywallExperimentGroup : PaywallKotlinEnum<PaywallExperimentGroup *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallExperimentGroup *control __attribute__((swift_name("control")));
@property (class, readonly) PaywallExperimentGroup *variant __attribute__((swift_name("variant")));
@property (class, readonly) PaywallExperimentGroup *holdout __attribute__((swift_name("holdout")));
+ (PaywallKotlinArray<PaywallExperimentGroup *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallExperimentGroup *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExperimentPhase")))
@interface PaywallExperimentPhase : PaywallKotlinEnum<PaywallExperimentPhase *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallExperimentPhase *aa __attribute__((swift_name("aa")));
@property (class, readonly) PaywallExperimentPhase *ab __attribute__((swift_name("ab")));
+ (PaywallKotlinArray<PaywallExperimentPhase *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallExperimentPhase *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ReferrerChannel")))
@interface PaywallReferrerChannel : PaywallKotlinEnum<PaywallReferrerChannel *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallReferrerChannel *search __attribute__((swift_name("search")));
@property (class, readonly) PaywallReferrerChannel *news __attribute__((swift_name("news")));
@property (class, readonly) PaywallReferrerChannel *discover __attribute__((swift_name("discover")));
+ (PaywallKotlinArray<PaywallReferrerChannel *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallReferrerChannel *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ReferrerMedium")))
@interface PaywallReferrerMedium : PaywallKotlinEnum<PaywallReferrerMedium *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallReferrerMedium *campaign __attribute__((swift_name("campaign")));
@property (class, readonly) PaywallReferrerMedium *direct __attribute__((swift_name("direct")));
@property (class, readonly) PaywallReferrerMedium *internal __attribute__((swift_name("internal")));
@property (class, readonly) PaywallReferrerMedium *search __attribute__((swift_name("search")));
@property (class, readonly) PaywallReferrerMedium *social __attribute__((swift_name("social")));
@property (class, readonly) PaywallReferrerMedium *other __attribute__((swift_name("other")));
+ (PaywallKotlinArray<PaywallReferrerMedium *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallReferrerMedium *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ReferrerSource")))
@interface PaywallReferrerSource : PaywallKotlinEnum<PaywallReferrerSource *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallReferrerSource *google __attribute__((swift_name("google")));
@property (class, readonly) PaywallReferrerSource *yahoo __attribute__((swift_name("yahoo")));
@property (class, readonly) PaywallReferrerSource *duckduckgo __attribute__((swift_name("duckduckgo")));
@property (class, readonly) PaywallReferrerSource *bing __attribute__((swift_name("bing")));
@property (class, readonly) PaywallReferrerSource *facebook __attribute__((swift_name("facebook")));
@property (class, readonly) PaywallReferrerSource *instagram __attribute__((swift_name("instagram")));
@property (class, readonly) PaywallReferrerSource *x __attribute__((swift_name("x")));
@property (class, readonly) PaywallReferrerSource *t __attribute__((swift_name("t")));
@property (class, readonly) PaywallReferrerSource *linkedin __attribute__((swift_name("linkedin")));
@property (class, readonly) PaywallReferrerSource *reddit __attribute__((swift_name("reddit")));
@property (class, readonly) PaywallReferrerSource *newsletter __attribute__((swift_name("newsletter")));
+ (PaywallKotlinArray<PaywallReferrerSource *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallReferrerSource *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ReferrerType")))
@interface PaywallReferrerType : PaywallKotlinEnum<PaywallReferrerType *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallReferrerType *campaign __attribute__((swift_name("campaign")));
@property (class, readonly) PaywallReferrerType *direct __attribute__((swift_name("direct")));
@property (class, readonly) PaywallReferrerType *internal __attribute__((swift_name("internal")));
@property (class, readonly) PaywallReferrerType *search __attribute__((swift_name("search")));
@property (class, readonly) PaywallReferrerType *social __attribute__((swift_name("social")));
@property (class, readonly) PaywallReferrerType *other __attribute__((swift_name("other")));
@property (class, readonly) PaywallReferrerType *none __attribute__((swift_name("none")));
+ (PaywallKotlinArray<PaywallReferrerType *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallReferrerType *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("VisitorType")))
@interface PaywallVisitorType : PaywallKotlinEnum<PaywallVisitorType *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallVisitorType *anonymous __attribute__((swift_name("anonymous")));
@property (class, readonly) PaywallVisitorType *registered __attribute__((swift_name("registered")));
+ (PaywallKotlinArray<PaywallVisitorType *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallVisitorType *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WallType")))
@interface PaywallWallType : PaywallKotlinEnum<PaywallWallType *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly, getter=companion) PaywallWallTypeCompanion *companion __attribute__((swift_name("companion")));
@property (class, readonly) PaywallWallType *nowall __attribute__((swift_name("nowall")));
@property (class, readonly) PaywallWallType *paywall __attribute__((swift_name("paywall")));
@property (class, readonly) PaywallWallType *regwall __attribute__((swift_name("regwall")));
+ (PaywallKotlinArray<PaywallWallType *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallWallType *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *traceCode __attribute__((swift_name("traceCode")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WallType.Companion")))
@interface PaywallWallTypeCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallWallTypeCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializerTypeParamsSerializers:(PaywallKotlinArray<id<PaywallKotlinx_serialization_coreKSerializer>> *)typeParamsSerializers __attribute__((swift_name("serializer(typeParamsSerializers:)")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WallVisibility")))
@interface PaywallWallVisibility : PaywallKotlinEnum<PaywallWallVisibility *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly, getter=companion) PaywallWallVisibilityCompanion *companion __attribute__((swift_name("companion")));
@property (class, readonly) PaywallWallVisibility *never __attribute__((swift_name("never")));
@property (class, readonly) PaywallWallVisibility *always __attribute__((swift_name("always")));
@property (class, readonly) PaywallWallVisibility *meter __attribute__((swift_name("meter")));
+ (PaywallKotlinArray<PaywallWallVisibility *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallWallVisibility *> *entries __attribute__((swift_name("entries")));
@property (readonly) int32_t code __attribute__((swift_name("code")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WallVisibility.Companion")))
@interface PaywallWallVisibilityCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallWallVisibilityCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializerTypeParamsSerializers:(PaywallKotlinArray<id<PaywallKotlinx_serialization_coreKSerializer>> *)typeParamsSerializers __attribute__((swift_name("serializer(typeParamsSerializers:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ContentDimensions")))
@interface PaywallContentDimensions : PaywallBase
- (instancetype)initWithId:(NSString *)id __attribute__((swift_name("init(id:)"))) __attribute__((objc_designated_initializer));
- (PaywallContentDimensions *)doCopyId:(NSString *)id __attribute__((swift_name("doCopy(id:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *id __attribute__((swift_name("id")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Decision")))
@interface PaywallDecision : PaywallBase
- (instancetype)initWithId:(NSString *)id outcome:(PaywallOutcome *)outcome trace:(NSString * _Nullable)trace createdAt:(NSString *)createdAt context:(NSDictionary<NSString *, id> *)context __attribute__((swift_name("init(id:outcome:trace:createdAt:context:)"))) __attribute__((objc_designated_initializer));
- (PaywallDecision *)doCopyId:(NSString *)id outcome:(PaywallOutcome *)outcome trace:(NSString * _Nullable)trace createdAt:(NSString *)createdAt context:(NSDictionary<NSString *, id> *)context __attribute__((swift_name("doCopy(id:outcome:trace:createdAt:context:)")));
- (NSString *)getContextCodes __attribute__((swift_name("getContextCodes()")));
- (NSString *)getInputsCodes __attribute__((swift_name("getInputsCodes()")));
@property (readonly) NSDictionary<NSString *, id> *context __attribute__((swift_name("context")));
@property (readonly) NSString *createdAt __attribute__((swift_name("createdAt")));
@property (readonly) NSString *id __attribute__((swift_name("id")));
@property (readonly) PaywallOutcome *outcome __attribute__((swift_name("outcome")));
@property (readonly) NSString * _Nullable trace __attribute__((swift_name("trace")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DeviceDimensions")))
@interface PaywallDeviceDimensions : PaywallBase
- (instancetype)initWithHourOfDay:(int32_t)hourOfDay os:(NSString * _Nullable)os type:(NSString * _Nullable)type viewer:(NSString * _Nullable)viewer __attribute__((swift_name("init(hourOfDay:os:type:viewer:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallDeviceDimensionsFactory *companion __attribute__((swift_name("companion")));
- (PaywallDeviceDimensions *)doCopyHourOfDay:(int32_t)hourOfDay os:(NSString * _Nullable)os type:(NSString * _Nullable)type viewer:(NSString * _Nullable)viewer __attribute__((swift_name("doCopy(hourOfDay:os:type:viewer:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property int32_t hourOfDay __attribute__((swift_name("hourOfDay")));
@property NSString * _Nullable os __attribute__((swift_name("os")));
@property NSString * _Nullable type __attribute__((swift_name("type")));
@property NSString * _Nullable viewer __attribute__((swift_name("viewer")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DeviceDimensions.Factory")))
@interface PaywallDeviceDimensionsFactory : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)factory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallDeviceDimensionsFactory *shared __attribute__((swift_name("shared")));
- (PaywallDeviceDimensions *)fromMapData:(NSDictionary<NSString *, id> *)data __attribute__((swift_name("fromMap(data:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Experiment")))
@interface PaywallExperiment : PaywallBase
- (instancetype)initWithExperimentId:(NSString *)experimentId assignedGroup:(NSString *)assignedGroup __attribute__((swift_name("init(experimentId:assignedGroup:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallExperimentCompanion *companion __attribute__((swift_name("companion")));
- (PaywallExperiment *)doCopyExperimentId:(NSString *)experimentId assignedGroup:(NSString *)assignedGroup __attribute__((swift_name("doCopy(experimentId:assignedGroup:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *assignedGroup __attribute__((swift_name("assignedGroup")));
@property (readonly) NSString *experimentId __attribute__((swift_name("experimentId")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Experiment.Companion")))
@interface PaywallExperimentCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallExperimentCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Outcome")))
@interface PaywallOutcome : PaywallBase
- (instancetype)initWithWallType:(PaywallWallType * _Nullable)wallType wallVisibility:(PaywallWallVisibility *)wallVisibility __attribute__((swift_name("init(wallType:wallVisibility:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallOutcomeCompanion *companion __attribute__((swift_name("companion")));
- (PaywallOutcome *)doCopyWallType:(PaywallWallType * _Nullable)wallType wallVisibility:(PaywallWallVisibility *)wallVisibility __attribute__((swift_name("doCopy(wallType:wallVisibility:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) PaywallWallType * _Nullable wallType __attribute__((swift_name("wallType")));
@property (readonly) PaywallInt * _Nullable wallTypeCode __attribute__((swift_name("wallTypeCode")));
@property (readonly) PaywallWallVisibility *wallVisibility __attribute__((swift_name("wallVisibility")));
@property (readonly) int32_t wallVisibilityCode __attribute__((swift_name("wallVisibilityCode")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Outcome.Companion")))
@interface PaywallOutcomeCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallOutcomeCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions")))
@interface PaywallUserDimensions : PaywallBase
- (instancetype)initWithTodayPageViews:(int32_t)todayPageViews todayPageViewsByArticle:(int32_t)todayPageViewsByArticle todayPageViewsByArticleWithPaywall:(int32_t)todayPageViewsByArticleWithPaywall todayPageViewsByArticleWithRegwall:(int32_t)todayPageViewsByArticleWithRegwall todayTopLevelSections:(int32_t)todayTopLevelSections todayTopLevelSectionsByArticle:(int32_t)todayTopLevelSectionsByArticle sevenDayPageViews:(int32_t)sevenDayPageViews sevenDayPageViewsByArticle:(int32_t)sevenDayPageViewsByArticle sevenDayPageViewsByArticleWithPaywall:(int32_t)sevenDayPageViewsByArticleWithPaywall sevenDayPageViewsByArticleWithRegwall:(int32_t)sevenDayPageViewsByArticleWithRegwall sevenDayTopLevelSections:(int32_t)sevenDayTopLevelSections sevenDayTopLevelSectionsByArticle:(int32_t)sevenDayTopLevelSectionsByArticle sevenDayVisitCount:(int32_t)sevenDayVisitCount twentyEightDayPageViews:(int32_t)twentyEightDayPageViews twentyEightDayPageViewsByArticle:(int32_t)twentyEightDayPageViewsByArticle twentyEightDayPageViewsByArticleWithPaywall:(int32_t)twentyEightDayPageViewsByArticleWithPaywall twentyEightDayPageViewsByArticleWithRegwall:(int32_t)twentyEightDayPageViewsByArticleWithRegwall twentyEightDayTopLevelSections:(int32_t)twentyEightDayTopLevelSections twentyEightDayTopLevelSectionsByArticle:(int32_t)twentyEightDayTopLevelSectionsByArticle twentyEightDayVisitCount:(int32_t)twentyEightDayVisitCount daysSinceLastVisit:(int32_t)daysSinceLastVisit visitorType:(PaywallVisitorType *)visitorType visitor:(PaywallUserDimensionsVisitor * _Nullable)visitor timeZone:(NSString * _Nullable)timeZone referrer:(NSString * _Nullable)referrer referrerData:(PaywallUserDimensionsReferrerData * _Nullable)referrerData __attribute__((swift_name("init(todayPageViews:todayPageViewsByArticle:todayPageViewsByArticleWithPaywall:todayPageViewsByArticleWithRegwall:todayTopLevelSections:todayTopLevelSectionsByArticle:sevenDayPageViews:sevenDayPageViewsByArticle:sevenDayPageViewsByArticleWithPaywall:sevenDayPageViewsByArticleWithRegwall:sevenDayTopLevelSections:sevenDayTopLevelSectionsByArticle:sevenDayVisitCount:twentyEightDayPageViews:twentyEightDayPageViewsByArticle:twentyEightDayPageViewsByArticleWithPaywall:twentyEightDayPageViewsByArticleWithRegwall:twentyEightDayTopLevelSections:twentyEightDayTopLevelSectionsByArticle:twentyEightDayVisitCount:daysSinceLastVisit:visitorType:visitor:timeZone:referrer:referrerData:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallUserDimensionsFactory *companion __attribute__((swift_name("companion")));
- (PaywallUserDimensions *)doCopyTodayPageViews:(int32_t)todayPageViews todayPageViewsByArticle:(int32_t)todayPageViewsByArticle todayPageViewsByArticleWithPaywall:(int32_t)todayPageViewsByArticleWithPaywall todayPageViewsByArticleWithRegwall:(int32_t)todayPageViewsByArticleWithRegwall todayTopLevelSections:(int32_t)todayTopLevelSections todayTopLevelSectionsByArticle:(int32_t)todayTopLevelSectionsByArticle sevenDayPageViews:(int32_t)sevenDayPageViews sevenDayPageViewsByArticle:(int32_t)sevenDayPageViewsByArticle sevenDayPageViewsByArticleWithPaywall:(int32_t)sevenDayPageViewsByArticleWithPaywall sevenDayPageViewsByArticleWithRegwall:(int32_t)sevenDayPageViewsByArticleWithRegwall sevenDayTopLevelSections:(int32_t)sevenDayTopLevelSections sevenDayTopLevelSectionsByArticle:(int32_t)sevenDayTopLevelSectionsByArticle sevenDayVisitCount:(int32_t)sevenDayVisitCount twentyEightDayPageViews:(int32_t)twentyEightDayPageViews twentyEightDayPageViewsByArticle:(int32_t)twentyEightDayPageViewsByArticle twentyEightDayPageViewsByArticleWithPaywall:(int32_t)twentyEightDayPageViewsByArticleWithPaywall twentyEightDayPageViewsByArticleWithRegwall:(int32_t)twentyEightDayPageViewsByArticleWithRegwall twentyEightDayTopLevelSections:(int32_t)twentyEightDayTopLevelSections twentyEightDayTopLevelSectionsByArticle:(int32_t)twentyEightDayTopLevelSectionsByArticle twentyEightDayVisitCount:(int32_t)twentyEightDayVisitCount daysSinceLastVisit:(int32_t)daysSinceLastVisit visitorType:(PaywallVisitorType *)visitorType visitor:(PaywallUserDimensionsVisitor * _Nullable)visitor timeZone:(NSString * _Nullable)timeZone referrer:(NSString * _Nullable)referrer referrerData:(PaywallUserDimensionsReferrerData * _Nullable)referrerData __attribute__((swift_name("doCopy(todayPageViews:todayPageViewsByArticle:todayPageViewsByArticleWithPaywall:todayPageViewsByArticleWithRegwall:todayTopLevelSections:todayTopLevelSectionsByArticle:sevenDayPageViews:sevenDayPageViewsByArticle:sevenDayPageViewsByArticleWithPaywall:sevenDayPageViewsByArticleWithRegwall:sevenDayTopLevelSections:sevenDayTopLevelSectionsByArticle:sevenDayVisitCount:twentyEightDayPageViews:twentyEightDayPageViewsByArticle:twentyEightDayPageViewsByArticleWithPaywall:twentyEightDayPageViewsByArticleWithRegwall:twentyEightDayTopLevelSections:twentyEightDayTopLevelSectionsByArticle:twentyEightDayVisitCount:daysSinceLastVisit:visitorType:visitor:timeZone:referrer:referrerData:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t daysSinceLastVisit __attribute__((swift_name("daysSinceLastVisit")));
@property (readonly) NSString * _Nullable referrer __attribute__((swift_name("referrer")));
@property (readonly) PaywallUserDimensionsReferrerData * _Nullable referrerData __attribute__((swift_name("referrerData")));
@property (readonly) int32_t sevenDayPageViews __attribute__((swift_name("sevenDayPageViews")));
@property (readonly) int32_t sevenDayPageViewsByArticle __attribute__((swift_name("sevenDayPageViewsByArticle")));
@property (readonly) int32_t sevenDayPageViewsByArticleWithPaywall __attribute__((swift_name("sevenDayPageViewsByArticleWithPaywall")));
@property (readonly) int32_t sevenDayPageViewsByArticleWithRegwall __attribute__((swift_name("sevenDayPageViewsByArticleWithRegwall")));
@property (readonly) int32_t sevenDayTopLevelSections __attribute__((swift_name("sevenDayTopLevelSections")));
@property (readonly) int32_t sevenDayTopLevelSectionsByArticle __attribute__((swift_name("sevenDayTopLevelSectionsByArticle")));
@property (readonly) int32_t sevenDayVisitCount __attribute__((swift_name("sevenDayVisitCount")));
@property (readonly) NSString * _Nullable timeZone __attribute__((swift_name("timeZone")));
@property (readonly) int32_t todayPageViews __attribute__((swift_name("todayPageViews")));
@property (readonly) int32_t todayPageViewsByArticle __attribute__((swift_name("todayPageViewsByArticle")));
@property (readonly) int32_t todayPageViewsByArticleWithPaywall __attribute__((swift_name("todayPageViewsByArticleWithPaywall")));
@property (readonly) int32_t todayPageViewsByArticleWithRegwall __attribute__((swift_name("todayPageViewsByArticleWithRegwall")));
@property (readonly) int32_t todayTopLevelSections __attribute__((swift_name("todayTopLevelSections")));
@property (readonly) int32_t todayTopLevelSectionsByArticle __attribute__((swift_name("todayTopLevelSectionsByArticle")));
@property (readonly) int32_t twentyEightDayPageViews __attribute__((swift_name("twentyEightDayPageViews")));
@property (readonly) int32_t twentyEightDayPageViewsByArticle __attribute__((swift_name("twentyEightDayPageViewsByArticle")));
@property (readonly) int32_t twentyEightDayPageViewsByArticleWithPaywall __attribute__((swift_name("twentyEightDayPageViewsByArticleWithPaywall")));
@property (readonly) int32_t twentyEightDayPageViewsByArticleWithRegwall __attribute__((swift_name("twentyEightDayPageViewsByArticleWithRegwall")));
@property (readonly) int32_t twentyEightDayTopLevelSections __attribute__((swift_name("twentyEightDayTopLevelSections")));
@property (readonly) int32_t twentyEightDayTopLevelSectionsByArticle __attribute__((swift_name("twentyEightDayTopLevelSectionsByArticle")));
@property (readonly) int32_t twentyEightDayVisitCount __attribute__((swift_name("twentyEightDayVisitCount")));
@property (readonly) PaywallUserDimensionsVisitor * _Nullable visitor __attribute__((swift_name("visitor")));
@property (readonly) PaywallVisitorType *visitorType __attribute__((swift_name("visitorType")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.Factory")))
@interface PaywallUserDimensionsFactory : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)factory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallUserDimensionsFactory *shared __attribute__((swift_name("shared")));
- (PaywallUserDimensions *)fromMapData:(NSDictionary<NSString *, id> *)data __attribute__((swift_name("fromMap(data:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.ReferrerData")))
@interface PaywallUserDimensionsReferrerData : PaywallBase
- (instancetype)initWithMedium:(PaywallReferrerMedium *)medium source:(PaywallReferrerSource * _Nullable)source channel:(PaywallReferrerChannel * _Nullable)channel __attribute__((swift_name("init(medium:source:channel:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallUserDimensionsReferrerDataFactory *companion __attribute__((swift_name("companion")));
- (PaywallUserDimensionsReferrerData *)doCopyMedium:(PaywallReferrerMedium *)medium source:(PaywallReferrerSource * _Nullable)source channel:(PaywallReferrerChannel * _Nullable)channel __attribute__((swift_name("doCopy(medium:source:channel:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) PaywallReferrerChannel * _Nullable channel __attribute__((swift_name("channel")));
@property (readonly) PaywallReferrerMedium *medium __attribute__((swift_name("medium")));
@property (readonly) PaywallReferrerSource * _Nullable source __attribute__((swift_name("source")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.ReferrerDataFactory")))
@interface PaywallUserDimensionsReferrerDataFactory : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)factory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallUserDimensionsReferrerDataFactory *shared __attribute__((swift_name("shared")));
- (PaywallUserDimensionsReferrerData * _Nullable)fromMapOptionalData:(id _Nullable)optionalData __attribute__((swift_name("fromMap(optionalData:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.Session")))
@interface PaywallUserDimensionsSession : PaywallBase
- (instancetype)initWithCampaignName:(NSString * _Nullable)campaignName referrerDomain:(NSString * _Nullable)referrerDomain referrerMedium:(PaywallReferrerMedium * _Nullable)referrerMedium referrerSource:(PaywallReferrerSource * _Nullable)referrerSource referrerChannel:(PaywallReferrerChannel * _Nullable)referrerChannel timestamp:(NSString * _Nullable)timestamp __attribute__((swift_name("init(campaignName:referrerDomain:referrerMedium:referrerSource:referrerChannel:timestamp:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallUserDimensionsSessionFactory *companion __attribute__((swift_name("companion")));
- (PaywallUserDimensionsSession *)doCopyCampaignName:(NSString * _Nullable)campaignName referrerDomain:(NSString * _Nullable)referrerDomain referrerMedium:(PaywallReferrerMedium * _Nullable)referrerMedium referrerSource:(PaywallReferrerSource * _Nullable)referrerSource referrerChannel:(PaywallReferrerChannel * _Nullable)referrerChannel timestamp:(NSString * _Nullable)timestamp __attribute__((swift_name("doCopy(campaignName:referrerDomain:referrerMedium:referrerSource:referrerChannel:timestamp:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString * _Nullable campaignName __attribute__((swift_name("campaignName")));
@property (readonly) PaywallReferrerChannel * _Nullable referrerChannel __attribute__((swift_name("referrerChannel")));
@property (readonly) NSString * _Nullable referrerDomain __attribute__((swift_name("referrerDomain")));
@property (readonly) PaywallReferrerMedium * _Nullable referrerMedium __attribute__((swift_name("referrerMedium")));
@property (readonly) PaywallReferrerSource * _Nullable referrerSource __attribute__((swift_name("referrerSource")));
@property (readonly) NSString * _Nullable timestamp __attribute__((swift_name("timestamp")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.SessionFactory")))
@interface PaywallUserDimensionsSessionFactory : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)factory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallUserDimensionsSessionFactory *shared __attribute__((swift_name("shared")));
- (PaywallUserDimensionsSession * _Nullable)fromMapOptionalData:(id _Nullable)optionalData __attribute__((swift_name("fromMap(optionalData:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.Visitor")))
@interface PaywallUserDimensionsVisitor : PaywallBase
- (instancetype)initWithSession:(PaywallUserDimensionsSession * _Nullable)session __attribute__((swift_name("init(session:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallUserDimensionsVisitorFactory *companion __attribute__((swift_name("companion")));
- (PaywallUserDimensionsVisitor *)doCopySession:(PaywallUserDimensionsSession * _Nullable)session __attribute__((swift_name("doCopy(session:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) PaywallUserDimensionsSession * _Nullable session __attribute__((swift_name("session")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserDimensions.VisitorFactory")))
@interface PaywallUserDimensionsVisitorFactory : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)factory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallUserDimensionsVisitorFactory *shared __attribute__((swift_name("shared")));
- (PaywallUserDimensionsVisitor * _Nullable)fromMapOptionalData:(id _Nullable)optionalData __attribute__((swift_name("fromMap(optionalData:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WallDecision")))
@interface PaywallWallDecision : PaywallBase
- (instancetype)initWithId:(NSString *)id createdAt:(NSString *)createdAt trace:(NSString * _Nullable)trace context:(NSString * _Nullable)context inputs:(NSString * _Nullable)inputs outcome:(PaywallOutcome *)outcome searchParams:(NSString * _Nullable)searchParams experiment:(PaywallExperiment *)experiment __attribute__((swift_name("init(id:createdAt:trace:context:inputs:outcome:searchParams:experiment:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallWallDecisionCompanion *companion __attribute__((swift_name("companion")));
- (PaywallWallDecision *)doCopyTrace:(NSString * _Nullable)trace outcome:(PaywallOutcome *)outcome __attribute__((swift_name("doCopy(trace:outcome:)")));
- (PaywallWallDecision *)doCopyId:(NSString *)id createdAt:(NSString *)createdAt trace:(NSString * _Nullable)trace context:(NSString * _Nullable)context inputs:(NSString * _Nullable)inputs outcome:(PaywallOutcome *)outcome searchParams:(NSString * _Nullable)searchParams experiment:(PaywallExperiment *)experiment __attribute__((swift_name("doCopy(id:createdAt:trace:context:inputs:outcome:searchParams:experiment:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString * _Nullable context __attribute__((swift_name("context")));
@property (readonly) NSString *createdAt __attribute__((swift_name("createdAt")));
@property (readonly) PaywallExperiment *experiment __attribute__((swift_name("experiment")));
@property (readonly) NSString *id __attribute__((swift_name("id")));
@property (readonly) NSString * _Nullable inputs __attribute__((swift_name("inputs")));
@property (readonly) PaywallOutcome *outcome __attribute__((swift_name("outcome")));
@property (readonly) NSString * _Nullable searchParams __attribute__((swift_name("searchParams")));
@property (readonly) NSString * _Nullable trace __attribute__((swift_name("trace")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WallDecision.Companion")))
@interface PaywallWallDecisionCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallWallDecisionCompanion *shared __attribute__((swift_name("shared")));
- (PaywallWallDecision *)fromDecisionDecision:(PaywallDecision *)decision __attribute__((swift_name("fromDecision(decision:)")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DeciderSettings")))
@interface PaywallDeciderSettings : PaywallBase
- (instancetype)initWithApiTimeoutInMilliSeconds:(int64_t)apiTimeoutInMilliSeconds paywallApiBaseUrl:(NSString *)paywallApiBaseUrl onDeviceModelRepositoryUrl:(NSString *)onDeviceModelRepositoryUrl __attribute__((swift_name("init(apiTimeoutInMilliSeconds:paywallApiBaseUrl:onDeviceModelRepositoryUrl:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallDeciderSettingsCompanion *companion __attribute__((swift_name("companion")));
- (PaywallDeciderSettings *)doCopyApiTimeoutInMilliSeconds:(int64_t)apiTimeoutInMilliSeconds paywallApiBaseUrl:(NSString *)paywallApiBaseUrl onDeviceModelRepositoryUrl:(NSString *)onDeviceModelRepositoryUrl __attribute__((swift_name("doCopy(apiTimeoutInMilliSeconds:paywallApiBaseUrl:onDeviceModelRepositoryUrl:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property int64_t apiTimeoutInMilliSeconds __attribute__((swift_name("apiTimeoutInMilliSeconds")));
@property NSString *onDeviceModelRepositoryUrl __attribute__((swift_name("onDeviceModelRepositoryUrl")));
@property NSString *paywallApiBaseUrl __attribute__((swift_name("paywallApiBaseUrl")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DeciderSettings.Companion")))
@interface PaywallDeciderSettingsCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallDeciderSettingsCompanion *shared __attribute__((swift_name("shared")));
- (PaywallDeciderSettings *)fromMapMap:(NSDictionary<NSString *, id> *)map __attribute__((swift_name("fromMap(map:)")));
@end

__attribute__((swift_name("DeviceDimensionRepository")))
@protocol PaywallDeviceDimensionRepository
@required
- (PaywallDeviceDimensions *)getAll __attribute__((swift_name("getAll()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallDecider")))
@interface PaywallPaywallDecider : PaywallBase
- (instancetype)initWithHost:(NSString *)host settings:(PaywallDeciderSettings *)settings userDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions __attribute__((swift_name("init(host:settings:userDimensions:deviceDimensions:)"))) __attribute__((objc_designated_initializer));
- (void)close __attribute__((swift_name("close()")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)decideContentId:(NSString *)contentId assignedGroup:(NSString * _Nullable)assignedGroup contentProperties:(NSDictionary<NSString *, id> * _Nullable)contentProperties userProperties:(NSDictionary<NSString *, id> * _Nullable)userProperties completionHandler:(void (^)(PaywallWallDecision * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("decide(contentId:assignedGroup:contentProperties:userProperties:completionHandler:)")));
@property PaywallDeviceDimensions *deviceDimensions __attribute__((swift_name("deviceDimensions")));
@property NSString *host __attribute__((swift_name("host")));
@property (readonly) id<PaywallNetworkManager> networkManager __attribute__((swift_name("networkManager")));
@property PaywallDeciderSettings *settings __attribute__((swift_name("settings")));
@property PaywallUserDimensions *userDimensions __attribute__((swift_name("userDimensions")));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable
*/
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallDeciderConfig")))
@interface PaywallPaywallDeciderConfig : PaywallBase
- (instancetype)initWithHost:(NSString *)host settings:(NSDictionary<NSString *, id> *)settings userDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions __attribute__((swift_name("init(host:settings:userDimensions:deviceDimensions:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PaywallPaywallDeciderConfigCompanion *companion __attribute__((swift_name("companion")));
- (PaywallPaywallDeciderConfig *)doCopyHost:(NSString *)host settings:(NSDictionary<NSString *, id> *)settings userDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions __attribute__((swift_name("doCopy(host:settings:userDimensions:deviceDimensions:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) PaywallDeviceDimensions *deviceDimensions __attribute__((swift_name("deviceDimensions")));
@property (readonly) NSString *host __attribute__((swift_name("host")));
@property (readonly) NSDictionary<NSString *, id> *settings __attribute__((swift_name("settings")));
@property (readonly) PaywallUserDimensions *userDimensions __attribute__((swift_name("userDimensions")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallDeciderConfig.Companion")))
@interface PaywallPaywallDeciderConfigCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallPaywallDeciderConfigCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallDeciderRepository")))
@interface PaywallPaywallDeciderRepository : PaywallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) PaywallPaywallDeciderRepositoryCompanion *companion __attribute__((swift_name("companion")));
- (PaywallPaywallDecider *)getOneByHostHost:(NSString *)host settings:(NSDictionary<NSString *, NSString *> *)settings __attribute__((swift_name("getOneByHost(host:settings:)")));
- (PaywallPaywallDeciderConfig *)getPaywallDeciderConfigByHostHost:(NSString *)host settings:(NSDictionary<NSString *, NSString *> *)settings __attribute__((swift_name("getPaywallDeciderConfigByHost(host:settings:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PaywallDeciderRepository.Companion")))
@interface PaywallPaywallDeciderRepositoryCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallPaywallDeciderRepositoryCompanion *shared __attribute__((swift_name("shared")));
- (PaywallPaywallDeciderRepository *)createNewUserRepository:(id<PaywallUserDimensionRepository>)userRepository deviceRepository:(id<PaywallDeviceDimensionRepository>)deviceRepository __attribute__((swift_name("createNew(userRepository:deviceRepository:)")));
- (PaywallPaywallDeciderRepository *)createNewFromDataUserDimensions:(PaywallUserDimensions *)userDimensions deviceDimensions:(PaywallDeviceDimensions *)deviceDimensions __attribute__((swift_name("createNewFromData(userDimensions:deviceDimensions:)")));
@end

__attribute__((swift_name("UserDimensionRepository")))
@protocol PaywallUserDimensionRepository
@required
- (PaywallUserDimensions *)getAll __attribute__((swift_name("getAll()")));
@end

__attribute__((swift_name("DeviceInfo")))
@protocol PaywallDeviceInfo
@required
@property (readonly) PaywallDeviceType *deviceType __attribute__((swift_name("deviceType")));
@property (readonly) NSString *osName __attribute__((swift_name("osName")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DeviceType")))
@interface PaywallDeviceType : PaywallKotlinEnum<PaywallDeviceType *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) PaywallDeviceType *console __attribute__((swift_name("console")));
@property (class, readonly) PaywallDeviceType *mobile __attribute__((swift_name("mobile")));
@property (class, readonly) PaywallDeviceType *tablet __attribute__((swift_name("tablet")));
@property (class, readonly) PaywallDeviceType *smartTv __attribute__((swift_name("smartTv")));
@property (class, readonly) PaywallDeviceType *wearable __attribute__((swift_name("wearable")));
@property (class, readonly) PaywallDeviceType *desktop __attribute__((swift_name("desktop")));
@property (class, readonly) PaywallDeviceType *embedded __attribute__((swift_name("embedded")));
@property (class, readonly) PaywallDeviceType *xr __attribute__((swift_name("xr")));
@property (class, readonly) PaywallDeviceType *other __attribute__((swift_name("other")));
@property (class, readonly) PaywallDeviceType *unknown __attribute__((swift_name("unknown")));
+ (PaywallKotlinArray<PaywallDeviceType *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<PaywallDeviceType *> *entries __attribute__((swift_name("entries")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("IOSDeviceInfo")))
@interface PaywallIOSDeviceInfo : PaywallBase <PaywallDeviceInfo>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (readonly) PaywallDeviceType *deviceType __attribute__((swift_name("deviceType")));
@property (readonly) NSString *osName __attribute__((swift_name("osName")));
@end

__attribute__((swift_name("NetworkManager")))
@protocol PaywallNetworkManager
@required
- (BOOL)isOnline __attribute__((swift_name("isOnline()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("IOSNetworkManager")))
@interface PaywallIOSNetworkManager : PaywallBase <PaywallNetworkManager>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)iOSNetworkManager __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallIOSNetworkManager *shared __attribute__((swift_name("shared")));
- (BOOL)isOnline __attribute__((swift_name("isOnline()")));
@property BOOL isOnlineStatus __attribute__((swift_name("isOnlineStatus")));
@end

__attribute__((swift_name("Storage")))
@protocol PaywallStorage
@required
- (NSString * _Nullable)fetchKey:(NSString *)key __attribute__((swift_name("fetch(key:)")));
- (void)saveKey:(NSString *)key value:(NSString *)value __attribute__((swift_name("save(key:value:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DeviceInfo_iosKt")))
@interface PaywallDeviceInfo_iosKt : PaywallBase
+ (id<PaywallDeviceInfo>)getDeviceInfo __attribute__((swift_name("getDeviceInfo()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ModelKt")))
@interface PaywallModelKt : PaywallBase
@property (class, readonly) NSDictionary<NSString *, NSDictionary<id, id> *> *DEFAULT_PARAMETERS __attribute__((swift_name("DEFAULT_PARAMETERS")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NetworkManager_iosKt")))
@interface PaywallNetworkManager_iosKt : PaywallBase
+ (id<PaywallNetworkManager>)getNetworkManager __attribute__((swift_name("getNetworkManager()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Utility_iosKt")))
@interface PaywallUtility_iosKt : PaywallBase
+ (NSString *)generateUUID __attribute__((swift_name("generateUUID()")));
+ (int32_t)getCurrentHour __attribute__((swift_name("getCurrentHour()")));
+ (NSString *)getCurrentISOTimestamp __attribute__((swift_name("getCurrentISOTimestamp()")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreSerializationStrategy")))
@protocol PaywallKotlinx_serialization_coreSerializationStrategy
@required
- (void)serializeEncoder:(id<PaywallKotlinx_serialization_coreEncoder>)encoder value:(id _Nullable)value __attribute__((swift_name("serialize(encoder:value:)")));
@property (readonly) id<PaywallKotlinx_serialization_coreSerialDescriptor> descriptor __attribute__((swift_name("descriptor")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreDeserializationStrategy")))
@protocol PaywallKotlinx_serialization_coreDeserializationStrategy
@required
- (id _Nullable)deserializeDecoder:(id<PaywallKotlinx_serialization_coreDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
@property (readonly) id<PaywallKotlinx_serialization_coreSerialDescriptor> descriptor __attribute__((swift_name("descriptor")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreKSerializer")))
@protocol PaywallKotlinx_serialization_coreKSerializer <PaywallKotlinx_serialization_coreSerializationStrategy, PaywallKotlinx_serialization_coreDeserializationStrategy>
@required
@end

__attribute__((swift_name("KotlinThrowable")))
@interface PaywallKotlinThrowable : PaywallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));

/**
 * @note annotations
 *   kotlin.experimental.ExperimentalNativeApi
*/
- (PaywallKotlinArray<NSString *> *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) PaywallKotlinThrowable * _Nullable cause __attribute__((swift_name("cause")));
@property (readonly) NSString * _Nullable message __attribute__((swift_name("message")));
- (NSError *)asError __attribute__((swift_name("asError()")));
@end

__attribute__((swift_name("KotlinException")))
@interface PaywallKotlinException : PaywallKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("KotlinRuntimeException")))
@interface PaywallKotlinRuntimeException : PaywallKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("KotlinIllegalStateException")))
@interface PaywallKotlinIllegalStateException : PaywallKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.4")
*/
__attribute__((swift_name("KotlinCancellationException")))
@interface PaywallKotlinCancellationException : PaywallKotlinIllegalStateException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(PaywallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlinx.serialization.Serializable(with=NormalClass(value=kotlinx/serialization/json/JsonElementSerializer))
*/
__attribute__((swift_name("Kotlinx_serialization_jsonJsonElement")))
@interface PaywallKotlinx_serialization_jsonJsonElement : PaywallBase
@property (class, readonly, getter=companion) PaywallKotlinx_serialization_jsonJsonElementCompanion *companion __attribute__((swift_name("companion")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinEnumCompanion")))
@interface PaywallKotlinEnumCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallKotlinEnumCompanion *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface PaywallKotlinArray<T> : PaywallBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(PaywallInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<PaywallKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreEncoder")))
@protocol PaywallKotlinx_serialization_coreEncoder
@required
- (id<PaywallKotlinx_serialization_coreCompositeEncoder>)beginCollectionDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor collectionSize:(int32_t)collectionSize __attribute__((swift_name("beginCollection(descriptor:collectionSize:)")));
- (id<PaywallKotlinx_serialization_coreCompositeEncoder>)beginStructureDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("beginStructure(descriptor:)")));
- (void)encodeBooleanValue:(BOOL)value __attribute__((swift_name("encodeBoolean(value:)")));
- (void)encodeByteValue:(int8_t)value __attribute__((swift_name("encodeByte(value:)")));
- (void)encodeCharValue:(unichar)value __attribute__((swift_name("encodeChar(value:)")));
- (void)encodeDoubleValue:(double)value __attribute__((swift_name("encodeDouble(value:)")));
- (void)encodeEnumEnumDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)enumDescriptor index:(int32_t)index __attribute__((swift_name("encodeEnum(enumDescriptor:index:)")));
- (void)encodeFloatValue:(float)value __attribute__((swift_name("encodeFloat(value:)")));
- (id<PaywallKotlinx_serialization_coreEncoder>)encodeInlineDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("encodeInline(descriptor:)")));
- (void)encodeIntValue:(int32_t)value __attribute__((swift_name("encodeInt(value:)")));
- (void)encodeLongValue:(int64_t)value __attribute__((swift_name("encodeLong(value:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (void)encodeNotNullMark __attribute__((swift_name("encodeNotNullMark()")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (void)encodeNull __attribute__((swift_name("encodeNull()")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (void)encodeNullableSerializableValueSerializer:(id<PaywallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeNullableSerializableValue(serializer:value:)")));
- (void)encodeSerializableValueSerializer:(id<PaywallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeSerializableValue(serializer:value:)")));
- (void)encodeShortValue:(int16_t)value __attribute__((swift_name("encodeShort(value:)")));
- (void)encodeStringValue:(NSString *)value __attribute__((swift_name("encodeString(value:)")));
@property (readonly) PaywallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreSerialDescriptor")))
@protocol PaywallKotlinx_serialization_coreSerialDescriptor
@required

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (NSArray<id<PaywallKotlinAnnotation>> *)getElementAnnotationsIndex:(int32_t)index __attribute__((swift_name("getElementAnnotations(index:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (id<PaywallKotlinx_serialization_coreSerialDescriptor>)getElementDescriptorIndex:(int32_t)index __attribute__((swift_name("getElementDescriptor(index:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (int32_t)getElementIndexName:(NSString *)name __attribute__((swift_name("getElementIndex(name:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (NSString *)getElementNameIndex:(int32_t)index __attribute__((swift_name("getElementName(index:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (BOOL)isElementOptionalIndex:(int32_t)index __attribute__((swift_name("isElementOptional(index:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
@property (readonly) NSArray<id<PaywallKotlinAnnotation>> *annotations __attribute__((swift_name("annotations")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
@property (readonly) int32_t elementsCount __attribute__((swift_name("elementsCount")));
@property (readonly) BOOL isInline __attribute__((swift_name("isInline")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
@property (readonly) BOOL isNullable __attribute__((swift_name("isNullable")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
@property (readonly) PaywallKotlinx_serialization_coreSerialKind *kind __attribute__((swift_name("kind")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
@property (readonly) NSString *serialName __attribute__((swift_name("serialName")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreDecoder")))
@protocol PaywallKotlinx_serialization_coreDecoder
@required
- (id<PaywallKotlinx_serialization_coreCompositeDecoder>)beginStructureDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("beginStructure(descriptor:)")));
- (BOOL)decodeBoolean __attribute__((swift_name("decodeBoolean()")));
- (int8_t)decodeByte __attribute__((swift_name("decodeByte()")));
- (unichar)decodeChar __attribute__((swift_name("decodeChar()")));
- (double)decodeDouble __attribute__((swift_name("decodeDouble()")));
- (int32_t)decodeEnumEnumDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)enumDescriptor __attribute__((swift_name("decodeEnum(enumDescriptor:)")));
- (float)decodeFloat __attribute__((swift_name("decodeFloat()")));
- (id<PaywallKotlinx_serialization_coreDecoder>)decodeInlineDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("decodeInline(descriptor:)")));
- (int32_t)decodeInt __attribute__((swift_name("decodeInt()")));
- (int64_t)decodeLong __attribute__((swift_name("decodeLong()")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (BOOL)decodeNotNullMark __attribute__((swift_name("decodeNotNullMark()")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (PaywallKotlinNothing * _Nullable)decodeNull __attribute__((swift_name("decodeNull()")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (id _Nullable)decodeNullableSerializableValueDeserializer:(id<PaywallKotlinx_serialization_coreDeserializationStrategy>)deserializer __attribute__((swift_name("decodeNullableSerializableValue(deserializer:)")));
- (id _Nullable)decodeSerializableValueDeserializer:(id<PaywallKotlinx_serialization_coreDeserializationStrategy>)deserializer __attribute__((swift_name("decodeSerializableValue(deserializer:)")));
- (int16_t)decodeShort __attribute__((swift_name("decodeShort()")));
- (NSString *)decodeString __attribute__((swift_name("decodeString()")));
@property (readonly) PaywallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_serialization_jsonJsonElement.Companion")))
@interface PaywallKotlinx_serialization_jsonJsonElementCompanion : PaywallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PaywallKotlinx_serialization_jsonJsonElementCompanion *shared __attribute__((swift_name("shared")));
- (id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol PaywallKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreCompositeEncoder")))
@protocol PaywallKotlinx_serialization_coreCompositeEncoder
@required
- (void)encodeBooleanElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(BOOL)value __attribute__((swift_name("encodeBooleanElement(descriptor:index:value:)")));
- (void)encodeByteElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int8_t)value __attribute__((swift_name("encodeByteElement(descriptor:index:value:)")));
- (void)encodeCharElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(unichar)value __attribute__((swift_name("encodeCharElement(descriptor:index:value:)")));
- (void)encodeDoubleElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(double)value __attribute__((swift_name("encodeDoubleElement(descriptor:index:value:)")));
- (void)encodeFloatElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(float)value __attribute__((swift_name("encodeFloatElement(descriptor:index:value:)")));
- (id<PaywallKotlinx_serialization_coreEncoder>)encodeInlineElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("encodeInlineElement(descriptor:index:)")));
- (void)encodeIntElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int32_t)value __attribute__((swift_name("encodeIntElement(descriptor:index:value:)")));
- (void)encodeLongElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int64_t)value __attribute__((swift_name("encodeLongElement(descriptor:index:value:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (void)encodeNullableSerializableElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index serializer:(id<PaywallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeNullableSerializableElement(descriptor:index:serializer:value:)")));
- (void)encodeSerializableElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index serializer:(id<PaywallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeSerializableElement(descriptor:index:serializer:value:)")));
- (void)encodeShortElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int16_t)value __attribute__((swift_name("encodeShortElement(descriptor:index:value:)")));
- (void)encodeStringElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(NSString *)value __attribute__((swift_name("encodeStringElement(descriptor:index:value:)")));
- (void)endStructureDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("endStructure(descriptor:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (BOOL)shouldEncodeElementDefaultDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("shouldEncodeElementDefault(descriptor:index:)")));
@property (readonly) PaywallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreSerializersModule")))
@interface PaywallKotlinx_serialization_coreSerializersModule : PaywallBase

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (void)dumpToCollector:(id<PaywallKotlinx_serialization_coreSerializersModuleCollector>)collector __attribute__((swift_name("dumpTo(collector:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (id<PaywallKotlinx_serialization_coreKSerializer> _Nullable)getContextualKClass:(id<PaywallKotlinKClass>)kClass typeArgumentsSerializers:(NSArray<id<PaywallKotlinx_serialization_coreKSerializer>> *)typeArgumentsSerializers __attribute__((swift_name("getContextual(kClass:typeArgumentsSerializers:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (id<PaywallKotlinx_serialization_coreSerializationStrategy> _Nullable)getPolymorphicBaseClass:(id<PaywallKotlinKClass>)baseClass value:(id)value __attribute__((swift_name("getPolymorphic(baseClass:value:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (id<PaywallKotlinx_serialization_coreDeserializationStrategy> _Nullable)getPolymorphicBaseClass:(id<PaywallKotlinKClass>)baseClass serializedClassName:(NSString * _Nullable)serializedClassName __attribute__((swift_name("getPolymorphic(baseClass:serializedClassName:)")));
@end

__attribute__((swift_name("KotlinAnnotation")))
@protocol PaywallKotlinAnnotation
@required
@end


/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
__attribute__((swift_name("Kotlinx_serialization_coreSerialKind")))
@interface PaywallKotlinx_serialization_coreSerialKind : PaywallBase
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((swift_name("Kotlinx_serialization_coreCompositeDecoder")))
@protocol PaywallKotlinx_serialization_coreCompositeDecoder
@required
- (BOOL)decodeBooleanElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeBooleanElement(descriptor:index:)")));
- (int8_t)decodeByteElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeByteElement(descriptor:index:)")));
- (unichar)decodeCharElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeCharElement(descriptor:index:)")));
- (int32_t)decodeCollectionSizeDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("decodeCollectionSize(descriptor:)")));
- (double)decodeDoubleElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeDoubleElement(descriptor:index:)")));
- (int32_t)decodeElementIndexDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("decodeElementIndex(descriptor:)")));
- (float)decodeFloatElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeFloatElement(descriptor:index:)")));
- (id<PaywallKotlinx_serialization_coreDecoder>)decodeInlineElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeInlineElement(descriptor:index:)")));
- (int32_t)decodeIntElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeIntElement(descriptor:index:)")));
- (int64_t)decodeLongElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeLongElement(descriptor:index:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (id _Nullable)decodeNullableSerializableElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index deserializer:(id<PaywallKotlinx_serialization_coreDeserializationStrategy>)deserializer previousValue:(id _Nullable)previousValue __attribute__((swift_name("decodeNullableSerializableElement(descriptor:index:deserializer:previousValue:)")));

/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
- (BOOL)decodeSequentially __attribute__((swift_name("decodeSequentially()")));
- (id _Nullable)decodeSerializableElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index deserializer:(id<PaywallKotlinx_serialization_coreDeserializationStrategy>)deserializer previousValue:(id _Nullable)previousValue __attribute__((swift_name("decodeSerializableElement(descriptor:index:deserializer:previousValue:)")));
- (int16_t)decodeShortElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeShortElement(descriptor:index:)")));
- (NSString *)decodeStringElementDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeStringElement(descriptor:index:)")));
- (void)endStructureDescriptor:(id<PaywallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("endStructure(descriptor:)")));
@property (readonly) PaywallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinNothing")))
@interface PaywallKotlinNothing : PaywallBase
@end


/**
 * @note annotations
 *   kotlinx.serialization.ExperimentalSerializationApi
*/
__attribute__((swift_name("Kotlinx_serialization_coreSerializersModuleCollector")))
@protocol PaywallKotlinx_serialization_coreSerializersModuleCollector
@required
- (void)contextualKClass:(id<PaywallKotlinKClass>)kClass provider:(id<PaywallKotlinx_serialization_coreKSerializer> (^)(NSArray<id<PaywallKotlinx_serialization_coreKSerializer>> *))provider __attribute__((swift_name("contextual(kClass:provider:)")));
- (void)contextualKClass:(id<PaywallKotlinKClass>)kClass serializer:(id<PaywallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("contextual(kClass:serializer:)")));
- (void)polymorphicBaseClass:(id<PaywallKotlinKClass>)baseClass actualClass:(id<PaywallKotlinKClass>)actualClass actualSerializer:(id<PaywallKotlinx_serialization_coreKSerializer>)actualSerializer __attribute__((swift_name("polymorphic(baseClass:actualClass:actualSerializer:)")));
- (void)polymorphicDefaultBaseClass:(id<PaywallKotlinKClass>)baseClass defaultDeserializerProvider:(id<PaywallKotlinx_serialization_coreDeserializationStrategy> _Nullable (^)(NSString * _Nullable))defaultDeserializerProvider __attribute__((swift_name("polymorphicDefault(baseClass:defaultDeserializerProvider:)"))) __attribute__((deprecated("Deprecated in favor of function with more precise name: polymorphicDefaultDeserializer")));
- (void)polymorphicDefaultDeserializerBaseClass:(id<PaywallKotlinKClass>)baseClass defaultDeserializerProvider:(id<PaywallKotlinx_serialization_coreDeserializationStrategy> _Nullable (^)(NSString * _Nullable))defaultDeserializerProvider __attribute__((swift_name("polymorphicDefaultDeserializer(baseClass:defaultDeserializerProvider:)")));
- (void)polymorphicDefaultSerializerBaseClass:(id<PaywallKotlinKClass>)baseClass defaultSerializerProvider:(id<PaywallKotlinx_serialization_coreSerializationStrategy> _Nullable (^)(id))defaultSerializerProvider __attribute__((swift_name("polymorphicDefaultSerializer(baseClass:defaultSerializerProvider:)")));
@end

__attribute__((swift_name("KotlinKDeclarationContainer")))
@protocol PaywallKotlinKDeclarationContainer
@required
@end

__attribute__((swift_name("KotlinKAnnotatedElement")))
@protocol PaywallKotlinKAnnotatedElement
@required
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.1")
*/
__attribute__((swift_name("KotlinKClassifier")))
@protocol PaywallKotlinKClassifier
@required
@end

__attribute__((swift_name("KotlinKClass")))
@protocol PaywallKotlinKClass <PaywallKotlinKDeclarationContainer, PaywallKotlinKAnnotatedElement, PaywallKotlinKClassifier>
@required

/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.1")
*/
- (BOOL)isInstanceValue:(id _Nullable)value __attribute__((swift_name("isInstance(value:)")));
@property (readonly) NSString * _Nullable qualifiedName __attribute__((swift_name("qualifiedName")));
@property (readonly) NSString * _Nullable simpleName __attribute__((swift_name("simpleName")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
