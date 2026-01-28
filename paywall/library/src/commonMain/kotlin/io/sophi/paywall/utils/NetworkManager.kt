package io.sophi.paywall.utils

interface NetworkManager {
    fun isOnline(): Boolean
}

expect fun getNetworkManager(): NetworkManager