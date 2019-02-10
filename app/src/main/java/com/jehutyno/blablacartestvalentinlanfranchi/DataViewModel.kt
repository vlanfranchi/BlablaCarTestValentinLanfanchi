package com.jehutyno.blablacartestvalentinlanfranchi

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.jehutyno.api.API
import com.jehutyno.api.Trips
import kotlinx.coroutines.*
import kotlinx.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class DataViewModel(application: Application) : AndroidViewModel(application), CoroutineScope by MainScope() {

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
        launch {
            try {
                val trips = withContext(Dispatchers.IO) {
                    API().getTrips()
                }
                tripsObservable = trips
            } catch (exception: IOException) {
                listeners.forEach {
                    withContext(Dispatchers.Main) {
                        it.ioError(exception.message)
                    }
                }
            } catch (exception: Exception) {
                listeners.forEach {
                    withContext(Dispatchers.Main) {
                        it.ioError(exception.message)
                    }
                }
            }
        }
    }
}

interface TripsListener {
    fun tripsChanged(trips: Trips?)
    fun ioError(message: String?)
    fun unknownError(message: String?)
}