package com.husseinelfeky.tomasulosimulator.model.simulation.base

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

abstract class Buffer(
    override val tag: Tag,
    open val address: Int?,
    override val isBusy: Boolean,
    override val instructionNumber: Int?,
    override val remainingCycles: Int?
) : SimulationItem(tag, isBusy, instructionNumber, remainingCycles)
