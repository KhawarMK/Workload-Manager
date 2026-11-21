package com.example.academicplanner.ui.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.academicplanner.data.local.entity.ModuleEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    viewModel: ModuleViewModel,
    modifier: Modifier = Modifier
) {
    // 1. Live database state
    val modules by viewModel.modules.collectAsState()

    // 2. Search query state
    var searchQuery by remember { mutableStateOf("") }

    // 3. Dialog state for Add / Edit
    var showAddEditDialog by remember { mutableStateOf(false) }
    var moduleToEdit by remember { mutableStateOf<ModuleEntity?>(null) }

    // Filter modules based on search input
    val filteredModules = remember(modules, searchQuery) {
        if (searchQuery.isBlank()) {
            modules
        } else {
            modules.filter {
                it.moduleCode.contains(searchQuery, ignoreCase = true) ||
                        it.moduleName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    // Main Dark Container
    Scaffold(
        containerColor = Color(0xFF0F0F12), // Deep dark background from mockup
        bottomBar = {
            // --- BOTTOM ACTION BAR: [Import] & [+ Add module] ---
            Surface(
                color = Color(0xFF0F0F12),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Import Button (Outlined)
                    OutlinedButton(
                        onClick = {
                            // Will open calendar import dialog in Step 3!
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF1E1E24),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color(0xFFB3A8FF),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Import", fontWeight = FontWeight.SemiBold)
                    }

                    // Add Module Button
                    Button(
                        onClick = {
                            moduleToEdit = null
                            showAddEditDialog = true
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B4DFF),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1.4f)
                            .height(52.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add module", fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // HEADER: Modules
            Text(
                text = "YOUR SEMESTER",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9D8CFF), // Light purple
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Modules",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${modules.size} modules • 0 linked assignments",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF8E8E93)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // SEARCH BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search modules", color = Color(0xFF6C6C75)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF1E1E24),
                        unfocusedContainerColor = Color(0xFF1E1E24),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF5B4DFF),
                        unfocusedBorderColor = Color(0xFF2C2C35)
                    ),
                    modifier = Modifier.weight(1f)
                )

                // Filter Icon Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF1E1E24),
                    modifier = Modifier.size(54.dp)
                ) {
                    IconButton(onClick = { /* Filter */ }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SCROLLABLE MODULES LIST
            if (filteredModules.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isNotBlank()) "No modules match your search." else "No modules added yet. Tap '+ Add module' below!",
                        color = Color(0xFF8E8E93),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(filteredModules, key = { it.id }) { module ->
                        StudyPlannerModuleCard(
                            module = module,
                            onClick = {
                                moduleToEdit = module
                                showAddEditDialog = true
                            },
                            onDelete = {
                                viewModel.deleteModule(module)
                            }
                        )
                    }
                }
            }
        }
    }

    //  ADD / EDIT POPUP
    if (showAddEditDialog) {
        AddEditModuleDialog(
            moduleToEdit = moduleToEdit,
            onDismiss = { showAddEditDialog = false },
            onSave = { code, name, credits, colorHex ->
                if (moduleToEdit == null) {
                    viewModel.addModule(code, name, credits, colorHex)
                } else {
                    viewModel.updateModule(moduleToEdit!!.id, code, name, credits, colorHex)
                }
                showAddEditDialog = false
            }
        )
    }
}

/**
 * Clean Add / Edit Dialog with vibrant color palette choices.
 */
@Composable
fun AddEditModuleDialog(
    moduleToEdit: ModuleEntity?,
    onDismiss: () -> Unit,
    onSave: (code: String, name: String, credits: Int, colorHex: String) -> Unit
) {
    var code by remember { mutableStateOf(moduleToEdit?.moduleCode ?: "") }
    var name by remember { mutableStateOf(moduleToEdit?.moduleName ?: "") }
    var creditsText by remember { mutableStateOf(moduleToEdit?.moduleCredits?.toString() ?: "15") }
    var selectedColor by remember { mutableStateOf(moduleToEdit?.moduleColors ?: "#5B4DFF") }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    val palette = listOf("#5B4DFF", "#00BFA5", "#FF7043", "#FFA726", "#AB47BC", "#26A69A")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1E24),
        titleContentColor = Color.White,
        textContentColor = Color(0xFFCCCCCC),
        title = {
            Text(if (moduleToEdit == null) "Add Module" else "Edit Module", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it; errorMessage = null },
                    label = { Text("Module Code (e.g. CS2800)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; errorMessage = null },
                    label = { Text("Module Name (e.g. Final Year Project)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = creditsText,
                    onValueChange = { creditsText = it.filter { c -> c.isDigit() }; errorMessage = null },
                    label = { Text("Credits") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text("Module Color:", style = MaterialTheme.typography.labelMedium, color = Color(0xFF8E8E93))

                // Color swatch picker
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    palette.forEach { colorHex ->
                        val color = Color(android.graphics.Color.parseColor(colorHex))
                        val isSelected = selectedColor == colorHex

                        Box(
                            modifier = Modifier
                                .size(if (isSelected) 30.dp else 24.dp)
                                .clip(CircleShape)
                                .background(color)
                        ) {
                            IconButton(
                                onClick = { selectedColor = colorHex },
                                modifier = Modifier.fillMaxSize()
                            ) {}
                        }
                    }
                }

                if (errorMessage != null) {
                    Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (code.isBlank() || name.isBlank()) {
                        errorMessage = "Please enter both module code and name."
                    } else {
                        val credits = creditsText.toIntOrNull() ?: 15
                        onSave(code.trim(), name.trim(), credits, selectedColor)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B4DFF))
            ) {
                Text(if (moduleToEdit == null) "Add" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF8E8E93))
            }
        }
    )
}