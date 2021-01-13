package com.husseinelfeky.tomasulosimulator.model.simulation.general

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation

data class Tag(
    val baseOperation: BaseOperation,
    val id: Int,
    var assignedRegister: Int? = null
) {

    val name: String
        get() = baseOperation.name + id
}
