package io.sophi.paywall.repository

import io.sophi.paywall.models.UserDimensions


interface UserDimensionRepository {
    fun getAll(): UserDimensions
}