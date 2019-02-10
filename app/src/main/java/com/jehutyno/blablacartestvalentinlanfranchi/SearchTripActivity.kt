package com.jehutyno.blablacartestvalentinlanfranchi

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jehutyno.api.Trips
import kotlinx.android.synthetic.main.search_activity.*


class SearchTripActivity: AppCompatActivity(), TripsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        val viewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
        viewModel.getTrips()
        viewModel.listeners += this
    }

    override fun tripsChanged(trips: Trips?) {
        println(trips)
    }

    override fun ioError(message: String?) {
        messageTv.text = message
    }

    override fun unknownError(message: String?) {
        messageTv.text = message
    }
}