// Provides a clean API for accessing inventory data
package com.lionico.core.data.repositories

import com.lionico.core.data.local.dao.InventoryDao
import com.lionico.core.data.local.entities.InventoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository for managing inventory data.
 * Acts as a single source of truth for inventory-related operations.
 * @property inventoryDao The DAO for accessing inventory data.
 */
class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao
) {

    /**
     * Retrieves all inventory items as a Flow.
     * @return A Flow of lists containing all inventory items.
     */
    fun getAllItems(): Flow<List<InventoryItem>> = inventoryDao.getAllItems()

    /**
     * Retrieves an inventory item by its ID.
     * @param id The ID of the item to retrieve.
     * @return The inventory item with the specified ID.
     */
    suspend fun getItemById(id: Long): InventoryItem? = inventoryDao.getItemById(id)

    /**
     * Inserts a new inventory item into the database.
     * @param item The inventory item to insert.
     */
    suspend fun insertItem(item: InventoryItem) = inventoryDao.insert(item)

    /**
     * Updates an existing inventory item in the database.
     * @param item The inventory item to update.
     */
    suspend fun updateItem(item: InventoryItem) = inventoryDao.update(item)

    /**
     * Deletes an inventory item by its ID.
     * @param id The ID of the item to delete.
     */
    suspend fun deleteItemById(id: Long) = inventoryDao.deleteById(id)
}
