package dev.aurakai.auraframefx.aura.reactivedesign.customization

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Z-Order Editor - MVP for managing component layer order
 *
 * Displays a list of components with their current Z-index values.
 * Allows reordering components to control visual layering.
 *
 * TODO: Implement drag-to-reorder functionality
 * TODO: Add visual preview of layer order
 * TODO: Add bulk operations (move to front/back)
 * TODO: Add layer grouping/nesting support
 */

data class LayerItem(
    val id: String,
    val name: String,
    val zIndex: Int,
    val isVisible: Boolean = true,
    val isLocked: Boolean = false
)

@Composable
fun ZOrderEditor(
    layers: List<LayerItem>,
    onLayerReorder: (fromIndex: Int, toIndex: Int) -> Unit = { _, _ -> },
    onVisibilityToggle: (String) -> Unit = {},
    onLockToggle: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Z-Order Editor",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "${layers.size} layers",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            // TODO: Add drag-to-reorder functionality
            Text(
                text = "🚧 Drag-to-reorder coming soon",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(layers) { layer ->
                    LayerItemCard(
                        layer = layer,
                        onVisibilityToggle = { onVisibilityToggle(layer.id) },
                        onLockToggle = { onLockToggle(layer.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LayerItemCard(
    layer: LayerItem,
    onVisibilityToggle: () -> Unit,
    onLockToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (layer.isVisible) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Layer info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = layer.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Z-Index: ${layer.zIndex}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Visibility toggle
                IconButton(onClick = onVisibilityToggle) {
                    Text(
                        text = if (layer.isVisible) "👁" else "👁‍🗨",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Lock toggle
                IconButton(onClick = onLockToggle) {
                    Text(
                        text = if (layer.isLocked) "🔒" else "🔓",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
