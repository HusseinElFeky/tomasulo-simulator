package com.husseinelfeky.tomasulosimulator.model.simulation.general

import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction

data class InstructionStatus(
    val instruction: Instruction,
    val issued: Int? = null,
    val executed: Int? = null,
    val computed: Int? = null,
    val written: Int? = null
)
