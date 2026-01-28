import { TurboModuleRegistry, type TurboModule } from 'react-native';

// Type definitions matching the native PaywallKit framework

export type VisitorType = 'anonymous' | 'registered';
export type ReferrerMedium = 'campaign' | 'direct' | 'internal' | 'search' | 'social' | 'other';
export type ReferrerSource = 'google' | 'yahoo' | 'duckduckgo' | 'bing' | 'facebook' | 'instagram' | 'x' | 't' | 'linkedin' | 'reddit' | 'newsletter';
export type ReferrerChannel = 'search' | 'news' | 'discover';

export type Visitor = {
  session: Session | null;
}

export type Session = {
  campaignName: string | null;
  referrerDomain: string | null;
  referrerMedium: ReferrerMedium | null;
  referrerSource: ReferrerSource | null;
  referrerChannel: ReferrerChannel | null;
  timestamp: string | null;
}

export type ReferrerData = {
  medium: ReferrerMedium;
  source: ReferrerSource | null;
  channel: ReferrerChannel | null;
}

export type UserDimensions = {
  todayPageViews: number;
  todayPageViewsByArticle: number;
  todayPageViewsByArticleWithPaywall: number;
  todayPageViewsByArticleWithRegwall: number;
  todayTopLevelSections: number;
  todayTopLevelSectionsByArticle: number;
  sevenDayPageViews: number;
  sevenDayPageViewsByArticle: number;
  sevenDayPageViewsByArticleWithPaywall: number;
  sevenDayPageViewsByArticleWithRegwall: number;
  sevenDayTopLevelSections: number;
  sevenDayTopLevelSectionsByArticle: number;
  sevenDayVisitCount: number;
  twentyEightDayPageViews: number;
  twentyEightDayPageViewsByArticle: number;
  twentyEightDayPageViewsByArticleWithPaywall: number;
  twentyEightDayPageViewsByArticleWithRegwall: number;
  twentyEightDayTopLevelSections: number;
  twentyEightDayTopLevelSectionsByArticle: number;
  twentyEightDayVisitCount: number;
  daysSinceLastVisit: number;
  visitorType: string;
  visitor: Visitor | null;
  timeZone: string | null;
  referrer: string | null;
  referrerData: ReferrerData | null;
  countryCode: string | null;
}

export type DeviceDimensions = {
  hourOfDay: number; // 0-23
  os: string | null; // e.g., "iOS", "Android"
  type: string | null; // e.g., "mobile", "tablet"
  viewer: string | null; // e.g., "company-ios-1.0.0"
}

export type WallDecisionExperiment = {
  experimentId: string;
  assignedGroup: string;
}

export type WallDecisionOutcome = {
  wallType: string | null;
  wallTypeCode: number | null;
  wallVisibility: string;
  wallVisibilityCode: number;
}

export type WallDecision = {
  id: string;
  createdAt: string;
  trace: string;
  context: string;
  inputs: string;
  outcome: WallDecisionOutcome;
  searchParams: string;
  experiment: WallDecisionExperiment;
}

export type PaywallDeciderConfig = {
  host: string;
  settings: Object;
  userDimensions: UserDimensions;
  deviceDimensions: DeviceDimensions;
}

export interface Spec extends TurboModule {
  initializePaywallDeciderRepository(
    userDimensions: UserDimensions,
    deviceDimensions: DeviceDimensions
  ): void;
  getPaywallDeciderConfigByHost(
    host: string,
    settings: Object
  ): PaywallDeciderConfig;
  decide(contentId: string, assignedGroup?: string): WallDecision;
}

export default TurboModuleRegistry.getEnforcing<Spec>('PaywallKit');
