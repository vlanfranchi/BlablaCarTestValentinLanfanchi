package com.jehutyno.blablacartestvalentinlanfranchi

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jehutyno.api.Trips
import kotlinx.android.synthetic.main.search_activity.*

class TripsResultActivity: AppCompatActivity(), TripsListener  {

    companion object {
        private val INTENT_DEPARTURE = "intent_departure"
        private val INTENT_DESTINATION = "intent_destination"

        fun intent(context: Context, departure: String, destination: String): Intent {
            return Intent(context, TripsResultActivity::class.java).apply {
                putExtra(INTENT_DEPARTURE, departure)
                putExtra(INTENT_DESTINATION, destination)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
        viewModel.getTrips()
        viewModel.listeners += this
    }

    override fun tripsChanged(trips: Trips?) {
        println(trips)
    }

    override fun onError(message: String?) {
        messageTv.text = message
    }

}