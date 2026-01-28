package io.sophi.paywall.utils

import java.util.*


actual fun generateUUID(): String {
    val uuid = UUID.randomUUID()
    return uuid.toString()
}

actual fun getCurrentISOTimestamp(): String {
    val calendar = Calendar.getInstance()
    val isoFormatter = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
    return isoFormatter.format(calendar.time)
}

actual fun getCurrentHour(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY)
}