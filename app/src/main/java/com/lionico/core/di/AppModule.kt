// Defines Hilt modules for dependency injection
package com.lionico.core.di

import android.content.Context
import com.lionico.core.data.local.AppDatabase
import com.lionico.core.data.local.dao.InventoryDao
import com.lionico.core.data.local.dao.ManualEntryDao
import com.lionico.core.data.repositories.InventoryRepository
import com.lionico.core.data.repositories.ManualEntryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing application-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the Room database instance.
     * @param context The application context.
     * @return The AppDatabase instance.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    /**
     * Provides the InventoryDao instance.
     * @param database The AppDatabase instance.
     * @return The InventoryDao instance.
     */
    @Provides
    fun provideInventoryDao(database: AppDatabase): InventoryDao {
        return database.inventoryDao()
    }

    /**
     * Provides the ManualEntryDao instance.
     * @param database The AppDatabase instance.
     * @return The ManualEntryDao instance.
     */
    @Provides
    fun provideManualEntryDao(database: AppDatabase): ManualEntryDao {
        return database.manualEntryDao()
    }

    /**
     * Provides the InventoryRepository instance.
     * @param inventoryDao The InventoryDao instance.
     * @return The InventoryRepository instance.
     */
    @Provides
    fun provideInventoryRepository(inventoryDao: InventoryDao): InventoryRepository {
        return InventoryRepository(inventoryDao)
    }

    /**
     * Provides the ManualEntryRepository instance.
     * @param manualEntryDao The ManualEntryDao instance.
     * @return The ManualEntryRepository instance.
     */
    @Provides
    fun provideManualEntryRepository(manualEntryDao: ManualEntryDao): ManualEntryRepository {
        return ManualEntryRepository(manualEntryDao)
    }
}
