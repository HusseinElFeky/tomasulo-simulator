package com.husseinelfeky.tomasulosimulator.model.simulation.base

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

abstract class Register(
    open val number: Int,
    open val tag: Tag?,
    open val content: Number
) : DifferentiableItem {

    abstract val name: String

    override fun getUniqueIdentifier(): Any = number

    override fun getContent(): String = toString()
}
