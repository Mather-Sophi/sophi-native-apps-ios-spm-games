package io.sophi.paywall.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import co.touchlab.kermit.Logger
import io.sophi.paywall.applicationContext
import io.sophi.paywall.isApplicationContextInitialized

object AndroidNetworkManager : NetworkManager {
    var isConnected = true
    override fun isOnline(): Boolean = isConnected

    init {
        if (isApplicationContextInitialized()) {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            connectivityManager?.let {
                Logger.i("NetworkManager initialized with ConnectivityManager.")
                val networkCallback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        isConnected = true
                        super.onAvailable(network)
                        Logger.i("NetworkManager detected network available.")
                    }

                }
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
            } ?: run {
                Logger.w("Unable to get NetworkManager. Falling back to default isOnline = true.")
                isConnected = true
            }
        } else {
            isConnected = true
            Logger.w("Application context is not initialized, falling back to default isOnline = true.")
        }
    }

}

actual fun getNetworkManager(): NetworkManager = AndroidNetworkManager