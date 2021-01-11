package com.husseinelfeky.tomasulosimulator.model.simulation.general

import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

data class InstructionStatus(
    val instruction: Instruction,
    val issued: Int? = null,
    val executed: Int? = null,
    val computed: Int? = null,
    val written: Int? = null
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = instruction.number

    override fun getContent(): String = toString()
}
