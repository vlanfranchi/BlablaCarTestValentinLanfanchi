package com.jehutyno.blablacartestvalentinlanfranchi

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jehutyno.api.Trips


class MainActivity: AppCompatActivity(), TripsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
        viewModel.getTrips()
        viewModel.listeners += this
    }

    override fun tripsChanged(trips: Trips?) {
        println("pliônio")
    }
}