package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.instructionstatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus

class InstructionStatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(instructionStatus: InstructionStatus) {
        // TODO
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
