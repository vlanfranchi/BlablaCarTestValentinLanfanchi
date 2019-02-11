package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("links") val links: Links,
    @SerialName("display_name") val displayName: String,
    @SerialName("picture") val picture: String
)