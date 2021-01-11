package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class StoreBuffer(
    override val tag: Tag,
    override val address: Double? = null,
    val v: Int? = null,
    val q: Tag? = null,
    override val isBusy: Boolean = false,
    override val remainingCycles: Int? = null
) : Buffer(tag, address, isBusy, remainingCycles) {

    companion object {
        private const val BUFFERS_STORE = 3

        fun getAll(): List<StoreBuffer> {
            return (1..BUFFERS_STORE).toList().map {
                StoreBuffer(Tag(BaseOperation.S, it))
            }
        }
    }
}
