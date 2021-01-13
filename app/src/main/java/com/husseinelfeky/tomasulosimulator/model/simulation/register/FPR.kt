package com.husseinelfeky.tomasulosimulator.model.simulation.register

import com.husseinelfeky.tomasulosimulator.model.simulation.base.Register
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag
import com.husseinelfeky.tomasulosimulator.utils.roundTo
import kotlin.random.Random

data class FPR(
    override val number: Int,
    override var tag: Tag? = null,
    override var content: Double = 0.0
) : Register(number, tag, content) {

    override val name: String
        get() = getName(number)

    companion object {
        private const val FPR_COUNT = 16

        fun getName(number: Int): String {
            return "F$number"
        }

        fun getAllNames(): List<String> {
            return (0 until FPR_COUNT).toList().map { getName(it) }
        }

        fun getAll(): List<FPR> {
            return (0 until FPR_COUNT).toList()
                .map { number ->
                    FPR(
                        number = number,
                        content = Random.nextDouble(100.0).roundTo(2)
                    )
                }
        }
    }
}
