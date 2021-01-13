package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.storebuffer

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.tomasulosimulator.model.simulation.buffer.StoreBuffer
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItemDiffUtil

class StoreBuffersAdapter : ListAdapter<StoreBuffer, StoreBufferViewHolder>(
    DifferentiableItemDiffUtil.getInstance()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreBufferViewHolder {
        return StoreBufferViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StoreBufferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
