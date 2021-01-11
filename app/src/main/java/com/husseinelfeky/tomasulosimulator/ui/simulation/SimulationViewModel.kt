package com.husseinelfeky.tomasulosimulator.ui.simulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus

class SimulationViewModel : ViewModel() {

    private val _instructionsStatus = MutableLiveData<List<InstructionStatus>>()
    val instructionsStatus: LiveData<List<InstructionStatus>>
        get() = _instructionsStatus

    fun initInstructions(instructionsArray: Array<Instruction>) {
        _instructionsStatus.value = instructionsArray.map {
            it.toInstructionStatus()
        }
    }

    fun goToNextCycle() {
        // TODO
    }
}
