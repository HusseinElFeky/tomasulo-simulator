package com.husseinelfeky.tomasulosimulator.ui.simulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husseinelfeky.tomasulosimulator.model.instruction.ITypeInstruction
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.instruction.RTypeInstruction
import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.simulation.LoadBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.Register
import com.husseinelfeky.tomasulosimulator.model.simulation.ReservationStation
import com.husseinelfeky.tomasulosimulator.model.simulation.StoreBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.base.RowItem.Companion.indexOfNextEmpty
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus.Companion.indexOfNextInstructionStatus

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
        // Increment cycle by one.
        _cycle.value = _cycle.value!! + 1

        // Issue next instruction if available.
        val nextInstSIndex = _instructionsStatus.value!!.indexOfNextInstructionStatus()
        val nextInstS = if (nextInstSIndex != -1) {
            _instructionsStatus.value!![nextInstSIndex]
        } else {
            null
        }

        if (nextInstS != null) {
            val nextInst = nextInstS.instruction
            when (nextInst.operation!!.baseOperation) {
                BaseOperation.A -> {
                    nextInst as RTypeInstruction
                    val nextEmptyIndex = _addStations.value!!.indexOfNextEmpty()
                    if (nextEmptyIndex != -1) {
                        val rsTag = _registers.value!![nextInst.rs!!].tag
                        val rtTag = _registers.value!![nextInst.rt!!].tag
                        _addStations.value = _addStations.value!!.apply {
                            this[nextEmptyIndex].apply {
                                operation = nextInst.operation
                                if (rsTag == null) {
                                    vj = nextInst.rs
                                } else {
                                    qj = rsTag
                                }
                                if (rtTag == null) {
                                    vk = nextInst.rt
                                } else {
                                    qk = rtTag
                                }
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rd!!].tag = _addStations.value!![nextEmptyIndex].tag
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
                BaseOperation.M -> {
                    nextInst as RTypeInstruction
                    val nextEmptyIndex = _mulStations.value!!.indexOfNextEmpty()
                    if (nextEmptyIndex != -1) {
                        val rsTag = _registers.value!![nextInst.rs!!].tag
                        val rtTag = _registers.value!![nextInst.rt!!].tag
                        _mulStations.value = _mulStations.value!!.apply {
                            this[nextEmptyIndex].apply {
                                operation = nextInst.operation
                                if (rsTag == null) {
                                    vj = nextInst.rs
                                } else {
                                    qj = rsTag
                                }
                                if (rtTag == null) {
                                    vk = nextInst.rt
                                } else {
                                    qk = rtTag
                                }
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rd!!].tag = _mulStations.value!![nextEmptyIndex].tag
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
                BaseOperation.L -> {
                    nextInst as ITypeInstruction
                    val nextEmptyIndex = _loadBuffers.value!!.indexOfNextEmpty()
                    if (nextEmptyIndex != -1) {
                        _loadBuffers.value = _loadBuffers.value!!.apply {
                            this[nextEmptyIndex].apply {
                                address = nextInst.address
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rt!!].tag = _loadBuffers.value!![nextEmptyIndex].tag
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
                BaseOperation.S -> {
                    nextInst as ITypeInstruction
                    val nextEmptyIndex = _storeBuffers.value!!.indexOfNextEmpty()
                    if (nextEmptyIndex != -1) {
                        val qTag = _registers.value!![nextInst.rt!!].tag
                        _storeBuffers.value = _storeBuffers.value!!.apply {
                            this[nextEmptyIndex].apply {
                                address = nextInst.address
                                if (qTag == null) {
                                    v = nextInst.rt
                                } else {
                                    q = qTag
                                }
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rt!!].tag = _storeBuffers.value!![nextEmptyIndex].tag
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
            }
        }

        // Execute pending instructions.
        _instructionsStatus.value = _instructionsStatus.value!!.onEach {
            if (it.issued == _cycle.value!! - 1) {
                it.executed = _cycle.value
            }
        }

        // Decrement remaining cycles by one.
        _addStations.value = _addStations.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].executed != null) {
                it.remainingCycles = it.remainingCycles!! - 1
            }
        }

        _mulStations.value = _mulStations.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].executed != null) {
                it.remainingCycles = it.remainingCycles!! - 1
            }
        }

        _loadBuffers.value = _loadBuffers.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].executed != null) {
                it.remainingCycles = it.remainingCycles!! - 1
            }
        }

        _storeBuffers.value = _storeBuffers.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].executed != null) {
                it.remainingCycles = it.remainingCycles!! - 1
            }
        }
    }
}
