package com.example.academicplanner.ui.modules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.academicplanner.data.local.entity.ModuleEntity

@Composable
fun ModuleScreen(
    viewModel: ModuleViewModel,
    modifier: Modifier = Modifier
) {
    // 1. Observe the live list from the database
    val modules by viewModel.modules.collectAsState()

    // 2. Input field states
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var credits by remember { mutableStateOf("15") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Track which module we are editing (null means we are adding a new one)
    var editingModuleId by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- SCREEN TITLE ---
        Text(
            text = "Module Manager",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- INPUT FORM ---
        OutlinedTextField(
            value = code,
            onValueChange = { code = it; errorMessage = null },
            label = { Text("Module Code (e.g. CS2800)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; errorMessage = null },
            label = { Text("Module Name (e.g. Final Year Project)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = credits,
            onValueChange = { credits = it.filter { c -> c.isDigit() }; errorMessage = null },
            label = { Text("Credits") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Show validation error if user leaves a field blank
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- BUTTONS ROW ---
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    if (code.isBlank() || name.isBlank()) {
                        errorMessage = "Please fill in both code and name."
                    } else {
                        val creditsNum = credits.toIntOrNull() ?: 15

                        if (editingModuleId == null) {
                            // CREATE: Add new module
                            viewModel.addModule(code, name, creditsNum, "#000000")
                        } else {
                            // UPDATE: Save edits
                            viewModel.updateModule(editingModuleId!!, code, name, creditsNum, "#000000")
                            editingModuleId = null
                        }

                        // Clear the input fields
                        code = ""
                        name = ""
                        credits = "15"
                        errorMessage = null
                    }
                }
            ) {
                Text(if (editingModuleId == null) "Add Module" else "Save Changes")
            }

            // If editing, show a Cancel button
            if (editingModuleId != null) {
                OutlinedButton(
                    onClick = {
                        editingModuleId = null
                        code = ""
                        name = ""
                        credits = "15"
                        errorMessage = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))

        // --- MODULE LIST ---
        Text(
            text = "Saved Modules (${modules.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (modules.isEmpty()) {
            Text(
                text = "No modules saved yet. Add one above!",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(modules, key = { it.id }) { module ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Module Info
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${module.moduleCode}: ${module.moduleName}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${module.moduleCredits} Credits",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            // Edit Button
                            TextButton(
                                onClick = {
                                    // Fill the form fields with this module's data for editing
                                    editingModuleId = module.id
                                    code = module.moduleCode
                                    name = module.moduleName
                                    credits = module.moduleCredits.toString()
                                }
                            ) {
                                Text("Edit")
                            }

                            // Delete Button
                            TextButton(
                                onClick = { viewModel.deleteModule(module) }
                            ) {
                                Text("Delete", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}