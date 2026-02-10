// Numeric index codes for enum values
export const ReferrerMediumCodes = {
  campaign: 0,
  direct: 1,
  internal: 2,
  search: 3,
  social: 4,
  other: 5,
} as const;

export const ReferrerSourceCodes = {
  google: 0,
  yahoo: 1,
  duckduckgo: 2,
  bing: 3,
  facebook: 4,
  instagram: 5,
  x: 6,
  linkedin: 7,
  reddit: 8,
  newsletter: 9,
} as const;

export const ReferrerChannelCodes = {
  search: 0,
  news: 1,
  discover: 2,
} as const;

export const VisitorTypeCodes = {
  anonymous: 0,
  registered: 1,
} as const;

// Prefixes for encoded context and input codes
export const ContextCodes = {
  todayPageViews: 'AA',
  todayPageViewsByArticle: 'AB',
  todayPageViewsByArticleWithPaywall: 'AC',
  todayTopLevelSections: 'AD',
  todayTopLevelSectionsByArticle: 'AE',
  todayPageViewsByArticleWithRegwall: 'AF',

  sevenDayPageViews: 'BA',
  sevenDayPageViewsByArticle: 'BB',
  sevenDayPageViewsByArticleWithPaywall: 'BC',
  sevenDayTopLevelSections: 'BD',
  sevenDayTopLevelSectionsByArticle: 'BE',
  sevenDayVisitCount: 'BF',
  sevenDayPageViewsByArticleWithRegwall: 'BG',

  twentyEightDayPageViews: 'CA',
  twentyEightDayPageViewsByArticle: 'CB',
  twentyEightDayPageViewsByArticleWithPaywall: 'CC',
  twentyEightDayTopLevelSections: 'CD',
  twentyEightDayTopLevelSectionsByArticle: 'CE',
  twentyEightDayVisitCount: 'CF',
  twentyEightDayPageViewsByArticleWithRegwall: 'CG',

  daysSinceLastVisit: 'DA',
  // Inputs with numeric-like formatting (hourOfDay) are treated separately
} as const;

export const InputCodes = {
  visitorType: 'FA',
  os: 'EA',
  type: 'EB',
  viewer: 'EC',
  hourOfDay: 'DD',
  timeZone: 'DB',
  referrerMedium: 'DE',
  referrerSource: 'DF',
  referrerChannel: 'DG',
  campaignName: 'GA',
  referrerDomain: 'GB',
  sessionReferrerMedium: 'GC',
  sessionReferrerSource: 'GD',
  sessionReferrerChannel: 'GE',
} as const;
