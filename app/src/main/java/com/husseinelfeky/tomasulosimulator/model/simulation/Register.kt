package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItem

data class Register(
    val number: Int,
    var tag: Tag? = null
) : DifferentiableItem {

    val rName: String
        get() = getRName(number)

    val fName: String
        get() = getFName(number)

    override fun getUniqueIdentifier(): Any = number

    override fun getContent(): String = toString()

    companion object {
        private const val REGISTER_COUNT = 16

        fun getRName(number: Int): String {
            return "R$number"
        }

        fun getFName(number: Int): String {
            return "F$number"
        }

        fun getAllRNames(): List<String> {
            return (0 until REGISTER_COUNT).toList().map { getRName(it) }
        }

        fun getAllFNames(): List<String> {
            return (0 until REGISTER_COUNT).toList().map { getFName(it) }
        }

        fun getAll(): List<Register> {
            return (0 until REGISTER_COUNT).toList().map { Register(it) }
        }
    }
}
