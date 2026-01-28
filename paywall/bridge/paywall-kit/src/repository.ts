import NativePaywallKit from './NativePaywallKit';
import type {
  UserDimensions,
  DeviceDimensions,
  WallDecision,
} from './NativePaywallKit';


export interface UserDimensionRepository {
  getAll(): UserDimensions;
}

export interface DeviceDimensionRepository {
  getAll(): DeviceDimensions;
}

/**
 * PaywallDeciderRepository - Manages paywall decision logic with stateful configuration
 * @example
 * ```typescript
 * const repository = PaywallDeciderRepository.createNew(userDimensionsRepository, deviceDimensionsRepository);
 * const decider = repository.getOneByHost('www.example.com', { apiTimeout: 'value' });
 * const decision = decider.decide('content-123', 'control');
 * ```
 */
export class PaywallDeciderRepository {
  private userDimensionsRepository: UserDimensionRepository;
  private deviceDimensionsRepository: DeviceDimensionRepository;
  private initialized: boolean = false;

  /**
   * Create a new PaywallDeciderRepository
   * @param userDimensionsRepository
   * @param deviceDimensionsRepository
   */
  private constructor(
    userDimensionsRepository: UserDimensionRepository,
    deviceDimensionsRepository: DeviceDimensionRepository
  ) {
    this.userDimensionsRepository = userDimensionsRepository;
    this.deviceDimensionsRepository = deviceDimensionsRepository;
    this.initialize();
  }

  static createNew(
    userDimensionsRepository: UserDimensionRepository,
    deviceDimensionsRepository: DeviceDimensionRepository
  ) {
    return new PaywallDeciderRepository(
      userDimensionsRepository,
      deviceDimensionsRepository
    );
  }

  /**
   * Initialize the native repository
   * @private
   */
  private initialize(): void {
    if (this.initialized) {
      return;
    }

    NativePaywallKit.initializePaywallDeciderRepository(
      this.userDimensionsRepository.getAll(),
      this.deviceDimensionsRepository.getAll()
    );
    this.initialized = true;
  }

  /**
   * Get a PaywallDecider instance for a specific host and settings
   * @param host API host URL
   * @param settings Configuration settings
   * @returns PaywallDecider instance
   */
  getOneByHost(
    host: string,
    settings: Record<string, string | null>
  ): PaywallDecider {

    NativePaywallKit.getPaywallDeciderConfigByHost(host, settings);

    return new PaywallDecider(
      host,
      settings
    );
  }
}

/**
 * PaywallDecider - Makes paywall decisions for content items
 * 
 * This class represents a configured decider for a specific host and settings.
 * It should be obtained from PaywallDeciderRepository.getOneByHost()
 */
export class PaywallDecider {

    host: string;
    settings: Record<string, string | null>;

  constructor(
    host: string,
    settings: Record<string, string | null>
  ) {
    this.host = host;
    this.settings = settings;
  }
  /**
   * Make a paywall decision for a content item
   * @param contentId The ID of the content to evaluate
   * @param assignedGroup Optional experiment group assignment
   * @returns WallDecision with outcome and experiment data
   */
  decide(contentId: string, assignedGroup: string): WallDecision {
    return NativePaywallKit.decide(contentId, assignedGroup);
  }
}
