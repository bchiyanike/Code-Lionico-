// ViewModel for managing inventory data in the UI
package com.lionico.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lionico.core.data.repositories.InventoryRepository
import com.lionico.core.data.local.entities.InventoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Inventory screen.
 * @property repository The repository for accessing inventory data.
 */
@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val repository: InventoryRepository
) : ViewModel() {

    /**
     * Retrieves all inventory items as a Flow.
     * @return A Flow of lists containing all inventory items.
     */
    val allItems: Flow<List<InventoryItem>> = repository.getAllItems()

    /**
     * Inserts a new inventory item into the database.
     * @param item The inventory item to insert.
     */
    fun insertItem(item: InventoryItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    /**
     * Updates an existing inventory item in the database.
     * @param item The inventory item to update.
     */
    fun updateItem(item: InventoryItem) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    /**
     * Deletes an inventory item from the database.
     * @param item The inventory item to delete.
     */
    fun deleteItem(item: InventoryItem) {
        viewModelScope.launch {
            repository.deleteItemById(item.id)
        }
    }
}
