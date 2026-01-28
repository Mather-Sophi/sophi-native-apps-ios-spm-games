import {
  DeviceDimensionRepository,
  DeviceDimensions,
  PaywallDecider,
  PaywallDeciderRepository,
  ReferrerData,
  Session,
  UserDimensionRepository,
  UserDimensions,
  Visitor,
  WallDecision
} from "@mather-sophi/sophi-react-native-paywall-kit";
import { getDeviceInfo } from "../device/deviceInfo";

export function mockUserDimensions(): UserDimensions {
  const session: Session = {
    campaignName: "sale",
    referrerDomain: "google.com",
    referrerMedium: "search",
    referrerSource: "google",
    referrerChannel: "search",
    timestamp: new Date().toISOString(),
  };

  const visitor: Visitor = {
    session: session,
  };

  const referrerData: ReferrerData = {
    medium: "search",
    source: "google",
    channel: "search",
  };

  return {
    todayPageViews: 3,
    todayPageViewsByArticle: 2,
    todayPageViewsByArticleWithPaywall: 1,
    todayPageViewsByArticleWithRegwall: 0,
    todayTopLevelSections: 2,
    todayTopLevelSectionsByArticle: 1,
    sevenDayPageViews: 7,
    sevenDayPageViewsByArticle: 5,
    sevenDayPageViewsByArticleWithPaywall: 2,
    sevenDayPageViewsByArticleWithRegwall: 1,
    sevenDayTopLevelSections: 3,
    sevenDayTopLevelSectionsByArticle: 2,
    sevenDayVisitCount: 4,
    twentyEightDayPageViews: 12,
    twentyEightDayPageViewsByArticle: 10,
    twentyEightDayPageViewsByArticleWithPaywall: 5,
    twentyEightDayPageViewsByArticleWithRegwall: 2,
    twentyEightDayTopLevelSections: 6,
    twentyEightDayTopLevelSectionsByArticle: 4,
    twentyEightDayVisitCount: 8,
    daysSinceLastVisit: 1,
    visitorType: "registered",
    visitor: visitor,
    timeZone: "America/New_York",
    referrer: "https://example.com",
    referrerData: referrerData,
    countryCode: "CA",
  };
}

export function mockDeviceDimensions(): DeviceDimensions {
  const deviceInfo = getDeviceInfo();
  console.log("Parsed device info:", deviceInfo);
  const hourOfDay = new Date().getHours();
  return {
    hourOfDay: hourOfDay,
    os: deviceInfo.os,
    type: deviceInfo.type,
    viewer: deviceInfo.viewer,
  };
}

class NativeDeviceDimensionRepository implements DeviceDimensionRepository {
  getAll(): DeviceDimensions {
    return mockDeviceDimensions();
  }
}

class NativeUserDimensionRepository implements UserDimensionRepository {
  getAll(): UserDimensions {
    return mockUserDimensions();
  }
}

let decider: PaywallDecider;
let repo: PaywallDeciderRepository

export function initializePaywallKit() {
    const userDimensionRepository = new NativeUserDimensionRepository();
    const deviceDimensionRepository = new NativeDeviceDimensionRepository();
    repo = PaywallDeciderRepository.createNew(userDimensionRepository, deviceDimensionRepository)
    console.log("PaywallDeciderRepository initialized successfully");
}

export function getPaywallDecision(
  contentId: string,
  assignedGroup?: string
): WallDecision {
  if (repo === undefined) {
    console.warn("PaywallDeciderRepository not initialized, initializing now.");
    initializePaywallKit();
  }
  decider = repo.getOneByHost('www.example.com', { apiTimeout: '5000' });
  return decider.decide(contentId, assignedGroup ? assignedGroup : "");
}
