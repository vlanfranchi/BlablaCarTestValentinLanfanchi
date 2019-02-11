package com.jehutyno.blablacartestvalentinlanfranchi

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.jehutyno.api.Trips
import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.response.readText
import kotlinx.coroutines.*
import kotlinx.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class DataViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private var tripsObservable by Delegates.observable<Trips?>(null) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            listeners.forEach {
                it.tripsChanged(newValue)
            }
        }
    }

    val listeners: MutableList<TripsListener> = mutableListOf()

    override val coroutineContext: CoroutineContext = Job()

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
        listeners.clear()
    }

    fun getTrips() {
        launch(Dispatchers.Main) {
            try {
                val trips = withContext(Dispatchers.IO) {
                    getApplication<AndroidApp>().api.getTrips()
                }
                tripsObservable = trips
            } catch (exception: BadResponseStatusException) {
                println(exception.response.readText())
                listeners.forEach {
                    it.onError(exception.message)
                }
            } catch (exception: IOException) {
                listeners.forEach {
                    it.onError(exception.message)
                }
            }
        }
    }
}

interface TripsListener {
    fun tripsChanged(trips: Trips?)
    fun onError(message: String?)
}