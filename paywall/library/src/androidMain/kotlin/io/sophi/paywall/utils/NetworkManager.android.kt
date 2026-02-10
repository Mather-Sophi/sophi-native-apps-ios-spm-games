package io.sophi.paywall.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import co.touchlab.kermit.Logger
import io.sophi.paywall.applicationContext
import io.sophi.paywall.isApplicationContextInitialized
import io.sophi.paywall.utils.NetworkManagerLog as Logs

object AndroidNetworkManager : NetworkManager {
    @Volatile
    var isConnected = true
    override fun isOnline(): Boolean = isConnected

    init {
        if (isApplicationContextInitialized()) {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            connectivityManager?.let {
                val networkCallback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        isConnected = true
                        super.onAvailable(network)
                        Logger.d(Logs.NETWORK_ONLINE)
                    }

                    override fun onLost(network: Network) {
                        isConnected = false
                        super.onLost(network)
                        Logger.d(Logs.NETWORK_OFFLINE)
                    }
                }
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
            } ?: run {
                Logger.w(Logs.NETWORK_MANAGER_NOT_AVAILABLE)
                isConnected = true
            }
        } else {
            Logger.w(Logs.NETWORK_MANAGER_NOT_AVAILABLE)
            isConnected = true
        }
    }

}

actual fun getNetworkManager(): NetworkManager = AndroidNetworkManager