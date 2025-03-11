// Adapter for displaying inventory items in a RecyclerView
package com.lionico.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lionico.core.data.local.entities.InventoryItem
import com.lionico.databinding.ItemInventoryBinding

/**
 * Adapter for displaying a list of inventory items.
 * @param onItemClick Callback when an item is clicked.
 * @param onItemDelete Callback when an item is deleted.
 */
class InventoryAdapter(
    private val onItemClick: (InventoryItem) -> Unit,
    private val onItemDelete: (InventoryItem) -> Unit
) : ListAdapter<InventoryItem, InventoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInventoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemInventoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds an inventory item to the ViewHolder.
         * @param item The inventory item to bind.
         */
        fun bind(item: InventoryItem) {
            binding.apply {
                textItemName.text = item.itemName
                textItemQuantity.text = "Quantity: ${item.quantity}"
                root.setOnClickListener { onItemClick(item) }
                buttonDelete.setOnClickListener { onItemDelete(item) }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<InventoryItem>() {
            override fun areItemsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
