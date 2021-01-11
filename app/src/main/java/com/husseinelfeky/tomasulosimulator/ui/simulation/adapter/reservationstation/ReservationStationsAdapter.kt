package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.reservationstation

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.tomasulosimulator.model.simulation.ReservationStation
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItemDiffUtil

class ReservationStationsAdapter : ListAdapter<ReservationStation, ReservationStationViewHolder>(
    DifferentiableItemDiffUtil.getInstance()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationStationViewHolder {
        return ReservationStationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ReservationStationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
