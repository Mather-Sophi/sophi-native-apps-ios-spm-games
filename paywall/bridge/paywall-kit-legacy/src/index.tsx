import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package '@mather-sophi/sophi-react-native-paywall-kit-legacy' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const SophiReactNativePaywallKitLegacy =
  NativeModules.SophiReactNativePaywallKitLegacy
    ? NativeModules.SophiReactNativePaywallKitLegacy
    : new Proxy(
        {},
        {
          get() {
            throw new Error(LINKING_ERROR);
          },
        }
      );

// Export models
export {
  type UserDimensions,
  type DeviceDimensions,
  type WallDecision,
  type WallDecisionExperiment,
  type WallDecisionOutcome,
  type Session,
  type Visitor,
  type ReferrerData,
  type VisitorType,
  type ReferrerChannel,
  type ReferrerMedium,
  type ReferrerSource,
} from './models';

// Export repository classes for controlled initialization
export {
  PaywallDeciderRepository,
  PaywallDecider,
  type UserDimensionRepository,
  type DeviceDimensionRepository,
} from './repository';
