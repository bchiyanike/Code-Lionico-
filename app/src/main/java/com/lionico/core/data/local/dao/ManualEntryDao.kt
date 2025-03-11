// app/src/main/java/com/lionico/core/data/local/dao/ManualEntryDao.kt
package com.lionico.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lionico.core.data.local.entities.ManualEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ManualEntryDao {
    @Insert
    suspend fun insert(entry: ManualEntry)

    @Query("SELECT * FROM manual_entries WHERE tabType = :tabType ORDER BY entryDate DESC")
    fun getEntriesByType(tabType: String): Flow<List<ManualEntry>>

    @Query("SELECT * FROM manual_entries ORDER BY entryDate DESC")
    fun getAllEntries(): Flow<List<ManualEntry>>
}
