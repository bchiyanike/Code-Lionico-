// ViewModel for the Soil Health tab
package com.lionico.ui.soil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lionico.core.data.repositories.ManualEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Soil Health tab.
 * @property repository The repository for accessing manual entry data.
 */
@HiltViewModel
class SoilViewModel @Inject constructor(
    private val repository: ManualEntryRepository
) : ViewModel() {

    /**
     * Retrieves all soil entries from the database.
     * @return A Flow of lists containing soil entries.
     */
    val soilEntries: Flow<List<ManualEntry>> = repository.getEntriesByType("soil")

    /**
     * Adds a new soil entry to the database.
     * @param pH The soil pH value.
     * @param moisture The soil moisture level.
     * @param nutrients The soil nutrient levels (e.g., "N:10, P:5, K:8").
     * @param notes Additional notes about the soil.
     */
    fun addSoilEntry(pH: String, moisture: String, nutrients: String, notes: String) {
        viewModelScope.launch {
            repository.insert(
                ManualEntry(
                    tabType = "soil",
                    field1 = pH,
                    field2 = moisture,
                    field3 = nutrients,
                    notes = notes
                )
            )
        }
    }
}
