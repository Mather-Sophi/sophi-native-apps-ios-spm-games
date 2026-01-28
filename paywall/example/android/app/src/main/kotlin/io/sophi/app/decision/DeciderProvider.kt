package io.sophi.app.decision

import io.sophi.app.repository.NativeDeviceDimensionRepository
import io.sophi.app.repository.NativeUserDimensionRepository
import io.sophi.paywall.repository.PaywallDeciderRepository


object DeciderProvider {
    private val deciders = initializeDecider()
    val decider = deciders.getOneByHost(
        "www.sophi.io", settings = mapOf(
            "apiTimeoutInMillis" to "5000",
            "onDeviceModelRepositoryUrl" to "http://10.0.2.2:8000"
        )
    )
    fun initializeDecider(): PaywallDeciderRepository {
        return PaywallDeciderRepository.createNew(NativeUserDimensionRepository, NativeDeviceDimensionRepository)
    }
}
