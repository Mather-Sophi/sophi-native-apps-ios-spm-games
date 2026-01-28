package io.sophi.paywall.repository

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.sophi.paywall.configuration.ConfigurationClient
import io.sophi.paywall.decider.onDevice.OnDeviceClient
import io.sophi.paywall.decider.paywallApi.PaywallApiClient
import io.sophi.paywall.models.*
import io.sophi.paywall.utils.getNetworkManager
import kotlinx.serialization.Serializable


@Serializable
data class PaywallDeciderConfig(
    val host: String,
    val settings: Map<String, String?>,
    val userDimensions: UserDimensions,
    val deviceDimensions: DeviceDimensions
)

class PaywallDecider(
    val host: String,
    val settings: Settings,
    var userDimensionRepository: UserDimensionRepository,
    var deviceDimensionRepository: DeviceDimensionRepository,
    val httpClient: HttpClient
) {

    private val configuration = ConfigurationClient(settings, httpClient).getExperienceByHost(host)
    private var paywallApiClient: PaywallApiClient = PaywallApiClient(host, settings, httpClient)
    private var onDeviceClient: OnDeviceClient = OnDeviceClient(configuration)
    val networkManager = getNetworkManager()

    val config: PaywallDeciderConfig
        get() {
            return PaywallDeciderConfig(
                host,
                settings.map,
                userDimensionRepository.getAll(),
                deviceDimensionRepository.getAll()
            )
        }


    suspend fun decide(
        contentId: String,
        assignedGroup: String? = null,
        contentProperties: Map<String, Any?>? = null,
        userProperties: Map<String, Any?>? = null
    ): WallDecision {
        val contentDimensions = ContentDimensions(
            id = contentId, properties = contentProperties
        )

        val context = Context.newFromUserAndDeviceDimensions(
            contentDimensions = contentDimensions,
            userDimensions = userDimensionRepository.getAll(),
            deviceDimensions = deviceDimensionRepository.getAll(),
            userProperties = userProperties,
            assignedGroup = assignedGroup
        )

        val onDeviceDecision: WallDecision = onDeviceClient.getDecision(context)
        Logger.d("On-Device Decision: $onDeviceDecision")

        val decision = if (networkManager.isOnline()) {
            val paywallApiDecision: WallDecision? = paywallApiClient.getDecision(
                context = context,
                onDeviceTrace = onDeviceDecision.trace
            )
            paywallApiDecision ?: onDeviceDecision
        } else {
            // TODO: use noInternetPolicy
            onDeviceDecision
        }

        return decision.copy(
            context = context.getEncodedContext(),
            inputs = context.getEncodedInputs(),
            userProperties = context.getEncodedUserProperties(configuration.userPropertyCodes),
            contentProperties = context.getEncodedContentProperties(configuration.contentPropertyCodes)
        )
    }


    // To be used by RN bridge to update dimensions
    fun updateDimensions(
        userDimensions: UserDimensions? = null,
        deviceDimensions: DeviceDimensions? = null
    ) {
        userDimensions?.let {
            userDimensionRepository = object : UserDimensionRepository {
                override fun getAll(): UserDimensions {
                    return it
                }
            }
        }

        deviceDimensions?.let {
            deviceDimensionRepository = object : DeviceDimensionRepository {
                override fun getAll(): DeviceDimensions {
                    return it
                }
            }
        }
    }
}