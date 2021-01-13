package com.husseinelfeky.tomasulosimulator.model.simulation.general

import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

data class InstructionStatus(
    val instruction: Instruction,
    var issued: Int? = null,
    var executed: Int? = null,
    var computed: Int? = null,
    var written: Int? = null
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = instruction.number

    override fun getContent(): String = toString()

    companion object {
        fun List<InstructionStatus>.indexOfNextInstructionStatus(): Int {
            return indexOfFirst { it.issued == null }
        }
    }
}
