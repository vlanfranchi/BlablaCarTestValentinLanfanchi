package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    @SerialName("departure_date") val departureDate: String,
    @SerialName("locations_to_display") val locationsToDisplay: List<String>,
    @SerialName("price") val price: Price,
    @SerialName("user") val user: User
)