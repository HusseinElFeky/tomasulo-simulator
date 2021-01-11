package com.husseinelfeky.tomasulosimulator.model.instruction

import com.husseinelfeky.tomasulosimulator.model.operation.Operation

data class RTypeInstruction(
    override val number: Int,
    override var operation: Operation? = null,
    override var rs: Int? = null,
    override var rt: Int? = null,
    var rd: Int? = null
) : Instruction(number, operation, rs, rt) {

    override fun isValid(): Boolean {
        return operation != null && rs != null && rt != null && rd != null
    }

    fun toITypeInstruction(operation: Operation): ITypeInstruction {
        return ITypeInstruction(number, operation)
    }
}
