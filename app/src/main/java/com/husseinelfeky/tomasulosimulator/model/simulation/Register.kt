package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

data class Register(
    val number: Int,
    val tag: Tag? = null
) : DifferentiableItem {

    val name = "R$number"

    override fun getUniqueIdentifier(): Any = number

    override fun getContent(): String = toString()

    companion object {
        private const val REGISTER_COUNT = 16

        fun getName(number: Int): String {
            return "R$number"
        }

        fun getAllNames(): List<String> {
            return (0 until REGISTER_COUNT).toList().map { getName(it) }
        }

        fun getAll(): List<Register> {
            return (0 until REGISTER_COUNT).toList().map { Register(it) }
        }
    }
}
