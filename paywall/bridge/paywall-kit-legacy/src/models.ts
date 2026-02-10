export type VisitorType = 'anonymous' | 'registered';

export type ReferrerMedium =
  | 'campaign'
  | 'direct'
  | 'internal'
  | 'search'
  | 'social'
  | 'other';

export type ReferrerSource =
  | 'google'
  | 'yahoo'
  | 'duckduckgo'
  | 'bing'
  | 'facebook'
  | 'instagram'
  | 'x'
  | 't'
  | 'linkedin'
  | 'reddit'
  | 'newsletter';

export type ReferrerChannel = 'search' | 'news' | 'discover';

export type DeviceType = 'native';

export type DeviceOs = 'ios' | 'android';

export type Visitor = {
  session: Session | null;
};

export type Session = {
  campaignName: string | null;
  referrerDomain: string | null;
  referrerMedium: ReferrerMedium | null;
  referrerSource: ReferrerSource | null;
  referrerChannel: ReferrerChannel | null;
  timestamp: string | null;
};

export type ReferrerData = {
  medium: ReferrerMedium;
  source: ReferrerSource | null;
  channel: ReferrerChannel | null;
  utmParameters: Record<string, string> | null;
};

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
  visitorType: VisitorType;
  visitor: Visitor | null;
  timeZone: string | null;
  referrer: ReferrerMedium;
  referrerData: ReferrerData | null;
};

export type DeviceDimensions = {
  hourOfDay: number; // 0-23
  os: DeviceOs;
  type: DeviceType;
  viewer: string | null; // e.g., "company-ios-1.0.0"
};

export type WallDecisionOutcome = {
  wallType?: string | null;
  wallTypeCode?: number | null;
  wallVisibility: string;
  wallVisibilityCode: number;
};

export type WallDecision = {
  id: string;
  createdAt: string;
  trace: string;
  context?: string | null;
  inputs?: string | null;
  outcome: WallDecisionOutcome;
  searchParams?: string | null;
  experimentCode?: string | null;
  paywallScore?: number | null;
  userProperties?: string | null;
  contentProperties?: string | null;
};

export type PaywallDeciderConfig = {
  host: string;
  settings: Object;
  userDimensions: UserDimensions;
  deviceDimensions: DeviceDimensions;
};
