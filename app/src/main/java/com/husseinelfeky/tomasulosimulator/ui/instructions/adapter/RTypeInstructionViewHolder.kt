package com.husseinelfeky.tomasulosimulator.ui.instructions.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.instruction.ITypeInstruction
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.instruction.InstructionType
import com.husseinelfeky.tomasulosimulator.model.instruction.RTypeInstruction
import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import kotlinx.android.synthetic.main.item_instruction_type_r.view.*

class RTypeInstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(instruction: RTypeInstruction, onInstructionUpdate: (Instruction) -> Unit) {
        with(itemView) {
            til_operation.hint = "op ${instruction.number}"

            instruction.operation?.let {
                tv_operation.setText(it.exactName)
            }

            instruction.rd?.let {
                tv_rd.setText("R$it")
            }

            instruction.rs?.let {
                tv_rs.setText("R$it")
            }

            instruction.rt?.let {
                tv_rt.setText("R$it")
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

                if (selectedOperation.instructionType == InstructionType.TYPE_I) {
                    onInstructionUpdate(ITypeInstruction(instruction.number, selectedOperation))
                } else {
                    instruction.operation = selectedOperation
                    onInstructionUpdate(instruction)
                }
            }

            val registers = (0..15).toList().map { "R$it" }
            tv_rd.setAdapter(
                ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, registers)
            )
            tv_rs.setAdapter(
                ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, registers)
            )
            tv_rt.setAdapter(
                ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, registers)
            )

            tv_rd.setOnItemClickListener { _, _, position, _ ->
                instruction.rd = position
                onInstructionUpdate(instruction)
            }

            tv_rs.setOnItemClickListener { _, _, position, _ ->
                instruction.rs = position
                onInstructionUpdate(instruction)
            }

            tv_rt.setOnItemClickListener { _, _, position, _ ->
                instruction.rt = position
                onInstructionUpdate(instruction)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RTypeInstructionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_instruction_type_r,
                    parent,
                    false
                )
            return RTypeInstructionViewHolder(view)
        }
    }
}
