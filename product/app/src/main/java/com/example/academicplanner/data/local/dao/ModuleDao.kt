package com.example.academicplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.academicplanner.data.local.entity.ModuleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the "modules" table.
 */
@Dao
interface ModuleDao {
    /**
     * Retrieves all modules from the database in alphabetical order by code.
     */
    @Query("SELECT * FROM modules ORDER BY code ASC")
    fun getAllModules(): Flow<List<ModuleEntity>>

    /**
     * Retrieves a specific module by its ID.
     */
    @Query("SELECT * FROM modules WHERE id = :moduleId LIMIT 1")
    suspend fun getModuleById(moduleId: Long): ModuleEntity?

    /**
     * Inserts or updates a module in the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: ModuleEntity): Long

    @Update
    suspend fun updateModule(module: ModuleEntity)

    /**
     * Deletes a module from the database.
     */
    @Delete
    suspend fun deleteModule(module: ModuleEntity)

    /**
     * Deletes a module from the database by its ID.
     */

    @Query("DELETE FROM modules WHERE id = :moduleId")
    suspend fun deleteModuleById(moduleId: Long)
}