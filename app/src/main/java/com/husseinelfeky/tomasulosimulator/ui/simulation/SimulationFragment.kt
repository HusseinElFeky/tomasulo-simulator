package com.husseinelfeky.tomasulosimulator.ui.simulation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class SimulationFragment : Fragment(R.layout.fragment_simulation) {

    private val viewModel: SimulationViewModel by viewModels()

    private val args: SimulationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        (requireActivity() as MainActivity).apply {
            btn_action_secondary.visibility = View.GONE

            fab_action.setImageResource(R.drawable.ic_play)
            btn_action_primary.text = getString(R.string.exit)
        }

        viewModel.initInstructions(args.instructions)
    }

    private fun initListeners() {
        (requireActivity() as MainActivity).apply {
            fab_action.setOnClickListener {
                viewModel.goToNextCycle()
            }

            btn_action_primary.setOnClickListener {
                findNavController().navigateUp()
            }

            btn_action_secondary.setOnClickListener(null)
        }
    }

    private fun initObservers() {
        viewModel.instructionsStatus.observe(viewLifecycleOwner) {
            // TODO
        }
    }
}
