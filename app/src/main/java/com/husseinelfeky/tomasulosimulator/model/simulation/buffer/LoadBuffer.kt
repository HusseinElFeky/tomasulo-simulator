package com.husseinelfeky.tomasulosimulator.model.simulation.buffer

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class LoadBuffer(
    override val tag: Tag,
    override var address: Address? = null,
    override var isBusy: Boolean = false,
    override var instructionNumber: Int? = null,
    override var remainingCycles: Int? = null
) : Buffer(tag, address, isBusy, instructionNumber, remainingCycles) {

    override fun canExecute(): Boolean {
        return isBusy && remainingCycles != 0
    }

    override fun clear() {
        tag.assignedRegister = null
        address = null
        isBusy = false
        instructionNumber = null
        remainingCycles = null
    }

    companion object {
        private const val BUFFERS_LOAD = 3

        fun getAll(): List<LoadBuffer> {
            return (1..BUFFERS_LOAD).toList().map {
                LoadBuffer(Tag(BaseOperation.L, it))
            }
        }
    }
}
