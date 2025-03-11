// Adapter for displaying summary cards in a RecyclerView
package com.lionico.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lionico.databinding.ItemSummaryCardBinding
import com.lionico.ui.dashboard.DashboardViewModel.SummaryCard

/**
 * Adapter for displaying summary cards.
 */
class SummaryCardAdapter : ListAdapter<SummaryCard, SummaryCardAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSummaryCardBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemSummaryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a summary card to the ViewHolder.
         * @param item The summary card to bind.
         */
        fun bind(item: SummaryCard) {
            binding.apply {
                textTitle.text = item.title
                textValue.text = item.value
                imageIcon.setImageResource(item.iconRes)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SummaryCard>() {
            override fun areItemsTheSame(oldItem: SummaryCard, newItem: SummaryCard): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: SummaryCard, newItem: SummaryCard): Boolean {
                return oldItem == newItem
            }
        }
    }
}
