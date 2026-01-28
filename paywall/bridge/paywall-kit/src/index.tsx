import type {
  UserDimensions,
  DeviceDimensions,
  WallDecision,
  WallDecisionExperiment,
  WallDecisionOutcome,
  Session,
  Visitor,
  ReferrerData,
} from './NativePaywallKit';

// Export repository classes for controlled initialization
export {
  PaywallDeciderRepository,
  PaywallDecider,
  type UserDimensionRepository,
  type DeviceDimensionRepository,
} from './repository';

// Re-export types
export type {
  UserDimensions,
  DeviceDimensions,
  WallDecision,
  WallDecisionExperiment,
  WallDecisionOutcome,
  Session,
  Visitor,
  ReferrerData
};
