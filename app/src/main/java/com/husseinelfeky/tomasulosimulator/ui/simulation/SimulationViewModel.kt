package com.husseinelfeky.tomasulosimulator.ui.simulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husseinelfeky.tomasulosimulator.model.instruction.ITypeInstruction
import com.husseinelfeky.tomasulosimulator.model.instruction.Instruction
import com.husseinelfeky.tomasulosimulator.model.instruction.RTypeInstruction
import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.ReservationStation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.SimulationItem.Companion.indexOfNextEmpty
import com.husseinelfeky.tomasulosimulator.model.simulation.buffer.LoadBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.buffer.StoreBuffer
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Address.Companion.toInt
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus.Companion.hasSimulationFinished
import com.husseinelfeky.tomasulosimulator.model.simulation.general.InstructionStatus.Companion.indexOfNextInstructionStatus
import com.husseinelfeky.tomasulosimulator.model.simulation.general.ValueReference
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR
import com.husseinelfeky.tomasulosimulator.utils.roundTo

class SimulationViewModel : ViewModel() {

    private val _cycle = MutableLiveData(0)
    val cycle: LiveData<Int>
        get() = _cycle

    private val _isSimulationEnded = MutableLiveData(false)
    val isSimulationEnded: LiveData<Boolean>
        get() = _isSimulationEnded

    private val _instructionsStatus = MutableLiveData<List<InstructionStatus>>()
    val instructionsStatus: LiveData<List<InstructionStatus>>
        get() = _instructionsStatus

