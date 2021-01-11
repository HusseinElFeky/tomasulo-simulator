package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.instructionstatus

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItemDiffUtil

class InstructionsStatusAdapter : ListAdapter<InstructionStatus, InstructionStatusViewHolder>(
    DifferentiableItemDiffUtil.getInstance()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionStatusViewHolder {
        return InstructionStatusViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: InstructionStatusViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
