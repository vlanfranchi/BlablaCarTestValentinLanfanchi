package com.jehutyno.blablacartestvalentinlanfranchi.results

import com.jehutyno.api.model.Trip
import com.jehutyno.blablacartestvalentinlanfranchi.Converter
import com.jehutyno.blablacartestvalentinlanfranchi.search.TripItem

object TripConverter : Converter<Trip, TripItem> {

    override fun convert(input: Trip): TripItem {
       return TripItem(
           departureTime = input.departureDate,
           locations = input.locationsToDisplay,
           price = input.price.string
       )
    }
}