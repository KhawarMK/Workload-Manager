package com.example.academicplanner.data.repository

import com.example.academicplanner.data.local.dao.ModuleDao
import com.example.academicplanner.data.local.entity.ModuleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository class for the "modules" table.
 * Acts as an intermediates between the viewmodel and the UI.
 */
class ModuleRepository(private val moduleDao: ModuleDao) {
    val allModules: Flow<List<ModuleEntity>> = moduleDao.getAllModules()

    /**
     * inserts a new module into the database
     */
    suspend fun insertModule(module: ModuleEntity) : Long {
        return moduleDao.insertModule(module)
    }
    /**
     * updates an existing module in the database
     */
    suspend fun updateModule(module: ModuleEntity) {
        moduleDao.updateModule(module)
    }

    /**
     * Deletes a module from the database.
     */
    suspend fun deleteModule(module: ModuleEntity) {
        moduleDao.deleteModule(module)
    }

    }

