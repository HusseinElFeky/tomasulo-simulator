package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.loadbuffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.LoadBuffer

class LoadBufferViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(loadBuffer: LoadBuffer) {
        // TODO
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
