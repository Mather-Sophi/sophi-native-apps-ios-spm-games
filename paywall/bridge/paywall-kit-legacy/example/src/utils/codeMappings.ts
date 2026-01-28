import type {
  ReferrerMedium,
  ReferrerSource,
  ReferrerChannel,
  VisitorType,
  UserDimensions,
} from '@mather-sophi/sophi-react-native-paywall-kit';

// Numeric index codes for enum values (provided by user)
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
export const ContextPrefixes = {
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

export const InputPrefixes = {
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

export type ContextKey = keyof typeof ContextPrefixes;
export type InputKey = keyof typeof InputPrefixes;

export function pad2(n: number): string {
  // Pad to at least 2 digits; allow values > 99 without truncation
  if (n === null || n === undefined) return '';
  return n.toString().padStart(2, '0');
}

export function encodeContextCode(key: ContextKey, value: number): string {
  const prefix = ContextPrefixes[key];
  if (prefix == null) throw new Error(`Unknown context key: ${key}`);
  const padded = pad2(value);
  return `${prefix}${padded}`;
}

export type InputValue = string | number;

export function encodeInputCode(key: InputKey, value: InputValue, format: 'name' | 'index' = 'name'): string {
  const prefix = InputPrefixes[key];
  if (prefix == null) throw new Error(`Unknown input key: ${key}`);

  // Special handling for hourOfDay numeric padding
  if (key === 'hourOfDay' && typeof value === 'number') {
    return `${prefix}:${value.toString().padStart(2, '0')}`;
  }

  // Optional index-based encoding for enums, default is human-readable name
  if (format === 'index') {
    switch (key) {
      case 'referrerMedium':
        return `${prefix}:${pad2((ReferrerMediumCodes as any)[value as string])}`;
      case 'referrerSource':
        return `${prefix}:${pad2((ReferrerSourceCodes as any)[value as string])}`;
      case 'referrerChannel':
        return `${prefix}:${pad2((ReferrerChannelCodes as any)[value as string])}`;
      case 'visitorType':
        return `${prefix}:${pad2((VisitorTypeCodes as any)[value as string])}`;
      default:
        break;
    }
  }

  return `${prefix}:${String(value)}`;
}

export interface ScenarioLike {
  userDimensions: Partial<UserDimensions>;
  visitorType: VisitorType;
  referrerMedium: ReferrerMedium;
  referrerSource: ReferrerSource;
  referrerChannel: ReferrerChannel;
}

// Build expected encoded codes for a scenario
export function expectedCodesForScenario(
  s: ScenarioLike,
  options?: { inputFormat?: 'name' | 'index' }
): { contextCodes: string[]; inputCodes: string[] } {
  const inputFormat = options?.inputFormat ?? 'name';

  // Only include context metrics that exist in the provided scenario
  const contextKeys: ContextKey[] = [
    'todayPageViews',
    'todayPageViewsByArticle',
    'sevenDayPageViews',
    'twentyEightDayPageViews',
    'daysSinceLastVisit',
  ];

  const contextCodes: string[] = [];
  for (const key of contextKeys) {
    const v = (s.userDimensions as any)[key];
    if (typeof v === 'number' && v != null) {
      contextCodes.push(encodeContextCode(key, v));
    }
  }

  const inputCodes: string[] = [
    encodeInputCode('visitorType', s.visitorType, inputFormat),
    encodeInputCode('referrerMedium', s.referrerMedium, inputFormat),
    encodeInputCode('referrerSource', s.referrerSource, inputFormat),
    encodeInputCode('referrerChannel', s.referrerChannel, inputFormat),
  ];

  return { contextCodes, inputCodes };
}
