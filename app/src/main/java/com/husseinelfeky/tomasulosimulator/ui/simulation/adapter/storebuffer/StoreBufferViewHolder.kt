package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.storebuffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.StoreBuffer

class StoreBufferViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(storeBuffer: StoreBuffer) {
        // TODO
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
