package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.register

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.tomasulosimulator.model.simulation.Register
import com.husseinelfeky.tomasulosimulator.utils.adapter.DifferentiableItemDiffUtil

class RegistersAdapter : ListAdapter<Register, RegisterViewHolder>(
    DifferentiableItemDiffUtil.getInstance()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        return RegisterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RegisterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
