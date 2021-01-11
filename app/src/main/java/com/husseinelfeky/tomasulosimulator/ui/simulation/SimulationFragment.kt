package com.husseinelfeky.tomasulosimulator.ui.simulation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.husseinelfeky.tomasulosimulator.R
import com.husseinelfeky.tomasulosimulator.ui.main.MainActivity
import com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.instructionstatus.InstructionsStatusAdapter
import com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.loadbuffer.LoadBuffersAdapter
import com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.register.RegistersAdapter
import com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.reservationstation.ReservationStationsAdapter
import com.husseinelfeky.tomasulosimulator.ui.simulation.adapter.storebuffer.StoreBuffersAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_simulation.*

class SimulationFragment : Fragment(R.layout.fragment_simulation) {

    private val viewModel: SimulationViewModel by viewModels()

    private val args: SimulationFragmentArgs by navArgs()

    private val instructionsStatusAdapter = InstructionsStatusAdapter()
    private val registersAdapter = RegistersAdapter()
    private val addStationsAdapter = ReservationStationsAdapter()
    private val mulStationsAdapter = ReservationStationsAdapter()
    private val loadBuffersAdapter = LoadBuffersAdapter()
    private val storeBuffersAdapter = StoreBuffersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindAdapters()
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

    private fun bindAdapters() {
        rv_instructions_status.adapter = instructionsStatusAdapter
        rv_registers.adapter = registersAdapter
        rv_add_stations.adapter = addStationsAdapter
        rv_mul_stations.adapter = mulStationsAdapter
        rv_load_buffers.adapter = loadBuffersAdapter
        rv_store_buffers.adapter = storeBuffersAdapter
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
        viewModel.cycle.observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).supportActionBar?.subtitle = getString(
                R.string.format_cycle,
                it
            )
        }

        viewModel.instructionsStatus.observe(viewLifecycleOwner) {
            instructionsStatusAdapter.submitList(ArrayList(it))
        }

        viewModel.registers.observe(viewLifecycleOwner) {
            registersAdapter.submitList(ArrayList(it))
        }

        viewModel.addStations.observe(viewLifecycleOwner) {
            addStationsAdapter.submitList(ArrayList(it))
        }

        viewModel.mulStations.observe(viewLifecycleOwner) {
            mulStationsAdapter.submitList(ArrayList(it))
        }

        viewModel.loadBuffers.observe(viewLifecycleOwner) {
            loadBuffersAdapter.submitList(ArrayList(it))
        }

        viewModel.storeBuffers.observe(viewLifecycleOwner) {
            storeBuffersAdapter.submitList(ArrayList(it))
        }
    }
}
