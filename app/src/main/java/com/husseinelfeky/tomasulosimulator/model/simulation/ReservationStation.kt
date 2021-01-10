package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.RowItem
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class ReservationStation(
    override val tag: Tag,
    val operation: Operation,
    val vj: Int,
    val vk: Int,
    val qj: Tag,
    val qk: Tag,
    override val isBusy: Boolean,
    override val remainingCycles: Int
) : RowItem(tag, isBusy, remainingCycles)
