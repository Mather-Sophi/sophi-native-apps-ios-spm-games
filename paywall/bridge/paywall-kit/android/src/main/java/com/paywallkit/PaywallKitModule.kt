package com.paywallkit

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReadableType
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
    userDimensions: ReadableMap?,
    deviceDimensions: ReadableMap?,
    promise: Promise?
  ) {
    try{
      if (deviceDimensions == null) {
        promise?.reject(INVALID_ARGUMENT_ERROR, "Device dimensions cannot be null")
        return
      }
      if (userDimensions == null) {
        promise?.reject(INVALID_ARGUMENT_ERROR, "User dimensions cannot be null")
        return
      }
      deciderRepository = PaywallDeciderRepository.createNewFromData(
        userDimensions =  UserDimensions.fromMap(userDimensions.toHashMap()),
        deviceDimensions = DeviceDimensions.fromMap(deviceDimensions.toHashMap())
      )
      promise?.resolve(null)
    } catch(e: Exception){
      promise?.reject(INITIALIZATION_ERROR, "Failed to initialize PaywallDeciderRepository", e)
    }
  }

  override fun getPaywallDeciderConfigByHost(
    host: String?,
    settings: ReadableMap?,
    promise: Promise?
  ) {
    if (host.isNullOrEmpty()) {
      promise?.reject(INVALID_ARGUMENT_ERROR, "Host cannot be null or empty")
      return
    }

    if (deciderRepository == null) {
      promise?.reject(NOT_INITIALIZED_ERROR, "PaywallDeciderRepository is not initialized.")
      return
    }

    try {
      paywallDecider = deciderRepository?.getOneByHost(
        host = host,
        settings = toMap(settings)
      )
      promise?.resolve(convertConfigToMap(paywallDecider?.config))
    } catch(e: Exception){
      promise?.reject(PAYWALL_DECIDER_FETCH_ERROR, "Unable to fetch decider for host ${host}.", e)
    }
  }

  override fun decide(
    contentId: String?,
    assignedGroup: String?,
    contentProperties: ReadableMap?,
    userProperties: ReadableMap?,
    promise: Promise?
  ) {

    if (contentId.isNullOrEmpty()) {
      promise?.reject(INVALID_ARGUMENT_ERROR, "ContentId cannot be null or empty")
      return
    }

    if(paywallDecider == null){
      promise?.reject(NOT_INITIALIZED_ERROR, "PaywallDecider is not initialized. Call getOneByHost to get the PaywallDecider.")
      return
    }
    try {
      runBlocking {
        val decision = paywallDecider?.decide(
          contentId = contentId,
          assignedGroup = assignedGroup,
          contentProperties = toMapOfAny(contentProperties),
          userProperties = toMapOfAny(userProperties)
        )
        promise?.resolve(convertDecisionToMap(decision))
      }
    } catch(e: Exception){
      promise?.reject(DECISION_ERROR, "Error occurred while making decision for contentId ${contentId}.", e)
    }
  }

  override fun updateDimensions(
    userDimensions: ReadableMap?,
    deviceDimensions: ReadableMap?,
    promise: Promise?
  ) {
    try {
      paywallDecider?.updateDimensions(
        userDimensions = userDimensions?.let { UserDimensions.fromMap(it.toHashMap()) },
        deviceDimensions = deviceDimensions?.let { DeviceDimensions.fromMap(it.toHashMap()) }
      )
      promise?.resolve(null)
    } catch (e: Exception) {
      promise?.reject(UPDATE_ERROR, "Error updating dimensions: ${e.message}", e)
    }
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

  private fun convertDecisionToMap(decision: WallDecision?): WritableMap {
    val map = WritableNativeMap()
    if (decision == null) return map

    map.putString("id", decision.id)
    map.putString("createdAt", decision.createdAt)
    decision.trace?.let { map.putString("trace", it) }
    decision.context?.let { map.putString("context", it) }
    decision.inputs?.let { map.putString("inputs", it) }
    decision.searchParams?.let { map.putString("searchParams", it) }
    decision.experimentsCode?.let { map.putString("experimentsCode", it) }
    decision.paywallScore?.let { map.putInt("paywallScore", it) }
    decision.userProperties?.let { map.putString("userProperties", it) }
    decision.contentProperties?.let { map.putString("contentProperties", it) }

    val outcomeMap = WritableNativeMap()
    if (decision.outcome.wallType == null) {
      outcomeMap.putNull("wallType")
      outcomeMap.putNull("wallTypeCode")
    } else {
      outcomeMap.putString("wallType", decision.outcome.wallType.toString())
      outcomeMap.putInt("wallTypeCode", decision.outcome.wallTypeCode!!)
    }

    outcomeMap.putString("wallVisibility", decision.outcome.wallVisibility.toString())
    outcomeMap.putInt("wallVisibilityCode", decision.outcome.wallVisibilityCode)
    map.putMap("outcome", outcomeMap)

    return map
  }

  fun toMap(readableMap: ReadableMap?): Map<String, String> {
    if (readableMap == null) return emptyMap()
    val map = mutableMapOf<String, String>()
    val iterator = readableMap.keySetIterator()
    while (iterator.hasNextKey()) {
      val key = iterator.nextKey()
      when (readableMap.getType(key)) {
        ReadableType.String -> map[key] = readableMap.getString(key) ?: ""
        else -> {}
      }
    }
    return map
  }

  fun toMapOfAny(readableMap: ReadableMap?): Map<String, Any?>? {
    if (readableMap == null) return null
    val map = mutableMapOf<String, Any?>()
    val iterator = readableMap.keySetIterator()
    while (iterator.hasNextKey()) {
      val key = iterator.nextKey()
      when (readableMap.getType(key)) {
        ReadableType.String -> map[key] = readableMap.getString(key)
        ReadableType.Number -> map[key] = readableMap.getDouble(key)
        ReadableType.Boolean -> map[key] = readableMap.getBoolean(key)
        ReadableType.Map -> map[key] = toMapOfAny(readableMap.getMap(key))
        ReadableType.Array -> {
          val array = readableMap.getArray(key)
          val list = mutableListOf<Any?>()
          if (array != null) {
            for (i in 0 until array.size()) {
              when (array.getType(i)) {
                ReadableType.String -> list.add(array.getString(i))
                ReadableType.Number -> list.add(array.getDouble(i))
                ReadableType.Boolean -> list.add(array.getBoolean(i))
                ReadableType.Map -> list.add(toMapOfAny(array.getMap(i)))
                ReadableType.Array -> list.add(null) // Nested arrays not handled
                ReadableType.Null -> list.add(null)
              }
            }
          }
          map[key] = list
        }

        ReadableType.Null -> map[key] = null
      }
    }
    return map
  }

  companion object {
    const val NAME = "PaywallKit"
  }
}
