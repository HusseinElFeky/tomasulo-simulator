package com.husseinelfeky.tomasulosimulator.ui.instructions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.ui.instructions.adapter.InstructionsAdapter
import com.husseinelfeky.tomasulosimulator.ui.main.MainActivity
import com.husseinelfeky.tomasulosimulator.utils.showSnackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_instructions.*
import timber.log.Timber

class InstructionsFragment : Fragment(R.layout.fragment_instructions) {

    private val viewModel: InstructionsViewModel by viewModels()

    private val instructionsAdapter = InstructionsAdapter { instruction ->
        viewModel.updateInstruction(instruction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        (requireActivity() as MainActivity).apply {
            btn_action_secondary.visibility = View.VISIBLE

            fab_action.setImageResource(R.drawable.ic_add)
            btn_action_primary.text = getString(R.string.proceed)
            btn_action_secondary.text = getString(R.string.clear)
        }

        rv_instructions.adapter = instructionsAdapter
    }

    private fun initListeners() {
        (requireActivity() as MainActivity).apply {
            fab_action.setOnClickListener {
                viewModel.addNewInstruction()
            }

            btn_action_primary.setOnClickListener {
                if (viewModel.areInstructionsValid()) {
                    findNavController().navigate(
                        InstructionsFragmentDirections.actionInstructionsFragmentToSimulationFragment(
                            viewModel.instructions.value!!.toTypedArray()
                        )
                    )
                } else {
                    showSnackbar(R.string.error_instructions_missing)
                }
            }

            btn_action_secondary.setOnClickListener {
                viewModel.clearInstructions()
            }
        }
    }

    private fun initObservers() {
        viewModel.instructions.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            instructionsAdapter.submitList(ArrayList(it))
        }
    }
}
