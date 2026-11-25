package com.example.academicplanner.ui.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.academicplanner.data.local.entity.ModuleEntity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment

/**
 * Module Card.
 */
@Composable
fun StudyPlannerModuleCard(
    module: ModuleEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) {
    // Parse the module's color safely
    val accentColor = remember(module.moduleColors) {
        try {
            Color(android.graphics.Color.parseColor(module.moduleColors))
        } catch (e: Exception) {
            Color(0xFF6C63FF) // Default vibrant purple
        }
    }

    // Outer Container
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF1E1E24),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min) // Matches the left strip height to the card
        ) {

            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(accentColor)
            )

            // INNER CONTENT
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = module.moduleName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${module.moduleCode} • ${module.moduleCredits} Credits",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF8E8E93)
                )
                // DELETE BUTTON
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Module",
                        tint = Color(0xFFFF5252))// Red trash bin icon
            }
        }
    }
}
}

