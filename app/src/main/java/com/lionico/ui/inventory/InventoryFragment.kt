// Defines the UI for inventory management
package com.lionico.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lionico.core.data.local.entities.InventoryItem
import com.lionico.databinding.FragmentInventoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for managing inventory items.
 */
@AndroidEntryPoint
class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        val adapter = InventoryAdapter(
            onItemClick = { item -> showEditDialog(item) },
            onItemDelete = { item -> viewModel.deleteItem(item) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observe inventory items
        lifecycleScope.launch {
            viewModel.allItems.collectLatest { items ->
                adapter.submitList(items)
            }
        }

        // Set up FAB for adding new items
        binding.fabAdd.setOnClickListener {
            showEditDialog(null)
        }
    }

    /**
     * Shows a dialog for adding or editing an inventory item.
     * @param item The item to edit, or null to add a new item.
     */
    private fun showEditDialog(item: InventoryItem?) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(if (item == null) "Add Item" else "Edit Item")
            setView(R.layout.dialog_inventory_input)
            setPositiveButton("Save") { _, _ ->
                val name = binding.dialogItemName.text.toString()
                val quantity = binding.dialogItemQuantity.text.toString().toIntOrNull() ?: 0
                if (item == null) {
                    viewModel.insertItem(InventoryItem(itemName = name, quantity = quantity))
                } else {
                    viewModel.updateItem(item.copy(itemName = name, quantity = quantity))
                }
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
