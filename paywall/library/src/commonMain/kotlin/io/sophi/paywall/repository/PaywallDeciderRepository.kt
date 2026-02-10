package io.sophi.paywall.repository

import io.sophi.paywall.models.DeviceDimensions
import io.sophi.paywall.models.Settings
import io.sophi.paywall.models.UserDimensions
import io.sophi.paywall.utils.HttpClientProvider

/**
 * Repository class for managing `PaywallDecider` instances.
 * This class interacts with `UserDimensionRepository` and `DeviceDimensionRepository`
 * to provide the necessary data for creating `PaywallDecider` objects.
 *
 * @property userRepository The repository with user dimension data.
 * @property deviceRepository The repository with device dimension data.
 */

class PaywallDeciderRepository() {
    private lateinit var userRepository: UserDimensionRepository
    private lateinit var deviceRepository: DeviceDimensionRepository

    private constructor(userRepository: UserDimensionRepository, deviceRepository: DeviceDimensionRepository) : this() {
        this.userRepository = userRepository
        this.deviceRepository = deviceRepository
    }

    companion object {
        /**
         * Factory method to create a new instance of `PaywallDeciderRepository`.
         *
         * @param userRepository The repository for user dimension data.
         * @param deviceRepository The repository for device dimension data.
         * @return A new instance of `PaywallDeciderRepository`.
         */
        fun createNew(
            userRepository: UserDimensionRepository,
            deviceRepository: DeviceDimensionRepository
        ): PaywallDeciderRepository {
            // implementation
            return PaywallDeciderRepository(userRepository, deviceRepository)
        }

        // Alternative factory method to create a new instance of `PaywallDeciderRepository` from RN bridge
        fun createNewFromData(
            userDimensions: UserDimensions,
            deviceDimensions: DeviceDimensions
        ): PaywallDeciderRepository {
            // implementation
            val userRepository = object : UserDimensionRepository {
                override fun getAll(): UserDimensions {
                    return userDimensions
                }
            }
            val deviceRepository = object : DeviceDimensionRepository {
                override fun getAll(): DeviceDimensions {
                    return deviceDimensions
                }
            }
            return PaywallDeciderRepository(userRepository, deviceRepository)
        }


    }

    /**
     * Returns a `PaywallDecider` instance for the given host and settings.
     *
     * @param host The host for which the `PaywallDecider` is being retrieved.
     * @param settings A map of settings to configure.
     * @return A `PaywallDecider` instance configured for the given host.
     */
    fun getOneByHost(host: String, settings: Map<String, String>): PaywallDecider {
        val deciderSettings = Settings(settings)
        // TODO: see if singleton HTTPClient can be returned and closed if needed.
        return PaywallDecider(
            host = host,
            settings = deciderSettings,
            userDimensionRepository = userRepository,
            deviceDimensionRepository = deviceRepository,
            httpClient = HttpClientProvider.getClient(deciderSettings)
        )
    }
}