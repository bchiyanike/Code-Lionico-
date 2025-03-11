// Fragment for the Dashboard screen
package com.lionico.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lionico.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * DashboardFragment serves as the central hub for summaries and alerts.
 */
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView for summary cards
        val adapter = SummaryCardAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns
        binding.recyclerView.adapter = adapter

        // Observe summary cards
        lifecycleScope.launch {
            viewModel.summaryCards.collectLatest { cards ->
                adapter.submitList(cards)
            }
        }

        // Observe alerts
        lifecycleScope.launch {
            viewModel.alerts.collectLatest { alerts ->
                if (alerts.isNotEmpty()) {
                    binding.textAlerts.text = alerts.joinToString("\n")
                } else {
                    binding.textAlerts.text = "No alerts"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
