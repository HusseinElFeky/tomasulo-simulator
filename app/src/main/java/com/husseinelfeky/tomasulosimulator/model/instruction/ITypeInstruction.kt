package com.husseinelfeky.tomasulosimulator.model.instruction

import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR
import com.husseinelfeky.tomasulosimulator.model.simulation.register.GPR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ITypeInstruction(
    override val number: Int,
    override var operation: Operation? = null,
    override var rs: Int? = null,
    override var rt: Int? = null,
    var offset: Int? = null
) : Instruction(number, operation, rs, rt) {

    val address: Address
        get() = if (offset != null && rs != null) {
            Address(offset!!, rs!!)
        } else {
            throw UninitializedPropertyAccessException("Address is undefined.")
        }

    override fun isValid(): Boolean {
        return operation != null && rs != null && rt != null && offset != null
    }

    override fun toFormattedString(): String {
        return "${operation!!.exactName} ${FPR.getName(rt!!)}, $offset(${GPR.getName(rs!!)})"
    }

    fun toRTypeInstruction(operation: Operation): RTypeInstruction {
        return RTypeInstruction(number, operation)
    }
}
