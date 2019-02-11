package com.jehutyno.blablacartestvalentinlanfranchi.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jehutyno.blablacartestvalentinlanfranchi.R
import com.jehutyno.blablacartestvalentinlanfranchi.view.CircleTransform
import com.squareup.picasso.Picasso

class TripsAdapter(private val context: Context): RecyclerView.Adapter<TripViewHolder>() {

    private var items: List<TripItem> = listOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(LayoutInflater.from(context).inflate(R.layout.trip_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.departureTime.text = items[position].departureTime
        holder.locations.text = items[position].locations.joinToString(" - ")
        holder.price.text = items[position].price
        holder.userName.text = items[position].userName
        Picasso.get().load(items[position].userPictureUrl).transform(CircleTransform()).into(holder.userPicture)
    }

    fun update(items: List<TripItem>?) {
        items?.let {
            this.items = items
            notifyDataSetChanged()
        }
    }

}