    private val _registers = MutableLiveData(FPR.getAll())
    val registers: LiveData<List<FPR>>
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
        if (_instructionsStatus.value == null) {
            _instructionsStatus.value = instructionsArray.map {
                it.toInstructionStatus()
            }
        }
    }

    private fun canIssue(instruction: Instruction): Boolean {
        when (instruction.baseOperation) {
            BaseOperation.A -> {
                if (_addStations.value!!.indexOfNextEmpty() == -1) return false
            }
            BaseOperation.M -> {
                if (_mulStations.value!!.indexOfNextEmpty() == -1) return false
            }
            BaseOperation.L -> {
                instruction as ITypeInstruction
                if (_loadBuffers.value!!.indexOfNextEmpty() == -1) return false

                // Check the address of previous instructions in all store buffers.
                _storeBuffers.value!!.forEach {
                    if (it.address?.toInt() == instruction.address.toInt()) return false
                }
            }
            BaseOperation.S -> {
                instruction as ITypeInstruction
                if (_storeBuffers.value!!.indexOfNextEmpty() == -1) return false

                // Check the address of previous instructions in all load and store buffers.
                _loadBuffers.value!!.forEach {
                    if (it.address?.toInt() == instruction.address.toInt()) return false
                }
                _storeBuffers.value!!.forEach {
                    if (it.address?.toInt() == instruction.address.toInt()) return false
                }
            }
        }

        return true
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
            when (nextInst.baseOperation) {
                BaseOperation.A -> {
                    nextInst as RTypeInstruction
                    if (canIssue(nextInst)) {
                        val nextEmptyIndex = _addStations.value!!.indexOfNextEmpty()
                        val rsRegister = _registers.value!![nextInst.rs!!]
                        val rtRegister = _registers.value!![nextInst.rt!!]
                        _addStations.value = _addStations.value!!.apply {
                            this[nextEmptyIndex].apply {
                                tag.assignedRegister = nextInst.rd
                                operation = nextInst.operation
                                if (rsRegister.tag == null) {
                                    vj = ValueReference(rsRegister.content, nextInst.rs!!)
                                } else {
                                    qj = rsRegister.tag!!.copy()
                                }
                                if (rtRegister.tag == null) {
                                    vk = ValueReference(rtRegister.content, nextInst.rt!!)
                                } else {
                                    qk = rtRegister.tag!!.copy()
                                }
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rd!!].tag =
                                _addStations.value!![nextEmptyIndex].tag.apply {
                                    assignedRegister = nextInst.rd
                                }
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
                BaseOperation.M -> {
                    nextInst as RTypeInstruction
                    if (canIssue(nextInst)) {
                        val nextEmptyIndex = _mulStations.value!!.indexOfNextEmpty()
                        val rsRegister = _registers.value!![nextInst.rs!!]
                        val rtRegister = _registers.value!![nextInst.rt!!]
                        _mulStations.value = _mulStations.value!!.apply {
                            this[nextEmptyIndex].apply {
                                tag.assignedRegister = nextInst.rd
                                operation = nextInst.operation
                                if (rsRegister.tag == null) {
                                    vj = ValueReference(rsRegister.content, nextInst.rs!!)
                                } else {
                                    qj = rsRegister.tag!!.copy()
                                }
                                if (rtRegister.tag == null) {
                                    vk = ValueReference(rtRegister.content, nextInst.rt!!)
                                } else {
                                    qk = rtRegister.tag!!.copy()
                                }
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rd!!].tag =
                                _mulStations.value!![nextEmptyIndex].tag.apply {
                                    assignedRegister = nextInst.rd
                                }
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
                BaseOperation.L -> {
                    nextInst as ITypeInstruction
                    if (canIssue(nextInst)) {
                        val nextEmptyIndex = _loadBuffers.value!!.indexOfNextEmpty()
                        _loadBuffers.value = _loadBuffers.value!!.apply {
                            this[nextEmptyIndex].apply {
                                tag.assignedRegister = nextInst.rt
                                address = nextInst.address
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rt!!].tag =
                                _loadBuffers.value!![nextEmptyIndex].tag.apply {
                                    assignedRegister = nextInst.rt
                                }
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
                BaseOperation.S -> {
                    nextInst as ITypeInstruction
                    if (canIssue(nextInst)) {
                        val nextEmptyIndex = _storeBuffers.value!!.indexOfNextEmpty()
                        val rtRegister = _registers.value!![nextInst.rt!!]
                        _storeBuffers.value = _storeBuffers.value!!.apply {
                            this[nextEmptyIndex].apply {
                                tag.assignedRegister = nextInst.rt
                                address = nextInst.address
                                if (rtRegister.tag == null) {
                                    v = ValueReference(rtRegister.content, nextInst.rt!!)
                                } else {
                                    q = rtRegister.tag!!.copy()
                                }
                                isBusy = true
                                instructionNumber = nextInst.number
                                remainingCycles = nextInst.operation!!.cycles
                            }
                        }
                        _registers.value = _registers.value!!.apply {
                            this[nextInst.rt!!].tag =
                                _storeBuffers.value!![nextEmptyIndex].tag.apply {
                                    assignedRegister = nextInst.rt
                                }
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[nextInstSIndex].issued = _cycle.value
                        }
                    }
                }
            }
        }

        // Write a computed instruction if there is any.
        var hasWritten = false

        _addStations.value = _addStations.value!!.apply {
            for (item in this) {
                if (item.remainingCycles == 0) {
                    _registers.value = _registers.value!!.apply {
                        this[item.assignedRegister!!].tag = null
                        this[item.assignedRegister!!].content = when (item.operation) {
                            Operation.ADD -> {
                                (item.vj!!.value + item.vk!!.value).roundTo(2)
                            }
                            Operation.SUB -> {
                                (item.vj!!.value - item.vk!!.value).roundTo(2)
                            }
                            else -> {
                                throw IllegalStateException("Operation ${item.operation} is not valid.")
                            }
                        }
                    }
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[item.instructionNumber!! - 1].written = _cycle.value
                    }
                    item.clear()
                    hasWritten = true
                    break
                }
            }
        }

        if (!hasWritten) {
            _mulStations.value = _mulStations.value!!.apply {
                for (item in this) {
                    if (item.remainingCycles == 0) {
                        _registers.value = _registers.value!!.apply {
                            this[item.assignedRegister!!].tag = null
                            this[item.assignedRegister!!].content = when (item.operation) {
                                Operation.MUL -> {
                                    (item.vj!!.value * item.vk!!.value).roundTo(2)
                                }
                                Operation.DIV -> {
                                    (item.vj!!.value / item.vk!!.value).roundTo(2)
                                }
                                else -> {
                                    throw IllegalStateException("Operation ${item.operation} is not valid.")
                                }
                            }
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[item.instructionNumber!! - 1].written = _cycle.value
                        }
                        item.clear()
                        hasWritten = true
                        break
                    }
                }
            }
        }

        if (!hasWritten) {
            _loadBuffers.value = _loadBuffers.value!!.apply {
                for (item in this) {
                    if (item.remainingCycles == 0) {
                        _registers.value = _registers.value!!.apply {
                            this[item.assignedRegister!!].tag = null
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[item.instructionNumber!! - 1].written = _cycle.value
                        }
                        item.clear()
                        hasWritten = true
                        break
                    }
                }
            }
        }

        if (!hasWritten) {
            _storeBuffers.value = _storeBuffers.value!!.apply {
                for (item in this) {
                    if (item.remainingCycles == 0) {
                        _registers.value = _registers.value!!.apply {
                            this[item.assignedRegister!!].tag = null
                        }
                        _instructionsStatus.value = _instructionsStatus.value!!.apply {
                            this[item.instructionNumber!! - 1].written = _cycle.value
                        }
                        item.clear()
                        hasWritten = true
                        break
                    }
                }
            }
        }

        // Execute ready instructions.
        _addStations.value = _addStations.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].issued != _cycle.value) {
                if (it.remainingCycles == it.operation!!.cycles) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].executed = _cycle.value
                    }
                }

                it.remainingCycles = it.remainingCycles!! - 1
                if (it.remainingCycles == 0) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].computed = _cycle.value
                    }
                }
            }
        }

        _mulStations.value = _mulStations.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].issued != _cycle.value) {
                if (it.remainingCycles == it.operation!!.cycles) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].executed = _cycle.value
                    }
                }

                it.remainingCycles = it.remainingCycles!! - 1
                if (it.remainingCycles == 0) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].computed = _cycle.value
                    }
                }
            }
        }

        _loadBuffers.value = _loadBuffers.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].issued != _cycle.value) {
                if (it.remainingCycles == Operation.LD.cycles) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].executed = _cycle.value
                    }
                }

                it.remainingCycles = it.remainingCycles!! - 1
                if (it.remainingCycles == 0) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].computed = _cycle.value
                    }
                }
            }
        }

        _storeBuffers.value = _storeBuffers.value!!.onEach {
            if (it.canExecute() && _instructionsStatus.value!![it.instructionNumber!! - 1].issued != _cycle.value) {
                if (it.remainingCycles == Operation.SD.cycles) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].executed = _cycle.value
                    }
                }

                it.remainingCycles = it.remainingCycles!! - 1
                if (it.remainingCycles == 0) {
                    _instructionsStatus.value = _instructionsStatus.value!!.apply {
                        this[it.instructionNumber!! - 1].computed = _cycle.value
                    }
                }
            }
        }

        // Publish written results to all reservation stations and store buffers.
        _addStations.value = _addStations.value!!.onEach {
            if (it.qj != null) {
                val rsRegister = _registers.value!![it.qj!!.assignedRegister!!]
                if (rsRegister.tag == null) {
                    it.vj = ValueReference(rsRegister.content, it.qj!!.assignedRegister!!)
                    it.qj = null
                }
            }
            if (it.qk != null) {
                val rtRegister = _registers.value!![it.qk!!.assignedRegister!!]
                if (rtRegister.tag == null) {
                    it.vk = ValueReference(rtRegister.content, it.qk!!.assignedRegister!!)
                    it.qk = null
                }
            }
        }

        _mulStations.value = _mulStations.value!!.onEach {
            if (it.qj != null) {
                val rsRegister = _registers.value!![it.qj!!.assignedRegister!!]
                if (rsRegister.tag == null) {
                    it.vj = ValueReference(rsRegister.content, it.qj!!.assignedRegister!!)
                    it.qj = null
                }
            }
            if (it.qk != null) {
                val rtRegister = _registers.value!![it.qk!!.assignedRegister!!]
                if (rtRegister.tag == null) {
                    it.vk = ValueReference(rtRegister.content, it.qk!!.assignedRegister!!)
                    it.qk = null
                }
            }
        }

        _storeBuffers.value = _storeBuffers.value!!.onEach {
            if (it.q != null) {
                val rtRegister = _registers.value!![it.q!!.assignedRegister!!]
                if (rtRegister.tag == null) {
                    it.v = ValueReference(rtRegister.content, it.q!!.assignedRegister!!)
                    it.q = null
                }
            }
        }

        // Check if simulation has finished.
        if (_instructionsStatus.value!!.hasSimulationFinished()) {
            _isSimulationEnded.value = true
        }
    }
}
