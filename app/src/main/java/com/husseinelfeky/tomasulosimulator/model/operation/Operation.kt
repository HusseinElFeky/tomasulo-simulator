package com.husseinelfeky.tomasulosimulator.model.operation

import com.husseinelfeky.tomasulosimulator.model.instruction.InstructionType

enum class Operation(val baseOperation: BaseOperation, val exactName: String) {

    ADD(BaseOperation.A, "ADD"),
    SUB(BaseOperation.A, "SUB"),
    MUL(BaseOperation.M, "MUL"),
    DIV(BaseOperation.M, "DIV"),
    LD(BaseOperation.L, "L.D"),
    SD(BaseOperation.S, "S.D");

    val instructionType: InstructionType
        get() = baseOperation.instructionType
}
