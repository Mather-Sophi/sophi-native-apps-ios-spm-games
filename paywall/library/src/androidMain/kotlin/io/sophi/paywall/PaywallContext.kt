package io.sophi.paywall

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context

internal fun isApplicationContextInitialized(): Boolean = ::applicationContext.isInitialized

object PaywallContext

class PaywallContextInitializer : Initializer<PaywallContext> {

    override fun create(context: Context): PaywallContext {
        applicationContext = context
        return PaywallContext
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}
