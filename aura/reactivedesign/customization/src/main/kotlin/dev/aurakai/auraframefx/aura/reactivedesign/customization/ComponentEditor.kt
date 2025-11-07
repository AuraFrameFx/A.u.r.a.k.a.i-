package dev.aurakai.auraframefx.aura.reactivedesign.customization

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Component Editor - MVP for editing UI component properties
 *
 * Provides sliders for basic transformation properties:
 * - Position (X, Y)
 * - Size (Width, Height)
 * - Rotation Z
 * - Z-Index (layer order)
 * - Opacity
 *
 * TODO: Implement property binding to actual components
 * TODO: Add live preview feedback
 * TODO: Add undo/redo support
 * TODO: Add preset management
 */
@Composable
fun ComponentEditor(
    componentId: String,
    onPropertyChanged: (String, Float) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var positionX by remember { mutableFloatStateOf(0f) }
    var positionY by remember { mutableFloatStateOf(0f) }
    var width by remember { mutableFloatStateOf(100f) }
    var height by remember { mutableFloatStateOf(100f) }
    var rotationZ by remember { mutableFloatStateOf(0f) }
    var zIndex by remember { mutableFloatStateOf(0f) }
    var opacity by remember { mutableFloatStateOf(1f) }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Component Editor",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Component: $componentId",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            // Position
            PropertySlider(
                label = "Position X",
                value = positionX,
                valueRange = -500f..500f,
                onValueChange = {
                    positionX = it
                    onPropertyChanged("positionX", it)
                }
            )

            PropertySlider(
                label = "Position Y",
                value = positionY,
                valueRange = -500f..500f,
                onValueChange = {
                    positionY = it
                    onPropertyChanged("positionY", it)
                }
            )

            // Size
            PropertySlider(
                label = "Width",
                value = width,
                valueRange = 0f..500f,
                onValueChange = {
                    width = it
                    onPropertyChanged("width", it)
                }
            )

            PropertySlider(
                label = "Height",
                value = height,
                valueRange = 0f..500f,
                onValueChange = {
                    height = it
                    onPropertyChanged("height", it)
                }
            )

            // Rotation
            PropertySlider(
                label = "Rotation Z",
                value = rotationZ,
                valueRange = 0f..360f,
                onValueChange = {
                    rotationZ = it
                    onPropertyChanged("rotationZ", it)
                }
            )

            // Z-Index
            PropertySlider(
                label = "Z-Index",
                value = zIndex,
                valueRange = 0f..100f,
                onValueChange = {
                    zIndex = it
                    onPropertyChanged("zIndex", it)
                }
            )

            // Opacity
            PropertySlider(
                label = "Opacity",
                value = opacity,
                valueRange = 0f..1f,
                onValueChange = {
                    opacity = it
                    onPropertyChanged("opacity", it)
                }
            )
        }
    }
}

@Composable
private fun PropertySlider(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "%.2f".format(value),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
