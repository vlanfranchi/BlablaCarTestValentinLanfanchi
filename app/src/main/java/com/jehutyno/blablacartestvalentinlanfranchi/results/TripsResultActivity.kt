package com.jehutyno.blablacartestvalentinlanfranchi.results

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jehutyno.blablacartestvalentinlanfranchi.R
import com.jehutyno.blablacartestvalentinlanfranchi.search.TripItem
import com.jehutyno.blablacartestvalentinlanfranchi.search.TripsAdapter
import kotlinx.android.synthetic.main.trips_activity.*

class TripsResultActivity: AppCompatActivity(), TripsListener {

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

    private val tripsAdapter by lazy { TripsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trips_activity)

        val departure = intent.getStringExtra(INTENT_DEPARTURE)
        val destination = intent.getStringExtra(INTENT_DESTINATION)

        titleTv.text = getString(R.string.trips_title, departure, destination)
        tripList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tripsAdapter
        }

        progress.visibility = VISIBLE
        val viewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
        viewModel.getTrips(departure, destination)
        viewModel.listeners += this
    }

    override fun tripsChanged(trips: List<TripItem>?) {
        progress.visibility = GONE
        tripsAdapter.update(trips)
    }

    override fun onError(message: String?) {
        progress.visibility = GONE
        messageTv.text = message
    }

}