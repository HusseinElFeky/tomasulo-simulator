package com.husseinelfeky.tomasulosimulator.model.simulation.general

import com.husseinelfeky.tomasulosimulator.model.simulation.Register

data class Address(
    val offset: Int,
    val base: Int
) {

    companion object {
        fun Address.toFormattedString(): String {
            return "$offset + ${Register.getRName(base)}"
        }
    }
}
