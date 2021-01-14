package com.husseinelfeky.tomasulosimulator.model.simulation.base

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

abstract class SimulationItem(
    open val tag: Tag,
    open val isBusy: Boolean,
    open val instructionNumber: Int?,
    open val remainingCycles: Int?,
    open val showValues: Boolean
) : DifferentiableItem {

    val assignedRegister: Int?
        get() = tag.assignedRegister

    abstract fun canExecute(): Boolean

    abstract fun clear()

    override fun getUniqueIdentifier(): Any = tag.id

    override fun getContent(): String = toString()

    companion object {
        fun List<SimulationItem>.indexOfNextEmpty(): Int {
            return indexOfFirst { !it.isBusy }
        }
    }
}
