package com.jehutyno.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TripRequest(val _format: String, val locale: String, val cur: String, val fn: String, val tn: String)