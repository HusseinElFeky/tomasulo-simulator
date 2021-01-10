package com.husseinelfeky.tomasulosimulator.model.instruction

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

abstract class Instruction(
    open val number: Int,
    open val operation: Operation,
    open val rs: Int,
    open val rt: Int
) : DifferentiableItem {

    val baseOperation: BaseOperation
        get() = when (operation) {
            Operation.ADD, Operation.SUB -> BaseOperation.A
            Operation.MUL, Operation.DIV -> BaseOperation.M
            Operation.LD -> BaseOperation.L
            Operation.SD -> BaseOperation.S
        }

    override fun getUniqueIdentifier(): Any = number

    override fun getContent(): String = toString()
}
