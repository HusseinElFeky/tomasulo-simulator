package com.husseinelfeky.tomasulosimulator.model.operation

import com.husseinelfeky.tomasulosimulator.model.instruction.InstructionType

enum class Operation(val baseOperation: BaseOperation, val exactName: String, val cycles: Int) {

    ADD(BaseOperation.A, "ADD", 3),
    SUB(BaseOperation.A, "SUB", 3),
    MUL(BaseOperation.M, "MUL", 4),
    DIV(BaseOperation.M, "DIV", 6),
    LD(BaseOperation.L, "L.D", 2),
    SD(BaseOperation.S, "S.D", 2);

    val instructionType: InstructionType
        get() = baseOperation.instructionType
}
