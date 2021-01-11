package com.husseinelfeky.tomasulosimulator.ui.instructions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.instruction.ITypeInstruction
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.instruction.RTypeInstruction
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItemDiffUtil

class InstructionsAdapter(
    private val onInstructionUpdate: (Instruction) -> Unit
) : ListAdapter<Instruction, RecyclerView.ViewHolder>(DifferentiableItemDiffUtil.getInstance()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_instruction_type_r -> RTypeInstructionViewHolder.create(parent)
            R.layout.item_instruction_type_i -> ITypeInstructionViewHolder.create(parent)
            else -> throw IllegalArgumentException("View type $viewType is unsupported.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_instruction_type_r -> (holder as RTypeInstructionViewHolder).bind(
                getItem(position) as RTypeInstruction,
                onInstructionUpdate
            )
            R.layout.item_instruction_type_i -> (holder as ITypeInstructionViewHolder).bind(
                getItem(position) as ITypeInstruction,
                onInstructionUpdate
            )
            else -> throw IllegalArgumentException("View type at position $position is unsupported.")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RTypeInstruction -> R.layout.item_instruction_type_r
            is ITypeInstruction -> R.layout.item_instruction_type_i
            else -> throw IllegalArgumentException("Instruction at position $position is unsupported.")
        }
    }
}
