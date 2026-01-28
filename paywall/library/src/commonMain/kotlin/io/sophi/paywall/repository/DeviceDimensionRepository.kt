package io.sophi.paywall.repository

import io.sophi.paywall.models.DeviceDimensions

interface DeviceDimensionRepository {
    fun getAll(): DeviceDimensions
}