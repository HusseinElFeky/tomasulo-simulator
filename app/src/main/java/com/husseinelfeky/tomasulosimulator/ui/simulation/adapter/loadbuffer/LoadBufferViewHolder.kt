package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.loadbuffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.LoadBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address.Companion.toFormattedString
import kotlinx.android.synthetic.main.item_buffer_load.view.*

class LoadBufferViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(loadBuffer: LoadBuffer) {
        with(itemView) {
            tv_remaining_cycles.text = loadBuffer.remainingCycles?.toString() ?: "-"
            tv_tag.text = loadBuffer.tag.name
            tv_address.text = loadBuffer.address?.toFormattedString() ?: "-"
            tv_busy.text = if (loadBuffer.isBusy) "Y" else "N"
        }
    }

    companion object {
        fun create(parent: ViewGroup): LoadBufferViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_buffer_load,
                    parent,
                    false
                )
            return LoadBufferViewHolder(view)
        }
    }
}
