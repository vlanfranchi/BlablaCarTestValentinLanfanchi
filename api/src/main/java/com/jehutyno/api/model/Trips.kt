package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trips (
    @SerialName("trips") val trips: List<Trip>
)