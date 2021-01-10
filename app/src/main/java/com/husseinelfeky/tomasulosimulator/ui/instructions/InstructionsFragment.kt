package com.husseinelfeky.tomasulosimulator.ui.instructions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.husseinelfeky.tomasulosimulator.R
import kotlinx.android.synthetic.main.fragment_instructions.*

class InstructionsFragment : Fragment(R.layout.fragment_instructions) {

    private val viewModel: InstructionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        fab_add_instruction.setOnClickListener {
            // TODO
        }
    }
}
