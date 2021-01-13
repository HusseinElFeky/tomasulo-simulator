package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class StoreBuffer(
    override val tag: Tag,
    override var address: Int? = null,
    var v: Int? = null,
    var q: Tag? = null,
    override var isBusy: Boolean = false,
    override var remainingCycles: Int? = null
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
