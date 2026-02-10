package io.sophi.paywall.utils

import co.touchlab.kermit.Logger
import platform.Network.*
import platform.darwin.DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL
import platform.darwin.dispatch_queue_create
import kotlin.concurrent.Volatile
import io.sophi.paywall.utils.NetworkManagerLog as Logs


object IOSNetworkManager : NetworkManager {

    @Volatile
    var isConnected: Boolean = true

    private val monitor = nw_path_monitor_create()

    override fun isOnline(): Boolean = isConnected

    init {
        val queue = dispatch_queue_create(
            label = "sophi.paywall.network.monitor",
            attr = DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL,
        )

        nw_path_monitor_set_update_handler(monitor) { path ->
            when (val status = nw_path_get_status(path)) {
                nw_path_status_satisfied -> {
                    isConnected = true
                    Logger.d(Logs.NETWORK_ONLINE)
                }

                nw_path_status_unsatisfied -> {
                    isConnected = false
                    Logger.d(Logs.NETWORK_OFFLINE)
                }

                nw_path_status_satisfiable -> {
                    isConnected = true
                    Logger.d(Logs.NETWORK_ONLINE)
                }

                else -> {
                    isConnected = false
                    Logger.d(Logs.NETWORK_OFFLINE)
                }
            }
        }
        nw_path_monitor_set_queue(monitor, queue)
        nw_path_monitor_start(monitor)
    }

}

actual fun getNetworkManager(): NetworkManager = IOSNetworkManager