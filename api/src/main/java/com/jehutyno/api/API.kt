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
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json


class API {

    val http = HttpClient().config {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict)
        }
        install(DefaultRequest) {
            header("Accept", "application/json")
        }
        install(Logging) {
            level = LogLevel.BODY
            logger = Logger.SIMPLE
        }
        ResponseObserver {
            println(it.status)
        }
    }

    //suspend fun

    suspend fun getTrips(): Trips {
        return oAuth(0) {
            http.get<Trips>("https://edge.blablacar.com/api/v2/trips") {

            }
        }
    }

    private suspend fun<T> oAuth(attempt: Int, block:suspend () -> T): T {
        return try {
            block()
        } catch (exception: BadResponseStatusException) {
            if (attempt < 5 && exception.statusCode == HttpStatusCode.Unauthorized) {

                oAuth(attempt + 1, block)
            } else {
                throw exception
            }
        }
    }

}
