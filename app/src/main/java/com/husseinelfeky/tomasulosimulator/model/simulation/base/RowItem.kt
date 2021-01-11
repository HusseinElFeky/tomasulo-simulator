package com.husseinelfeky.tomasulosimulator.model.simulation.base

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

abstract class RowItem(
    open val tag: Tag,
    open val isBusy: Boolean,
    open val remainingCycles: Int?
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = tag.id

    override fun getContent(): String = toString()
}
