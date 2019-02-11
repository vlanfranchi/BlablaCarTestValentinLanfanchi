package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trips (
    @SerialName("distance") val distance: Long,
    @SerialName("duration") val duration: Long,
    @SerialName("trips") val trips: List<Trip>
)