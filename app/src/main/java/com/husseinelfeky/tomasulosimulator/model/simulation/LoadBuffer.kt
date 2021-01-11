package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class LoadBuffer(
    override val tag: Tag,
    override val address: Double? = null,
    override val isBusy: Boolean = false,
    override val remainingCycles: Int? = null
) : Buffer(tag, address, isBusy, remainingCycles) {

    companion object {
        private const val BUFFERS_LOAD = 3

        fun getAll(): List<LoadBuffer> {
            return (1..BUFFERS_LOAD).toList().map {
                LoadBuffer(Tag(BaseOperation.L, it))
            }
        }
    }
}
