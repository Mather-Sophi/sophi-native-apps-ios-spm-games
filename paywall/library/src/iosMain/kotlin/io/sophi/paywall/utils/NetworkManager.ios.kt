package io.sophi.paywall.utils

import co.touchlab.kermit.Logger
import platform.Network.*
import platform.darwin.DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL
import platform.darwin.dispatch_queue_create


object IOSNetworkManager : NetworkManager {

    var isConnected: Boolean = true

    override fun isOnline(): Boolean = isConnected

    init {
        Logger.i("Creating Path Monitor")
        val monitor = nw_path_monitor_create()
        val queue = dispatch_queue_create(
            label = "sophi.paywall.network.monitor",
            attr = DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL,
        )
        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = nw_path_get_status(path)
            when (status) {
                nw_path_status_satisfied -> {
                    Logger.i("Network status: Satisfied")
                    val isWifi = nw_path_uses_interface_type(path, nw_interface_type_wifi)
                    Logger.d("Network is Wifi: $isWifi")

                    val isExpensive = nw_path_is_expensive(path)
                    Logger.d("Network is Expensive: $isExpensive")

                    val isConstrained = nw_path_is_constrained(path)
                    Logger.d("Network is Constrained: $isConstrained")

                    val isMetered = !isWifi && (isExpensive || isConstrained)
                    Logger.d("Network is Metered: $isMetered")

                    Logger.i("Network status changed: Online")
                    isConnected = true
                }

                nw_path_status_unsatisfied -> {
                    Logger.i("Network status: Unsatisfied")
                    Logger.i("Network status changed: Offline")
                    isConnected = false
                }

                nw_path_status_satisfiable -> {
                    Logger.i("Network status: Satisfiable")
                }

                else -> {
                    Logger.i("Network status: Unknown")
                    Logger.i("Network status changed: Offline")
                    isConnected = false
                }
            }
        }
        nw_path_monitor_set_queue(monitor, queue)
        nw_path_monitor_start(monitor)
        Logger.i("IOSNetworkManager initialized.")
    }

}

actual fun getNetworkManager(): NetworkManager = IOSNetworkManager