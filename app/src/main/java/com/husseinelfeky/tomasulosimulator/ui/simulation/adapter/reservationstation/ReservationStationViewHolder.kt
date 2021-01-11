package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.reservationstation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.ReservationStation

class ReservationStationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(reservationStation: ReservationStation) {
        // TODO
    }

    companion object {
        fun create(parent: ViewGroup): ReservationStationViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_reservation_station,
                    parent,
                    false
                )
            return ReservationStationViewHolder(view)
        }
    }
}
