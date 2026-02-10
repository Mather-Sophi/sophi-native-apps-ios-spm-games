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
    ).then(() => {
      this.initialized = true;
    }).catch((error: any) => {
      console.error("Error initializing PaywallDeciderRepository:", error); 
    })
  }

  /**
   * Get a PaywallDecider instance for a specific host and settings
   * @param host API host URL
   * @param settings Configuration settings
   * @returns Promise<PaywallDecider> instance
   */
  async getOneByHost(
    host: string,
    settings: Record<string, string | null>
  ): Promise<PaywallDecider> {

    await NativePaywallKit.getPaywallDeciderConfigByHost(host, settings);

    return new PaywallDecider(
      host,
      settings,
      this.userDimensionsRepository,
      this.deviceDimensionsRepository 
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
  userDimensionsRepository: UserDimensionRepository;
  deviceDimensionsRepository: DeviceDimensionRepository;

  constructor(host: string, settings: Record<string, string | null>, userDimensionsRepository: UserDimensionRepository, deviceDimensionsRepository: DeviceDimensionRepository) {
    this.host = host;
    this.settings = settings;
    this.userDimensionsRepository = userDimensionsRepository;
    this.deviceDimensionsRepository = deviceDimensionsRepository;
  }
  /**
   * Make a paywall decision for a content item
   * @param contentId The ID of the content to evaluate
   * @param assignedGroup Optional experiment group assignment
   * @returns WallDecision with outcome and experiment data
   */
  decide(contentId: string, assignedGroup: string, contentProperties?: Record<string, any>, userProperties?: Record<string, any>): Promise<WallDecision> {
    NativePaywallKit.updateDimensions(
      this.userDimensionsRepository.getAll(), 
      this.deviceDimensionsRepository.getAll()
    ).then(() => {
      console.debug("Dimensions updated successfully before decision.");
    }).catch((error: any) => {
      console.error("Error updating dimensions before decision:", error);
    });
    return NativePaywallKit.decide(
        contentId, 
        assignedGroup, 
        contentProperties, 
        userProperties
      );
  }
}
