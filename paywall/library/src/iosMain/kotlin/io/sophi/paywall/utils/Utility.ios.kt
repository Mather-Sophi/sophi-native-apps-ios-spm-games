package io.sophi.paywall.utils

import platform.Foundation.*


actual fun generateUUID(): String {
    val uuid = NSUUID()
    return uuid.UUIDString().lowercase()
}

actual fun getCurrentISOTimestamp(): String {
    val now = NSDate()
    val isoFormatter = NSISO8601DateFormatter()
    return isoFormatter.stringFromDate(now)
}

actual fun getCurrentHour(): Int {
    val now = NSDate()
    return NSCalendar.currentCalendar().component(
        NSCalendarUnitHour,
        fromDate = now
    ).toInt()
}