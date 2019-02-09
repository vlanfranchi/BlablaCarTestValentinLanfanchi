package com.jehutyno.blablacartestvalentinlanfranchi

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.jehutyno.api.API
import com.jehutyno.api.Trips
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class DataViewModel(application: Application) : AndroidViewModel(application), CoroutineScope by MainScope() {

    private var plop by Delegates.observable<Trips?>(null) { _, oldValue, newValue ->
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
        launch {
            val trips = withContext(Dispatchers.IO) {
                API().getTrips()
            }
            plop = trips
        }
    }
}

interface TripsListener {
    fun tripsChanged(trips: Trips?)
}