// Fragment for the Soil Health tab
package com.lionico.ui.soil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lionico.databinding.FragmentSoilBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * SoilFragment allows farmers to input and view soil health data.
 */
@AndroidEntryPoint
class SoilFragment : Fragment() {

    private var _binding: FragmentSoilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SoilViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView for soil entries
        val adapter = SoilEntryAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observe soil entries
        lifecycleScope.launch {
            viewModel.soilEntries.collectLatest { entries ->
                adapter.submitList(entries)
            }
        }

        // Set up FAB for adding new soil entries
        binding.fabAdd.setOnClickListener {
            showSoilInputDialog()
        }
    }

    /**
     * Shows a dialog for inputting soil health data.
     */
    private fun showSoilInputDialog() {
        val dialogBinding = DialogSoilInputBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Add Soil Data")
            setView(dialogBinding.root)
            setPositiveButton("Save") { _, _ ->
                val pH = dialogBinding.editPh.text.toString()
                val moisture = dialogBinding.editMoisture.text.toString()
                val nutrients = dialogBinding.editNutrients.text.toString()
                val notes = dialogBinding.editNotes.text.toString()

                viewModel.addSoilEntry(pH, moisture, nutrients, notes)
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
