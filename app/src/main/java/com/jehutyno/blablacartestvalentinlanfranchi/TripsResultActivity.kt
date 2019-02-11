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
        private const val INTENT_DEPARTURE = "intent_departure"
        private const val INTENT_DESTINATION = "intent_destination"

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
        viewModel.getTrips(intent.getStringExtra(INTENT_DEPARTURE), intent.getStringExtra(INTENT_DESTINATION))
        viewModel.listeners += this
    }

    override fun tripsChanged(trips: Trips?) {
        println(trips)
    }

    override fun onError(message: String?) {
        messageTv.text = message
    }

}