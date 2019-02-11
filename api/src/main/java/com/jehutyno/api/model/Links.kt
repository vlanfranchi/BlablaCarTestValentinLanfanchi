package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @SerialName("_front") val front: String
)