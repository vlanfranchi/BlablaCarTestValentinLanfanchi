package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    @SerialName("value") val value: Int,
    @SerialName("currency") val currency: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("string_value") val string: String

)