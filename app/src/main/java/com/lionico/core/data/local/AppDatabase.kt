// Defines the Room database for the Lionico app
package com.lionico.core.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.lionico.core.data.local.dao.InventoryDao
import com.lionico.core.data.local.dao.ManualEntryDao
import com.lionico.core.data.local.entities.InventoryItem
import com.lionico.core.data.local.entities.ManualEntry

/**
 * The Room database for the Lionico app.
 * @property manualEntryDao Provides access to manual entry data.
 * @property inventoryDao Provides access to inventory data.
 */
@Database(
    entities = [ManualEntry::class, InventoryItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun manualEntryDao(): ManualEntryDao
    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the singleton instance of the database.
         * @param context The application context.
         * @return The database instance.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lionico_database"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
