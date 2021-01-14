package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.storebuffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.buffer.StoreBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address.Companion.toFormattedString
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address.Companion.toInt
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR
import kotlinx.android.synthetic.main.item_buffer_store.view.*

class StoreBufferViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(storeBuffer: StoreBuffer) {
        with(itemView) {
            tv_remaining_cycles.text = storeBuffer.remainingCycles?.toString() ?: "-"
            tv_tag.text = storeBuffer.tag.name
            tv_address.text = if (storeBuffer.address != null) {
                if (storeBuffer.showValues) {
                    storeBuffer.address!!.toInt().toString()
                } else {
                    storeBuffer.address!!.toFormattedString()
                }
            } else {
                "-"
            }
            tv_v.text = if (storeBuffer.v != null) {
                if (storeBuffer.showValues) {
                    storeBuffer.v!!.value.toString()
                } else {
                    "R[${FPR.getName(storeBuffer.v!!.registerReference)}]"
                }
            } else {
                "-"
            }
            tv_q.text = storeBuffer.q?.name ?: "-"
            tv_busy.text = if (storeBuffer.isBusy) "Y" else "N"
        }
    }

    companion object {
        fun create(parent: ViewGroup): StoreBufferViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_buffer_store,
                    parent,
                    false
                )
            return StoreBufferViewHolder(view)
        }
    }
}
