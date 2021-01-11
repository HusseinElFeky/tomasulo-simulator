package com.husseinelfeky.tomasulosimulator.model.instruction

import android.os.Parcelable
import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

abstract class Instruction(
    open val number: Int,
    open val operation: Operation?,
    open val rs: Int?,
    open val rt: Int?
) : Parcelable, DifferentiableItem {

    val baseOperation: BaseOperation
        get() = operation?.baseOperation ?: throw UninitializedPropertyAccessException(
            "Instruction operation is not initialized yet."
        )

    val cyclesNeeded: Int
        get() = operation?.cycles ?: throw UninitializedPropertyAccessException(
            "Instruction operation is not initialized yet."
        )

    abstract fun isValid(): Boolean

    override fun getUniqueIdentifier(): Any = number

    override fun getContent(): String = toString()

    fun toInstructionStatus(): InstructionStatus {
        return InstructionStatus(this)
    }
}
