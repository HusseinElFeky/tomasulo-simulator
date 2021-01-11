package com.husseinelfeky.tomasulosimulator.ui.simulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.simulation.LoadBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.Register
import com.husseinelfeky.tomasulosimulator.model.simulation.ReservationStation
import com.husseinelfeky.tomasulosimulator.model.simulation.StoreBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus

class SimulationViewModel : ViewModel() {

    private val _cycle = MutableLiveData(0)
    val cycle: LiveData<Int>
        get() = _cycle

    private val _instructionsStatus = MutableLiveData<List<InstructionStatus>>()
    val instructionsStatus: LiveData<List<InstructionStatus>>
        get() = _instructionsStatus

    private val _registers = MutableLiveData(Register.getAll())
    val registers: LiveData<List<Register>>
        get() = _registers

    private val _addStations = MutableLiveData(ReservationStation.getAddStations())
    val addStations: LiveData<List<ReservationStation>>
        get() = _addStations

    private val _mulStations = MutableLiveData(ReservationStation.getMulStations())
    val mulStations: LiveData<List<ReservationStation>>
        get() = _mulStations

    private val _loadBuffers = MutableLiveData(LoadBuffer.getAll())
    val loadBuffers: LiveData<List<LoadBuffer>>
        get() = _loadBuffers

    private val _storeBuffers = MutableLiveData(StoreBuffer.getAll())
    val storeBuffers: LiveData<List<StoreBuffer>>
        get() = _storeBuffers

    fun initInstructions(instructionsArray: Array<Instruction>) {
        _instructionsStatus.value = instructionsArray.map {
            it.toInstructionStatus()
        }
    }

    fun goToNextCycle() {
        // TODO
    }
}
