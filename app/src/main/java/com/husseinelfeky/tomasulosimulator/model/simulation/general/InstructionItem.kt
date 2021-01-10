package com.husseinelfeky.tomasulosimulator.model.simulation.general

import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction

data class InstructionItem(
    val instruction: Instruction,
    val issued: Int,
    val executed: Int,
    val computed: Int,
    val written: Int
)
