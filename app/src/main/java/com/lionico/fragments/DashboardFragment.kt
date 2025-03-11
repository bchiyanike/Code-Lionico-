/**
 * DashboardFragment.kt
 * This fragment serves as the main dashboard for the application.
 * It displays summary cards, notifications, and work history.
 */
package com.lionico.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lionico.R
import com.lionico.adapters.NotificationsAdapter
import com.lionico.adapters.SummaryCardAdapter
import com.lionico.viewmodels.DashboardViewModel
import com.lionico.data.entities.Notification
import com.lionico.data.entities.SummaryCard

class DashboardFragment : Fragment() {
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        setupSummaryCards(view)
        setupNotifications(view)
        setupWorkHistory(view)

        return view
    }

    private fun setupSummaryCards(view: View) {
        val adapter = SummaryCardAdapter(listOf(
            SummaryCard("Crop Health", "85% Good", R.color.green, "CropHealthFragment"),
            SummaryCard("Low Stock", "3 Items", R.color.orange, "InventoryFragment"),
            SummaryCard("Weather", "Sunny", R.color.blue, "WeatherFragment"),
            SummaryCard("Tasks Due", "2 Unfinished", R.color.red, "LabourFragment")
        )) { tab -> navigateToTab(tab) }

        view.findViewById<RecyclerView>(R.id.rvSummaryCards).apply {
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }
    }

    private fun setupNotifications(view: View) {
        val adapter = NotificationsAdapter { notification ->
            navigateToTab(notification.linkedTab)
        }
        viewModel.notifications.observe(viewLifecycleOwner) { notifications ->
            adapter.submitList(notifications)
        }
        view.findViewById<RecyclerView>(R.id.rvNotifications).adapter = adapter
    }

    private fun setupWorkHistory(view: View) {
        val adapter = WorkHistoryAdapter()
        viewModel.workHistory.observe(viewLifecycleOwner) { history ->
            adapter.submitList(history)
        }
        view.findViewById<RecyclerView>(R.id.rvWorkHistory).adapter = adapter
    }

    private fun navigateToTab(tab: String) {
        when (tab) {
            "CropHealthFragment" -> findNavController().navigate(R.id.action_to_cropHealth)
            "InventoryFragment" -> findNavController().navigate(R.id.action_to_inventory)
            // Add other tabs as needed
        }
    }
}
