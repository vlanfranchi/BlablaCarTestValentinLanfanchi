package com.jehutyno.api

import com.jehutyno.api.model.Token
import com.jehutyno.api.model.Trips
import io.ktor.client.HttpClient
import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.json


class API {

    var token: Token? = null

    val http = HttpClient().config {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict)
        }
        install(DefaultRequest) {
           /* header("Accept-Encoding", "gzip")
            header("Accept-Language", "fr")
            header("Application-Client", "Android")
            header("Application-Version", "5.20.0-debug-33fbb08d3")
            header("Connection", "Keep-Alive")
            header("X-Client", "ANDROID|5.25.0")
            header("X-Currency", "EUR")
            header("X-Locale", "fr_FR")*/
        }
        install(Logging) {
            level = LogLevel.INFO
            logger = Logger.SIMPLE
        }
    }

    suspend fun getToken(): Token {
        return http.post("https://edge.blablacar.com/token") {
            val json = json {
                "grant_type" to "client_credentials"
                "client_id" to "android-technical-tests"
                "client_secret" to "Y1oAL3QdPfVhGOWj3UeDjo3q02Qwhvrj"
            }.toString()
            body = TextContent(json, contentType = ContentType.Application.Json)
        }
    }

    suspend fun getTrips(departure: String, destination: String): Trips {
        return oAuth(0) {
            http.get<Trips>("https://edge.blablacar.com/api/v2/trips") {
                header("Authorization", "Bearer " + token?.accessToken)
                parameter("_format", "json")
                parameter("locale", "fr_FR")
                parameter("cur", "EUR")
                parameter("fn", departure)
                parameter("tn", destination)
            }
        }
    }

    private suspend fun<T> oAuth(attempt: Int, block:suspend () -> T): T {
        return try {
            block()
        } catch (exception: BadResponseStatusException) {
            if (attempt < 5 && exception.statusCode.value == HttpStatusCode.Unauthorized.value) {
                token = getToken()
                oAuth(attempt + 1, block)
            } else {
                throw exception
            }
        }
    }

}
