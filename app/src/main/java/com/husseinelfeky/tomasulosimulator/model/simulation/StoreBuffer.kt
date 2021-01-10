package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class StoreBuffer(
    override val tag: Tag,
    override val address: Double,
    val v: String,
    val q: String,
    override val isBusy: Boolean,
    override val remainingCycles: Int
) : Buffer(tag, address, isBusy, remainingCycles)
