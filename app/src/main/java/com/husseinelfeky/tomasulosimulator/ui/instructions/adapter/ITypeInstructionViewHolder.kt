package com.husseinelfeky.tomasulosimulator.ui.instructions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.instruction.ITypeInstruction
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.instruction.InstructionType
import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR
import com.husseinelfeky.tomasulosimulator.model.simulation.register.GPR
import kotlinx.android.synthetic.main.item_instruction_type_i.view.*

class ITypeInstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(instruction: ITypeInstruction, onInstructionUpdate: (Instruction) -> Unit) {
        with(itemView) {
            til_operation.hint = "op ${instruction.number}"

            instruction.operation?.let {
                tv_operation.setText(it.exactName)
            }

            instruction.rt?.let {
                tv_rt.setText(FPR.getName(it))
            }

            instruction.offset?.let {
                tv_offset.setText(it.toString())
            }

            instruction.rs?.let {
                tv_rs.setText(GPR.getName(it))
            }

            val operations = Operation.values()
            tv_operation.setAdapter(
                ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    operations.map { it.exactName }
                )
            )
            tv_operation.setOnItemClickListener { _, _, position, _ ->
                val selectedOperation = operations[position]

                if (selectedOperation.instructionType == InstructionType.TYPE_R) {
                    onInstructionUpdate(instruction.toRTypeInstruction(selectedOperation))
                } else {
                    instruction.operation = selectedOperation
                    onInstructionUpdate(instruction)
                }
            }

            tv_rt.setAdapter(
                ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    FPR.getAllNames()
                )
            )
            tv_rs.setAdapter(
                ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    GPR.getAllNames()
                )
            )

            tv_rt.setOnItemClickListener { _, _, position, _ ->
                instruction.rt = position
                onInstructionUpdate(instruction)
            }

            tv_rs.setOnItemClickListener { _, _, position, _ ->
                instruction.rs = position
                onInstructionUpdate(instruction)
            }

            tv_offset.addTextChangedListener {
                instruction.offset = it?.toString()?.toIntOrNull()
                onInstructionUpdate(instruction)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ITypeInstructionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_instruction_type_i,
                    parent,
                    false
                )
            return ITypeInstructionViewHolder(view)
        }
    }
}
