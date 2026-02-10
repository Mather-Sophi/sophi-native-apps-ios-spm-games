import type { ReferrerMedium, ReferrerSource, ReferrerChannel, VisitorType } from '@mather-sophi/sophi-react-native-paywall-kit';

export const REFERRER_MEDIUM_OPTIONS: ReferrerMedium[] = [
  'campaign',
  'direct',
  'internal',
  'search',
  'social',
  'other',
];

export const REFERRER_SOURCE_OPTIONS: ReferrerSource[] = [
  'google',
  'yahoo',
  'duckduckgo',
  'bing',
  'facebook',
  'instagram',
  'x',
  't',
  'linkedin',
  'reddit',
  'newsletter',
];

export const REFERRER_CHANNEL_OPTIONS: ReferrerChannel[] = [
  'search',
  'news',
  'discover',
];

export const VISITOR_TYPE_OPTIONS: VisitorType[] = [
  'anonymous',
  'registered',
];
