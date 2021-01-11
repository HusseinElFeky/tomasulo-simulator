package com.husseinelfeky.tomasulosimulator.model.simulation

import com.husseinelfeky.tomasulosimulator.model.operation.BaseOperation
import com.husseinelfeky.tomasulosimulator.model.operation.Operation
import com.husseinelfeky.tomasulosimulator.model.simulation.base.RowItem
import com.husseinelfeky.tomasulosimulator.model.simulation.general.Tag

data class ReservationStation(
    override val tag: Tag,
    val operation: Operation? = null,
    val vj: Int? = null,
    val vk: Int? = null,
    val qj: Tag? = null,
    val qk: Tag? = null,
    override val isBusy: Boolean = false,
    override val remainingCycles: Int? = null
) : RowItem(tag, isBusy, remainingCycles) {

    companion object {
        private const val STATIONS_ADD = 3
        private const val STATIONS_MUL = 2

        fun getAddStations(): List<ReservationStation> {
            return (1..STATIONS_ADD).toList().map {
                ReservationStation(Tag(BaseOperation.A, it))
            }
        }

        fun getMulStations(): List<ReservationStation> {
            return (1..STATIONS_MUL).toList().map {
                ReservationStation(Tag(BaseOperation.M, it))
            }
        }
    }
}
