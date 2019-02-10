package com.jehutyno.api

import io.ktor.client.HttpClient
import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.json


class API {

    var token: Token? = null

    val http = HttpClient().config {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict)
        }
        install(DefaultRequest) {
     /*       header("Accept-Encoding", "gzip")
            header("Accept-Language", "fr")
            header("Application-Client", "Android")
            header("Application-Version", "5.20.0-debug-33fbb08d3")
            header("Connection", "Keep-Alive")
            header("X-Client", "ANDROID|5.25.0")
            header("X-Currency", "EUR")
            header("X-Locale", "fr_FR")*/
        }
        install(Logging) {
            level = LogLevel.BODY
            logger = Logger.SIMPLE
        }
        ResponseObserver {
            println(it.call.request.url)
            println(it.status)
        }
    }

    suspend fun getToken(): Token {
        return http.post("https://edge.blablacar.com/token") {
            //header("Accept", "application/json")
            header("Content-Type", "application/json")
            val lol = json {
                "grant_type" to "client_credentials"
                "client_id" to "android-technical-tests"
                "client_secret" to "Y1oAL3QdPfVhGOWj3UeDjo3q02Qwhvrj"
            }.toString()
            body = lol
        }
    }

    suspend fun getTrips(): Trips {
        return oAuth(0) {
            http.get<Trips>("https://edge.blablacar.com/api/v2/trips") {
                header("Authorization", "Bearer " + token?.access_token)
                parameter("_format", "json")
                parameter("locale", "fr_FR")
                parameter("cur", "EUR")
                parameter("fn", "Paris")
                parameter("tn", "Rennes")
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
