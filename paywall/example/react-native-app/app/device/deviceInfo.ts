import { Platform, PlatformAndroidStatic, PlatformIOSStatic } from "react-native";

// ============================================================================
// TYPES & CONSTANTS
// ============================================================================

/** All possible device types */
const deviceTypes = [
  "console",
  "mobile",
  "tablet",
  "smarttv",
  "wearable",
  "desktop",
  "embedded",
  "xr",
  "other",
  "",
] as const;

/** Numeric codes for device types (for model input encoding) */
export const DeviceTypeCodes = {
  "console": 0,
  "mobile": 1,
  "tablet": 2,
  "smarttv": 3,
  "wearable": 4,
  "desktop": 5,
  "embedded": 6,
  "xr": 7,
  "other": 8,
} as const;

/** Device type extracted from user agent */
export type DeviceType = typeof deviceTypes[number];

/**
 * Device information extracted from user agent string.
 * @property viewer - App version (e.g., '1.0.0', '2.3.4') or empty string if unknown
 * @property os - Operating system (e.g., 'iOS', 'Windows') or empty string if unknown
 * @property type - Device type (e.g., 'mobile', 'desktop') or empty string if unknown
 */
export type Device = {
  viewer: string;
  os: string;
  type: DeviceType;
}

// ============================================================================
// MAIN EXPORT FUNCTION
// ============================================================================

/**
 * Parses a user agent string to extract device information.
 * 
 * @param userAgent - A user agent string (defaults to current browser's UA if not provided)
 * @returns Device object with viewer (browser), os, and type properties
 * 
 * @example
 * // Parse current browser
 * const device = getDeviceInfo();
 * // Returns: { viewer: 'app-version', os: 'iOS', type: 'mobile' }
 * 
 * @example
 * // Parse a specific user agent
 * const device = getDeviceInfo();
 */
export const getDeviceInfo = function (): Device {
  try {
    const viewer = "mobile-app-v1"; // Placeholder since React Native does not expose browser
    const os = Platform.OS;
    const constants = Platform.constants;
    let type: DeviceType = "other";
    Platform.select({
      ios: () => {
        const iosConstants = constants as PlatformIOSStatic["constants"];
        const deviceType = iosConstants.interfaceIdiom;
        if (deviceType === "phone") {
          type = "mobile";
        } else if (deviceType === "pad") {
          type = "tablet";
        } else if (deviceType === "tv") {
          type = "smarttv";
        } else if (deviceType === "mac") {
          type = "desktop";
        }
        return { viewer: "Safari", type, os: "iOS" };
      },
      android: () => {
        const androidConstants = constants as PlatformAndroidStatic["constants"];
        const mode = androidConstants.uiMode;
        if(mode === "car") {
          type = "embedded";
        } else if(mode === "desk") {
          type = "desktop";
        } else if(mode === "normal") {
          type = "mobile"; 
        } else if(mode === "tv") {
          type = "smarttv";
        } else if(mode === "watch") {
          type = "wearable";
        }
        return { viewer: "Chrome", type, os: "Android" };
      },
      default: () => {
        return { viewer: "", type: "other", os: "other" };
      },
    })();



    return { viewer, type, os };
  } catch (error) {
    return { viewer: "", type: "", os: "" };
  }
}

export default getDeviceInfo;