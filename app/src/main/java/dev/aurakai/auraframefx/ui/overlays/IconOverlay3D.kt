package dev.aurakai.auraframefx.ui.overlays

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.aurakai.auraframefx.ui.theme.CyberpunkPink
import dev.aurakai.auraframefx.ui.theme.CyberpunkCyan
import dev.aurakai.auraframefx.ui.theme.CyberpunkPurple
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 🎨 3D Icon Overlay System
 *
 * Creates floating, holographic icon cards in 3D space like the mockup.
 * Features:
 * - 3D perspective transforms
 * - Gyroscope-responsive positioning
 * - Neon glowing borders
 * - Parallax depth effect
 * - Draggable & editable
 * - Iconify integration
 *
 * Inspired by: Screenshot 2025-11-04 230351.png
 */

/**
 * 3D Icon Overlay data model
 */
data class IconOverlay3D(
    val id: String,
    val iconId: String, // Iconify icon (e.g., "mdi:pencil", "mdi:cube")
    val label: String,
    val description: String = "",

    // 3D Position (screen space with depth)
    val x: Float = 0f, // -1.0 (left) to 1.0 (right)
    val y: Float = 0f, // -1.0 (top) to 1.0 (bottom)
    val z: Float = 0f, // 0.0 (near) to 1.0 (far) - for parallax

    // 3D Rotation (for perspective)
    val rotationX: Float = 0f, // -180 to 180
    val rotationY: Float = 0f, // -180 to 180
    val rotationZ: Float = 0f, // -180 to 180

    // Scale & Properties
    val scale: Float = 1f,
    val alpha: Float = 1f,

    // Visual Style
    val glowColor: Color = CyberpunkCyan,
    val borderStyle: BorderStyle = BorderStyle.NEON_GLOW,

    // Behavior
    val isInteractive: Boolean = true,
    val isEditing: Boolean = false,
    val destination: String? = null, // Navigation destination

    // Animation
    val floatOffset: Float = 0f, // Vertical floating animation offset
    val pulseIntensity: Float = 1f
)

enum class BorderStyle {
    NEON_GLOW,
    HOLOGRAPHIC,
    SOLID,
    GRADIENT,
    ANIMATED_GRADIENT
}

/**
 * Icon Overlay layout presets (like the mockup)
 */
object IconOverlayPresets {
    /**
     * Main menu hub layout (center + 4 corners + 1 top)
     */
    fun mainMenuHub(): List<IconOverlay3D> = listOf(
        // Top card
        IconOverlay3D(
            id = "artist_library",
            iconId = "mdi:cube",
            label = "ARTIST LIBRARY",
            x = 0.65f,
            y = -0.35f,
            z = 0.2f,
            rotationY = -15f,
            glowColor = CyberpunkPurple
        ),

        // Top-left card
        IconOverlay3D(
            id = "collab_canvas",
            iconId = "mdi:pencil",
            label = "COLLAB CANVAS",
            x = -0.65f,
            y = -0.35f,
            z = 0.3f,
            rotationY = 15f,
            glowColor = CyberpunkCyan
        ),

        // Bottom-left card
        IconOverlay3D(
            id = "content",
            iconId = "mdi:package-variant",
            label = "CONTENT",
            x = -0.65f,
            y = 0.35f,
            z = 0.25f,
            rotationY = 15f,
            glowColor = CyberpunkCyan
        ),

        // Bottom-right card
        IconOverlay3D(
            id = "cloud_sync",
            iconId = "mdi:cloud-upload",
            label = "CLOUD SYNC",
            x = 0.65f,
            y = 0.35f,
            z = 0.2f,
            rotationY = -15f,
            glowColor = CyberpunkPink
        )
    )

    /**
     * Circular orbit layout
     */
    fun circularOrbit(count: Int = 6, radius: Float = 0.6f): List<IconOverlay3D> {
        return (0 until count).map { index ->
            val angle = (index / count.toFloat()) * 2f * Math.PI.toFloat()
            val x = cos(angle) * radius
            val y = sin(angle) * radius

            IconOverlay3D(
                id = "icon_$index",
                iconId = "mdi:star", // Default, should be customized
                label = "ITEM ${index + 1}",
                x = x,
                y = y,
                z = 0.2f + (index % 3) * 0.1f,
                rotationY = -angle * 57.3f, // Convert to degrees, face center
                glowColor = when (index % 3) {
                    0 -> CyberpunkCyan
                    1 -> CyberpunkPink
                    else -> CyberpunkPurple
                }
            )
        }
    }

    /**
     * Grid layout
     */
    fun grid(rows: Int = 2, cols: Int = 3): List<IconOverlay3D> {
        val overlays = mutableListOf<IconOverlay3D>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val x = -0.6f + (col / (cols - 1).toFloat()) * 1.2f
                val y = -0.4f + (row / (rows - 1).toFloat()) * 0.8f

                overlays.add(
                    IconOverlay3D(
                        id = "icon_${row}_${col}",
                        iconId = "mdi:apps",
                        label = "APP ${row * cols + col + 1}",
                        x = x,
                        y = y,
                        z = 0.15f + ((row + col) % 3) * 0.1f,
                        glowColor = when ((row + col) % 3) {
                            0 -> CyberpunkCyan
                            1 -> CyberpunkPink
                            else -> CyberpunkPurple
                        }
                    )
                )
            }
        }

        return overlays
    }
}

/**
 * Individual 3D Icon Overlay Card
 */
