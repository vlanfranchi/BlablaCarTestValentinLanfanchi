package com.jehutyno.blablacartestvalentinlanfranchi.results

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.jehutyno.api.model.Trips
import com.jehutyno.blablacartestvalentinlanfranchi.AndroidApp
import com.jehutyno.blablacartestvalentinlanfranchi.search.TripItem
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
                it.tripsChanged(TripConverter.convert(newValue?.trips))
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

    fun getTrips(departure: String, destination: String) {
        launch(Dispatchers.Main) {
            try {
                val trips = withContext(Dispatchers.IO) {
                    getApplication<AndroidApp>().api.getTrips(departure, destination)
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
    fun tripsChanged(trips: List<TripItem>?)
    fun onError(message: String?)
}