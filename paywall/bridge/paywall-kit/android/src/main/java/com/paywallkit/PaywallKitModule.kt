package com.paywallkit

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.module.annotations.ReactModule
import io.sophi.paywall.models.DeviceDimensions
import io.sophi.paywall.models.UserDimensions
import io.sophi.paywall.models.WallDecision
import io.sophi.paywall.repository.PaywallDecider
import io.sophi.paywall.repository.PaywallDeciderConfig

import io.sophi.paywall.repository.PaywallDeciderRepository
import kotlinx.coroutines.runBlocking

@ReactModule(name = PaywallKitModule.NAME)
class PaywallKitModule(reactContext: ReactApplicationContext) :
  NativePaywallKitSpec(reactContext) {

  private var deciderRepository: PaywallDeciderRepository? = null
  private var paywallDecider: PaywallDecider? = null

  override fun getName(): String {
    return NAME
  }

  override fun initializePaywallDeciderRepository(
    userDimensions: ReadableMap,
    deviceDimensions: ReadableMap
  ) {
    deciderRepository = PaywallDeciderRepository.createNewFromData(
      userDimensions = UserDimensions.fromMap(userDimensions.toHashMap()),
      deviceDimensions = DeviceDimensions.fromMap(deviceDimensions.toHashMap())
    )
  }

  override fun getPaywallDeciderConfigByHost(
    host: String?,
    settings: ReadableMap?
  ): WritableMap? {
    if (host.isNullOrEmpty()) {
      throw IllegalArgumentException("Host cannot be null or empty")
    }

    // ToDo combine in one single call
    paywallDecider = deciderRepository?.getOneByHost(
      host = host ?: "",
      settings = mapOf()//settings?.toHashMap()
    )
    val config = deciderRepository?.getPaywallDeciderConfigByHost(
      host = host ?: "",
      settings = mapOf()//?.toHashMap()
    )
    return convertConfigToMap(config)
  }


  private fun convertConfigToMap(config: PaywallDeciderConfig?): WritableMap? {
    if (config == null) return null
    val map = WritableNativeMap()

    map.putString("host", config.host)

    val settingsMap = WritableNativeMap()
    config.settings.forEach { (key, value) ->
      when (value) {
        is String -> settingsMap.putString(key, value)
        null -> settingsMap.putNull(key)
      }
    }
    map.putMap("settings", settingsMap)
    map.putMap("userDimensions", WritableNativeMap()) // Add user dimensions conversion
    map.putMap("deviceDimensions", WritableNativeMap()) // Add device dimensions conversion

    return map
  }

  override fun decide(
    contentId: String,
    assignedGroup: String?,
    contentProperties: Map<String, Any?>?,
    userProperties: Map<String, Any?>?
  ): WritableMap {
    var decision: WallDecision? = null
    runBlocking {
      if (paywallDecider == null) {
        throw IllegalStateException("PaywallDecider is not initialized. Call getPaywallDeciderConfigByHost first.")
      }
      decision = paywallDecider?.decide(contentId, assignedGroup, contentProperties, userProperties)
    }
    return convertDecisionToMap(decision)
  }

  private fun convertDecisionToMap(decision: WallDecision?): WritableMap {
    val map = WritableNativeMap()
    if (decision == null) return map

    map.putString("id", decision.id)
    map.putString("createdAt", decision.createdAt)
    map.putString("trace", decision.trace)
    map.putString("context", decision.context)
    map.putString("inputs", decision.inputs)
    map.putString("searchParams", decision.searchParams)

    val outcomeMap = WritableNativeMap()
    if (decision.outcome.wallType == null)
      outcomeMap.putNull("wallType")
    else
      outcomeMap.putString("wallType", decision.outcome.wallType ?: "")

    if (decision.outcome.wallType == null)
      outcomeMap.putNull("wallTypeCode")
    else
      outcomeMap.putInt("wallTypeCode", decision.outcome.wallTypeCode!!)

    outcomeMap.putString("wallVisibility", decision.outcome.wallVisibility)
    outcomeMap.putInt("wallVisibilityCode", decision.outcome.wallVisibilityCode)
    map.putMap("outcome", outcomeMap)

    val experimentMap = WritableNativeMap()
    experimentMap.putString("experimentId", decision.experiment.experimentId)
    experimentMap.putString("assignedGroup", decision.experiment.assignedGroup)
    map.putMap("experiment", experimentMap)

    return map
  }

  companion object {
    const val NAME = "PaywallKit"
  }
}
