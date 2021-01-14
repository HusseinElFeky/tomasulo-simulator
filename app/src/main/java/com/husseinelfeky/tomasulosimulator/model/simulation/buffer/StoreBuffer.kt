package com.husseinelfeky.tomasulosimulator.model.simulation.buffer

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.model.simulation.general.ValueReference

data class StoreBuffer(
    override val tag: Tag,
    override var address: Address? = null,
    var v: ValueReference? = null,
    var q: Tag? = null,
    override var isBusy: Boolean = false,
    override var instructionNumber: Int? = null,
    override var remainingCycles: Int? = null,
    override var showValues: Boolean = false
) : Buffer(tag, address, isBusy, instructionNumber, remainingCycles, showValues) {

    override fun canExecute(): Boolean {
        return isBusy && remainingCycles != 0 && v != null
    }

    override fun clear() {
        tag.assignedRegister = null
        address = null
        v = null
        q = null
        isBusy = false
        instructionNumber = null
        remainingCycles = null
    }

    companion object {
        private const val BUFFERS_STORE = 3

        fun getAll(): List<StoreBuffer> {
            return (1..BUFFERS_STORE).toList().map {
                StoreBuffer(Tag(BaseOperation.S, it))
            }
        }
    }
}
