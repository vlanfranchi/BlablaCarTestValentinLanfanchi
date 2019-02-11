package com.jehutyno.blablacartestvalentinlanfranchi.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.jehutyno.blablacartestvalentinlanfranchi.R
import com.jehutyno.blablacartestvalentinlanfranchi.results.TripsResultActivity
import kotlinx.android.synthetic.main.search_activity.*


class SearchTripActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        search.setOnClickListener {
            if (departure.text.isNotBlank() && destination.text.isNotBlank()) {
                startActivity(
                    TripsResultActivity.intent(
                        this,
                        departure.text.toString(),
                        destination.text.toString()
                    )
                )
            } else {
                Snackbar.make(searchRoot, getString(R.string.error_search_empty), Snackbar.LENGTH_SHORT).show()
            }
        }

    }

}