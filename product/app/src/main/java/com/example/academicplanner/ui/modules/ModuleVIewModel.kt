package com.example.academicplanner.ui.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.academicplanner.data.local.entity.ModuleEntity
import com.example.academicplanner.data.repository.ModuleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel class for the "modules" table.
 */
class ModuleViewModel(private val repository: ModuleRepository) : ViewModel() {

    /**
     * it takes data from SQLite and sends it to Compose.
     */
    val modules: StateFlow<List<ModuleEntity>> = repository.allModules
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    /**
     * CREATE: Inserts a new module with user inputs.
     */
    fun addModule(code: String, name: String, credits: Int, colorHex: String) {
        viewModelScope.launch {
            val newModule = ModuleEntity(
                moduleCode = code.trim(),
                moduleName = name.trim(),
                moduleCredits = credits,
                moduleColors = colorHex
            )
            repository.insertModule(newModule)
        }
    }

    /**
     * UPDATE: Edits an existing module.
     */
    fun updateModule(id: Long, code: String, name: String, credits: Int, colorHex: String) {
        viewModelScope.launch {
            val updatedModule = ModuleEntity(
                id = id,
                moduleCode = code.trim(),
                moduleName = name.trim(),
                moduleCredits = credits,
                moduleColors = colorHex
            )
            repository.updateModule(updatedModule)
        }
    }

    /**
     * DELETE: Deletes a module from SQLite.
     */
    fun deleteModule(module: ModuleEntity) {
        viewModelScope.launch {
            repository.deleteModule(module)
        }
    }
}

/**
 * Factory class required to instantiate the ModuleViewModel with a Repository dependency.
 */
class ModuleViewModelFactory(private val repository: ModuleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModuleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ModuleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}