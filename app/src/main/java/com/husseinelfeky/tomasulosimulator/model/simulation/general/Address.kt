package com.husseinelfeky.tomasulosimulator.model.simulation.general

data class Address(
    val offset: Int,
    val base: Int
) {

    companion object {
        fun Address.toFormattedString(): String {
            return "$offset + R$base"
        }
    }
}