@Composable
fun IconOverlay3DCard(
    overlay: IconOverlay3D,
    gyroscopeX: Float = 0f, // -1 to 1
    gyroscopeY: Float = 0f, // -1 to 1
    screenWidth: Float,
    screenHeight: Float,
    onClick: () -> Unit = {},
    onDrag: (Offset) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    // Calculate screen position from normalized coordinates
    val screenX = (overlay.x * screenWidth / 2f) + (gyroscopeX * 50f * (1f - overlay.z))
    val screenY = (overlay.y * screenHeight / 2f) + (gyroscopeY * 50f * (1f - overlay.z))

    // Floating animation
    val infiniteTransition = rememberInfiniteTransition(label = "float_${overlay.id}")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000 + (overlay.id.hashCode() % 1000), easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // Pulse animation for glow
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Scale based on depth (z-index)
    val depthScale = 1f - (overlay.z * 0.3f)

    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    x = screenX.roundToInt(),
                    y = (screenY + floatOffset).roundToInt()
                )
            }
            .size((140.dp * depthScale * overlay.scale))
            .graphicsLayer {
                // 3D transforms
                rotationX = overlay.rotationX + (gyroscopeY * 10f)
                rotationY = overlay.rotationY + (gyroscopeX * 10f)
                rotationZ = overlay.rotationZ
                scaleX = depthScale * overlay.scale
                scaleY = depthScale * overlay.scale
                alpha = overlay.alpha
                cameraDistance = 12f * density

                // Shadow depth
                shadowElevation = (20f * (1f - overlay.z)).dp.toPx()
            }
            .clickable(enabled = overlay.isInteractive, onClick = onClick)
            .pointerInput(Unit) {
                if (overlay.isEditing) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        onDrag(dragAmount)
                    }
                }
            }
    ) {
        // Outer glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .blur(16.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            overlay.glowColor.copy(alpha = pulseAlpha * 0.6f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Card surface
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = overlay.glowColor,
                    spotColor = overlay.glowColor
                ),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF0A0A0A).copy(alpha = 0.85f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        brush = when (overlay.borderStyle) {
                            BorderStyle.NEON_GLOW -> Brush.linearGradient(
                                colors = listOf(
                                    overlay.glowColor.copy(alpha = pulseAlpha),
                                    overlay.glowColor.copy(alpha = 0.5f),
                                    overlay.glowColor.copy(alpha = pulseAlpha)
                                )
                            )
                            BorderStyle.HOLOGRAPHIC -> Brush.sweepGradient(
                                colors = listOf(
                                    CyberpunkCyan,
                                    CyberpunkPurple,
                                    CyberpunkPink,
                                    CyberpunkCyan
                                )
                            )
                            BorderStyle.SOLID -> Brush.linearGradient(
                                colors = listOf(overlay.glowColor, overlay.glowColor)
                            )
                            BorderStyle.GRADIENT -> Brush.linearGradient(
                                colors = listOf(
                                    overlay.glowColor,
                                    overlay.glowColor.copy(alpha = 0.3f)
                                )
                            )
                            BorderStyle.ANIMATED_GRADIENT -> Brush.sweepGradient(
                                colors = listOf(
                                    overlay.glowColor.copy(alpha = pulseAlpha),
                                    Color.Transparent,
                                    overlay.glowColor.copy(alpha = pulseAlpha)
                                )
                            )
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Icon
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("https://api.iconify.design/${overlay.iconId}.svg?color=${overlay.glowColor.value.toString(16).substring(2)}")
                            .crossfade(true)
                            .build(),
                        contentDescription = overlay.label,
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .size(48.dp * depthScale)
                            .graphicsLayer {
                                alpha = pulseAlpha
                            },
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Label
                    Text(
                        text = overlay.label,
                        fontSize = (12.sp.value * depthScale).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        letterSpacing = 1.5.sp
                    )

                    if (overlay.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = overlay.description,
                            fontSize = (8.sp.value * depthScale).sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }

                // Edit indicator
                if (overlay.isEditing) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editing",
                        tint = CyberpunkPink,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Holographic central platform (like in the mockup)
 */
@Composable
fun HolographicPlatform(
    gyroscopeX: Float = 0f,
    gyroscopeY: Float = 0f,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "platform")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .size(200.dp)
            .graphicsLayer {
                rotationX = gyroscopeY * 20f
                rotationY = gyroscopeX * 20f
                rotationZ = rotation
                scaleX = pulseScale
                scaleY = pulseScale
                cameraDistance = 12f * density
            },
        contentAlignment = Alignment.Center
    ) {
        // Outer ring
        Box(
            modifier = Modifier
                .size(200.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            CyberpunkCyan.copy(alpha = 0.8f),
                            CyberpunkPurple.copy(alpha = 0.6f),
                            CyberpunkPink.copy(alpha = 0.8f),
                            CyberpunkCyan.copy(alpha = 0.8f)
                        )
                    ),
                    shape = RoundedCornerShape(100.dp)
                )
                .blur(4.dp)
        )

        // Middle ring
        Box(
            modifier = Modifier
                .size(150.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            CyberpunkPink.copy(alpha = 0.6f),
                            CyberpunkCyan.copy(alpha = 0.4f),
                            CyberpunkPink.copy(alpha = 0.6f)
                        )
                    ),
                    shape = RoundedCornerShape(75.dp)
                )
                .blur(2.dp)
        )

        // Inner ring
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(
                    width = 1.dp,
                    color = CyberpunkCyan.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(50.dp)
                )
        )

        // Center glow
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            CyberpunkPink.copy(alpha = 0.6f),
                            CyberpunkCyan.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
        )
    }
}
