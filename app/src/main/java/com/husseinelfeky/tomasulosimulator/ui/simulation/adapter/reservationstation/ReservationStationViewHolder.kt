package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.reservationstation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.ReservationStation
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR
import kotlinx.android.synthetic.main.item_reservation_station.view.*

class ReservationStationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(reservationStation: ReservationStation) {
        with(itemView) {
            tv_remaining_cycles.text = reservationStation.remainingCycles?.toString() ?: "-"
            tv_tag.text = reservationStation.tag.name
            tv_operation.text = reservationStation.operation?.toString() ?: "-"
            tv_vj.text = if (reservationStation.vj != null) {
                if (reservationStation.showValues) {
                    reservationStation.vj!!.value.toString()
                } else {
                    "R[${FPR.getName(reservationStation.vj!!.registerReference)}]"
                }
            } else {
                "-"
            }
            tv_vk.text = if (reservationStation.vk != null) {
                if (reservationStation.showValues) {
                    reservationStation.vk!!.value.toString()
                } else {
                    "R[${FPR.getName(reservationStation.vk!!.registerReference)}]"
                }
            } else {
                "-"
            }
            tv_qj.text = reservationStation.qj?.name ?: "-"
            tv_qk.text = reservationStation.qk?.name ?: "-"
            tv_busy.text = if (reservationStation.isBusy) "Y" else "N"
        }
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
