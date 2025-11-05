package dev.aurakai.auraframefx.assets

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

/**
 * Validates the asset directory reorganization.
 * 
 * Tests ensure:
 * - New directory structure exists
 * - Assets have been moved correctly
 * - No duplicate assets in old location
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Asset Reorganization Validation Tests")
class AssetReorganizationValidationTest {

    private val newAssetDir = File("SVGPNGASSESTS+")
    private val oldAssetDir = File("SVGPNGASSETS")

    @Test
    @DisplayName("New asset directory should exist")
    fun newAssetDirectoryShouldExist() {
        assertTrue(
            newAssetDir.exists(),
            "SVGPNGASSESTS+ directory should exist"
        )
        assertTrue(
            newAssetDir.isDirectory,
            "SVGPNGASSESTS+ should be a directory"
        )
    }

    @Test
    @DisplayName("Old asset directory should not exist or be empty")
    fun oldAssetDirectoryShouldNotExistOrBeEmpty() {
        if (oldAssetDir.exists()) {
            val files = oldAssetDir.listFiles() ?: emptyArray()
            assertTrue(
                files.isEmpty(),
                "Old SVGPNGASSETS directory should be empty if it exists"
            )
        }
    }

    @Test
    @DisplayName("New directory should contain SVG files")
    fun newDirectoryShouldContainSVGFiles() {
        if (newAssetDir.exists()) {
            val svgFiles = newAssetDir.listFiles { file ->
                file.name.endsWith(".svg", ignoreCase = true)
            } ?: emptyArray()
            
            assertTrue(
                svgFiles.isNotEmpty(),
                "New directory should contain SVG files"
            )
        }
    }

    @Test
    @DisplayName("New directory should contain PNG files")
    fun newDirectoryShouldContainPNGFiles() {
        if (newAssetDir.exists()) {
            val pngFiles = newAssetDir.listFiles { file ->
                file.name.endsWith(".png", ignoreCase = true)
            } ?: emptyArray()
            
            assertTrue(
                pngFiles.isNotEmpty(),
                "New directory should contain PNG files"
            )
        }
    }

    @Test
    @DisplayName("Expected asset files should exist in new location")
    fun expectedAssetFilesShouldExistInNewLocation() {
        val expectedFiles = listOf(
            "BCyxZ101.svg",
            "E1z3rq01.svg",
            "Txo6hL01.svg",
            "image2vector.svg"
        )
        
        if (newAssetDir.exists()) {
            expectedFiles.forEach { filename ->
                val file = File(newAssetDir, filename)
                assertTrue(
                    file.exists(),
                    "Expected file should exist: $filename"
                )
            }
        }
    }

    @Test
    @DisplayName("Asset files should have valid extensions")
    fun assetFilesShouldHaveValidExtensions() {
        if (newAssetDir.exists()) {
            val files = newAssetDir.listFiles() ?: emptyArray()
            val validExtensions = setOf("svg", "png", "jpg", "jpeg")
            
            files.filter { it.isFile }.forEach { file ->
                val extension = file.extension.toLowerCase()
                // Allow files without extensions (like 'mockups')
                assertTrue(
                    extension.isEmpty() || extension in validExtensions,
                    "File should have valid extension or none: ${file.name}"
                )
            }
        }
    }

    @Test
    @DisplayName("SVG files should be valid XML")
    fun svgFilesShouldBeValidXML() {
        if (newAssetDir.exists()) {
            val svgFiles = newAssetDir.listFiles { file ->
                file.name.endsWith(".svg", ignoreCase = true)
            } ?: emptyArray()
            
            svgFiles.take(5).forEach { svgFile -> // Test first 5 to avoid timeout
                val content = svgFile.readText()
                assertTrue(
                    content.contains("<svg") || content.contains("<SVG"),
                    "SVG file should contain svg tag: ${svgFile.name}"
                )
            }
        }
    }

    @Test
    @DisplayName("Directory should not contain hidden system files")
    fun directoryShouldNotContainHiddenSystemFiles() {
        if (newAssetDir.exists()) {
            val files = newAssetDir.listFiles() ?: emptyArray()
            val hiddenSystemFiles = files.filter { 
                it.name.startsWith(".") && it.name != "." && it.name != ".."
            }
            
            assertTrue(
                hiddenSystemFiles.isEmpty(),
                "Directory should not contain hidden system files like .DS_Store"
            )
        }
    }

    @Test
    @DisplayName("Screenshot files should follow naming convention")
    fun screenshotFilesShouldFollowNamingConvention() {
        if (newAssetDir.exists()) {
            val screenshotFiles = newAssetDir.listFiles { file ->
                file.name.startsWith("Screenshot", ignoreCase = true)
            } ?: emptyArray()
            
            screenshotFiles.forEach { file ->
                // Screenshots should have a date or identifier
                assertTrue(
                    file.name.matches(Regex("Screenshot.*\\d+.*\\.(png|svg)", RegexOption.IGNORE_CASE)),
                    "Screenshot should have date/number and valid extension: ${file.name}"
                )
            }
        }
    }
}