package com.husseinelfeky.tomasulosimulator.model.simulation.base

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

abstract class Buffer(
    override val tag: Tag,
    open val address: Double?,
    override val isBusy: Boolean,
    override val remainingCycles: Int?
) : RowItem(tag, isBusy, remainingCycles)
