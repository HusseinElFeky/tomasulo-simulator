package com.husseinelfeky.tomasulosimulator.ui.instructions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.instruction.RTypeInstruction

class InstructionsViewModel : ViewModel() {

    private val _instructions = MutableLiveData<MutableList<Instruction>>(mutableListOf())
    val instructions: LiveData<MutableList<Instruction>>
        get() = _instructions

    fun addNewInstruction() {
        _instructions.value = _instructions.value?.apply {
            add(RTypeInstruction(size + 1))
        }
    }

    fun updateInstruction(instruction: Instruction) {
        _instructions.value = _instructions.value?.apply {
            removeAt(instruction.number - 1)
            add(instruction.number - 1, instruction)
        }
    }

    fun clearInstructions() {
        _instructions.value = mutableListOf()
    }

    fun areInstructionsValid(): Boolean {
        val instructions = _instructions.value

        if (instructions.isNullOrEmpty()) {
            return false
        }

        instructions.forEach {
            if (!it.isValid()) return false
        }

        return true
    }
}
