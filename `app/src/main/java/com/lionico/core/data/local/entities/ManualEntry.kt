// app/src/main/java/com/lionico/core/data/local/entities/ManualEntry.kt
package com.lionico.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "manual_entries")
data class ManualEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tabType: String, // "weather", "soil", etc.
    val entryDate: LocalDate = LocalDate.now(),
    val field1: String = "",
    val field2: String = "",
    val field3: String = "",
    val notes: String = ""
)
