// ViewModel for the Dashboard screen
package com.lionico.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lionico.core.data.repositories.InventoryRepository
import com.lionico.core.data.repositories.ManualEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Dashboard screen.
 * @property inventoryRepository The repository for accessing inventory data.
 * @property manualEntryRepository The repository for accessing manual entry data.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val manualEntryRepository: ManualEntryRepository
) : ViewModel() {

    /**
     * Represents a summary card on the dashboard.
     * @property title The title of the card.
     * @property value The value to display.
     * @property iconRes The icon resource ID.
     */
    data class SummaryCard(
        val title: String,
        val value: String,
        val iconRes: Int
    )

    // State for summary cards
    private val _summaryCards = MutableStateFlow<List<SummaryCard>>(emptyList())
    val summaryCards: Flow<List<SummaryCard>> = _summaryCards

    // State for alerts
    private val _alerts = MutableStateFlow<List<String>>(emptyList())
    val alerts: Flow<List<String>> = _alerts

    init {
        // Observe inventory and manual entry data
        viewModelScope.launch {
            combine(
                inventoryRepository.getAllItems(),
                manualEntryRepository.getAllEntries()
            ) { inventoryItems, manualEntries ->
                // Generate summary cards
                val cards = mutableListOf<SummaryCard>()
                cards.add(
                    SummaryCard(
                        title = "Inventory Items",
                        value = inventoryItems.size.toString(),
                        iconRes = R.drawable.ic_inventory
                    )
                )
                cards.add(
                    SummaryCard(
                        title = "Soil Readings",
                        value = manualEntries.count { it.tabType == "soil" }.toString(),
                        iconRes = R.drawable.ic_soil
                    )
                )
                _summaryCards.value = cards

                // Generate alerts
                val alerts = mutableListOf<String>()
                inventoryItems.filter { it.quantity < 5 }.forEach {
                    alerts.add("Low stock: ${it.itemName} (${it.quantity} left)")
                }
                _alerts.value = alerts
            }.collect()
        }
    }
}
