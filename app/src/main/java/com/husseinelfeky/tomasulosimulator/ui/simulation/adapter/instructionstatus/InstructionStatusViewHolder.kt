package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.instructionstatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus
import kotlinx.android.synthetic.main.item_instruction_status.view.*

class InstructionStatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(instructionStatus: InstructionStatus) {
        with(itemView) {
            tv_number.text = instructionStatus.instruction.number.toString()
            tv_instruction.text = instructionStatus.instruction.toFormattedString()
            tv_issued.text = instructionStatus.issued?.toString() ?: "-"
            tv_executed.text = instructionStatus.executed?.toString() ?: "-"
            tv_computed.text = instructionStatus.computed?.toString() ?: "-"
            tv_written.text = instructionStatus.written?.toString() ?: "-"
        }
    }

    companion object {
        fun create(parent: ViewGroup): InstructionStatusViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_instruction_status,
                    parent,
                    false
                )
            return InstructionStatusViewHolder(view)
        }
    }
}
