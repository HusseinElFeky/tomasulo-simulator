package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.loadbuffer

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.tomasulosimulator.model.simulation.buffer.LoadBuffer
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItemDiffUtil

class LoadBuffersAdapter : ListAdapter<LoadBuffer, LoadBufferViewHolder>(
    DifferentiableItemDiffUtil.getInstance()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadBufferViewHolder {
        return LoadBufferViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LoadBufferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
