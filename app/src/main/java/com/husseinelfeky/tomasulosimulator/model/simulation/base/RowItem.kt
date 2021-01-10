package com.husseinelfeky.tomasulosimulator.model.simulation.base

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

abstract class RowItem(
    open val tag: Tag,
    open val isBusy: Boolean,
    open val remainingCycles: Int
)
