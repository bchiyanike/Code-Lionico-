// Adapter for displaying soil entries in a RecyclerView
package com.lionico.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lionico.core.data.local.entities.ManualEntry
import com.lionico.databinding.ItemSoilEntryBinding
import java.time.format.DateTimeFormatter

/**
 * Adapter for displaying a list of soil entries.
 */
class SoilEntryAdapter : ListAdapter<ManualEntry, SoilEntryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSoilEntryBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemSoilEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a soil entry to the ViewHolder.
         * @param entry The soil entry to bind.
         */
        fun bind(entry: ManualEntry) {
            binding.apply {
                textDate.text = entry.entryDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                textPh.text = "pH: ${entry.field1}"
                textMoisture.text = "Moisture: ${entry.field2}%"
                textNutrients.text = "Nutrients: ${entry.field3}"
                textNotes.text = entry.notes
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ManualEntry>() {
            override fun areItemsTheSame(oldItem: ManualEntry, newItem: ManualEntry): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ManualEntry, newItem: ManualEntry): Boolean {
                return oldItem == newItem
            }
        }
    }
}
