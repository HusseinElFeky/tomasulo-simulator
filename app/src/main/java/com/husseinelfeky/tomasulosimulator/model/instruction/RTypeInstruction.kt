package com.husseinelfeky.tomasulosimulator.model.instruction

import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR.Companion.getName
import kotlinx.android.parcel.Parcelize

@Parcelize
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

    override fun toFormattedString(): String {
        return "${operation!!.exactName} ${getName(rd!!)}, ${getName(rs!!)}, ${getName(rt!!)}"
    }

    fun toITypeInstruction(operation: Operation): ITypeInstruction {
        return ITypeInstruction(number, operation)
    }
}
