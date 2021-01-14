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
import timber.log.Timber

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
            switch_action.visibility = View.VISIBLE

            fab_action.setImageResource(R.drawable.ic_play)
            btn_action_primary.text = getString(R.string.exit_simulation)
            switch_action.text = getString(R.string.show_values)
            switch_action.isChecked = false
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

            switch_action.setOnCheckedChangeListener { _, isChecked ->
                viewModel.showValues(isChecked)
            }
        }
    }

    private fun initObservers() {
        viewModel.cycle.observe(viewLifecycleOwner) { cycle ->
            (requireActivity() as MainActivity).supportActionBar?.title = getString(
                R.string.format_cycle,
                cycle
            )
        }

        viewModel.isSimulationEnded.observe(viewLifecycleOwner) { isSimulationEnded ->
            (requireActivity() as MainActivity).fab_action.isEnabled = !isSimulationEnded
        }

        viewModel.instructionsStatus.observe(viewLifecycleOwner) { list ->
            Timber.i("Cycle ${viewModel.cycle.value}: $list")
            instructionsStatusAdapter.submitList(list.map { it.copy() })
        }

        viewModel.registers.observe(viewLifecycleOwner) { list ->
            Timber.i("Cycle ${viewModel.cycle.value}: $list")
            registersAdapter.submitList(list.map { it.copy() })
        }

        viewModel.addStations.observe(viewLifecycleOwner) { list ->
            Timber.i("Cycle ${viewModel.cycle.value}: $list")
            addStationsAdapter.submitList(list.map { it.copy() })
        }

        viewModel.mulStations.observe(viewLifecycleOwner) { list ->
            Timber.i("Cycle ${viewModel.cycle.value}: $list")
            mulStationsAdapter.submitList(list.map { it.copy() })
        }

        viewModel.loadBuffers.observe(viewLifecycleOwner) { list ->
            Timber.i("Cycle ${viewModel.cycle.value}: $list")
            loadBuffersAdapter.submitList(list.map { it.copy() })
        }

        viewModel.storeBuffers.observe(viewLifecycleOwner) { list ->
            Timber.i("Cycle ${viewModel.cycle.value}: $list")
            storeBuffersAdapter.submitList(list.map { it.copy() })
        }
    }
}
