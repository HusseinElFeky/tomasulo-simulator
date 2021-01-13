package com.husseinelfeky.tomasulosimulator.model.operation

import com.husseinelfeky.tomasulosimulator.model.instruction.InstructionType

enum class Operation(val baseOperation: BaseOperation, val exactName: String, val cycles: Int) {

    ADD(BaseOperation.A, "ADD.D", 3),
    SUB(BaseOperation.A, "SUB.D", 3),
    MUL(BaseOperation.M, "MUL.D", 6),
    DIV(BaseOperation.M, "DIV.D", 8),
    LD(BaseOperation.L, "L.D", 2),
    SD(BaseOperation.S, "S.D", 2);

    val instructionType: InstructionType
        get() = baseOperation.instructionType
}
