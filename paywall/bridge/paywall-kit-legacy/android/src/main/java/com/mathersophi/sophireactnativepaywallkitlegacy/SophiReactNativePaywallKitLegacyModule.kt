package com.mathersophi.sophireactnativepaywallkitlegacy

import com.facebook.react.bridge.*
import io.sophi.paywall.models.DeviceDimensions
import io.sophi.paywall.models.UserDimensions
import io.sophi.paywall.models.WallDecision
import io.sophi.paywall.repository.PaywallDecider
import io.sophi.paywall.repository.PaywallDeciderConfig
import io.sophi.paywall.repository.PaywallDeciderRepository
import kotlinx.coroutines.runBlocking


class SophiReactNativePaywallKitLegacyModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private var deciderRepository: PaywallDeciderRepository? = null
  private var paywallDecider: PaywallDecider? = null

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun initializePaywallDeciderRepository(
    userDimensions: ReadableMap,
    deviceDimensions: ReadableMap,
    promise: Promise
  ) {
    deciderRepository = PaywallDeciderRepository.createNewFromData(
      userDimensions = UserDimensions.fromMap(userDimensions.toHashMap()),
      deviceDimensions = DeviceDimensions.fromMap(deviceDimensions.toHashMap())
    )
    promise.resolve(null)
  }

  @ReactMethod
  fun getPaywallDeciderConfigByHost(
    host: String?,
    settings: ReadableMap?,
    promise: Promise
  ) {
    if (host.isNullOrEmpty()) {
      throw IllegalArgumentException("Host cannot be null or empty")
    }

    paywallDecider = deciderRepository?.getOneByHost(
      host = host,
      settings = toMap(settings)
    )
    val config = paywallDecider?.config
    if (config == null) {
      promise.reject("not_found", "No PaywallDecider found for host: $host")
    }
    promise.resolve(convertConfigToMap(config))
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

  @ReactMethod
  fun decide(
    contentId: String,
    assignedGroup: String?,
    contentProperties: ReadableMap?,
    userProperties: ReadableMap?,
    promise: Promise
  ) {
    runBlocking {
      try {
        val decision = paywallDecider?.decide(
          contentId = contentId,
          assignedGroup = assignedGroup,
          contentProperties = toMapOfAny(contentProperties),
          userProperties = toMapOfAny(userProperties)
        )
        promise.resolve(convertDecisionToMap(decision))
      } catch (e: Exception) {
        promise.reject("decision_error", "Error making paywall decision: ${e.message}", e)
      }
    }
  }

  @ReactMethod
  fun updateDimensions(userDimensions: ReadableMap?, deviceDimensions: ReadableMap?, promise: Promise) {
    try {
      paywallDecider?.updateDimensions(
        userDimensions = userDimensions?.let { UserDimensions.fromMap(it.toHashMap()) },
        deviceDimensions = deviceDimensions?.let { DeviceDimensions.fromMap(it.toHashMap()) }
      )
      promise.resolve(null)
    } catch (e: Exception) {
      promise.reject("update_error", "Error updating dimensions: ${e.message}", e)
    }

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

  companion object {
    const val NAME = "SophiReactNativePaywallKitLegacy"
  }
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
