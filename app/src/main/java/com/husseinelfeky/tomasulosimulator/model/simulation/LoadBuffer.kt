package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.simulation.base.Buffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class LoadBuffer(
    override val tag: Tag,
    override val address: Double,
    override val isBusy: Boolean,
    override val remainingCycles: Int
) : Buffer(tag, address, isBusy, remainingCycles)
