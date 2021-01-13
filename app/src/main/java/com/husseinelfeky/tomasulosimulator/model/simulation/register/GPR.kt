package com.husseinelfeky.tomasulosimulator.model.simulation.register

import com.husseinelfeky.tomasulosimulator.model.simulation.base.Register
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class GPR(
    override val number: Int,
    override var tag: Tag? = null,
    override var content: Int = 0
) : Register(number, tag, content) {

    override val name: String
        get() = getName(number)

    companion object {
        private const val GPR_COUNT = 16

        fun getName(number: Int): String {
            return "R$number"
        }

        fun getAllNames(): List<String> {
            return (0 until GPR_COUNT).toList().map { getName(it) }
        }

        fun getAll(): List<GPR> {
            return (0 until GPR_COUNT).toList()
                .map { number ->
                    GPR(
                        number = number,
                        content = number
                    )
                }
        }
    }
}
