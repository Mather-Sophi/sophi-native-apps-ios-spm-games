package io.sophi.paywall.enums

enum class DeviceType(val value: String, val code: Int) {
    CONSOLE("console", 0),
    MOBILE("mobile", 1),
    TABLET("tablet", 2),
    SMART_TV("smarttv", 3),
    WEARABLE("wearable", 4),
    DESKTOP("desktop", 5),
    EMBEDDED("embedded", 5),
    XR("xr", 5),
    OTHER("other", 5),
    UNKNOWN("", 5),
    NATIVE(value = "native", code = 6)
}