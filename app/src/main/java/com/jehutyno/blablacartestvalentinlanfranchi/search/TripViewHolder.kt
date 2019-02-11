package com.jehutyno.blablacartestvalentinlanfranchi.search

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.trip_list_item.view.*

class TripViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val departureTime = view.departureTime
    val locations = view.locations
    val price = view.price

}