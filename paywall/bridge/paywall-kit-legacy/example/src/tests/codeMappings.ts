import type {
  ReferrerMedium,
  ReferrerSource,
  ReferrerChannel,
  VisitorType,
  UserDimensions,
} from '@mather-sophi/sophi-react-native-paywall-kit';
import {
  ReferrerMediumCodes,
  ReferrerSourceCodes,
  ReferrerChannelCodes,
  VisitorTypeCodes,
  ContextCodes,
  InputCodes,
} from '../constants/codes';

export type ContextKey = keyof typeof ContextCodes;
export type InputKey = keyof typeof InputCodes;

export function pad2(n: number): string {
  // Pad to at least 2 digits; allow values > 99 without truncation
  if (n === null || n === undefined) return '';
  return n.toString().padStart(2, '0');
}

export function encodeContextCode(key: ContextKey, value: number): string {
  const prefix = ContextCodes[key];
  if (prefix == null) throw new Error(`Unknown context key: ${key}`);
  const padded = pad2(value);
  return `${prefix}${padded}`;
}

export type InputValue = string | number;

export function encodeInputCode(key: InputKey, value: InputValue, format: 'name' | 'index' = 'name'): string {
  const prefix = InputCodes[key];
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
