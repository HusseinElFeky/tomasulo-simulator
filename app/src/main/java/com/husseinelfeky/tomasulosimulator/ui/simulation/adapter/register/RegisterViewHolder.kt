package com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.model.simulation.register.FPR
import kotlinx.android.synthetic.main.item_register.view.*

class RegisterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(register: FPR) {
        with(itemView) {
            tv_name.text = register.name
            tv_tag.text = register.tag?.name ?: "-"
            tv_content.text = register.content.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): RegisterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_register,
                    parent,
                    false
                )
            return RegisterViewHolder(view)
        }
    }
}
