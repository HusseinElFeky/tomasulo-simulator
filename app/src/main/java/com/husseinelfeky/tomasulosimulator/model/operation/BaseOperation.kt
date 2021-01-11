package com.husseinelfeky.tomasulosimulator.model.operation

import com.husseinelfeky.tomasulosimulator.model.instruction.InstructionType

enum class BaseOperation(val instructionType: InstructionType) {
    A(InstructionType.TYPE_R),
    M(InstructionType.TYPE_R),
    L(InstructionType.TYPE_I),
    S(InstructionType.TYPE_I)
}
