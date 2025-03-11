// Defines the Data Access Object (DAO) for InventoryItem
package com.lionico.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lionico.core.data.local.entities.InventoryItem
import kotlinx.coroutines.flow.Flow

/**
 * Provides methods to interact with the inventory_items table in the database.
 */
@Dao
interface InventoryDao {

    /**
     * Inserts a new inventory item into the database.
     * @param item The inventory item to insert.
     */
    @Insert
    suspend fun insert(item: InventoryItem)

    /**
     * Updates an existing inventory item in the database.
     * @param item The inventory item to update.
     */
    @Update
    suspend fun update(item: InventoryItem)

    /**
     * Retrieves all inventory items from the database.
     * @return A Flow of lists containing all inventory items.
     */
    @Query("SELECT * FROM inventory_items ORDER BY itemName ASC")
    fun getAllItems(): Flow<List<InventoryItem>>

    /**
     * Retrieves an inventory item by its ID.
     * @param id The ID of the item to retrieve.
     * @return The inventory item with the specified ID.
     */
    @Query("SELECT * FROM inventory_items WHERE id = :id")
    suspend fun getItemById(id: Long): InventoryItem?

    /**
     * Deletes an inventory item by its ID.
     * @param id The ID of the item to delete.
     */
    @Query("DELETE FROM inventory_items WHERE id = :id")
    suspend fun deleteById(id: Long)
}
