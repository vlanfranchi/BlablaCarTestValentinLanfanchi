package com.jehutyno.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("issued_at") val issuedAt: Long,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("scopes") val scopes: List<String>)