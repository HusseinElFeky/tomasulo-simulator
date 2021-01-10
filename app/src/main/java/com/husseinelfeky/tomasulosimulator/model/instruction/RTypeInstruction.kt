package com.husseinelfeky.tomasulosimulator.model.instruction

import com.husseinelfeky.tomasulosimulator.model.operation.Operation

data class RTypeInstruction(
    override val number: Int,
    override val operation: Operation,
    override val rs: Int,
    override val rt: Int,
    val rd: Int
) : Instruction(number, operation, rs, rt)
