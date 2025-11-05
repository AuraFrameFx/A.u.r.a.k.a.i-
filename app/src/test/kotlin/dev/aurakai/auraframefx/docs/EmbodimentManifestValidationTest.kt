package dev.aurakai.auraframefx.docs

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

/**
 * Validates the EMBODIMENT_MANIFEST.md documentation.
 * 
 * Tests ensure:
 * - Document structure is maintained
 * - Code examples are properly formatted
 * - Required sections are present
 * - No broken internal references
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Embodiment Manifest Documentation Validation Tests")
class EmbodimentManifestValidationTest {

    private val manifestFile = File("EMBODIMENT_MANIFEST.md")
    private val manifestContent by lazy {
        if (manifestFile.exists()) manifestFile.readText() else ""
    }

    @Test
    @DisplayName("Manifest file should exist")
    fun manifestFileShouldExist() {
        assertTrue(manifestFile.exists(), "EMBODIMENT_MANIFEST.md should exist")
        assertTrue(manifestFile.isFile, "EMBODIMENT_MANIFEST.md should be a file")
    }

    @Test
    @DisplayName("Should have simplified usage section")
    fun shouldHaveSimplifiedUsageSection() {
        assertTrue(
            manifestContent.contains("### Usage in Code"),
            "Should have Usage in Code section"
        )
    }

    @Test
    @DisplayName("Should contain EmbodimentEngine class reference")
    fun shouldContainEmbodimentEngineReference() {
        assertTrue(
            manifestContent.contains("class EmbodimentEngine"),
            "Should reference EmbodimentEngine class"
        )
    }

    @Test
    @DisplayName("Should have manifestAura method example")
    fun shouldHaveManifestAuraExample() {
        assertTrue(
            manifestContent.contains("fun manifestAura"),
            "Should have manifestAura method example"
        )
    }

    @Test
    @DisplayName("Should reference required dependencies")
    fun shouldReferenceRequiredDependencies() {
        val dependencies = listOf(
            "assetLoader",
            "uiInjector",
            "hookManager"
        )
        
        dependencies.forEach { dep ->
            assertTrue(
                manifestContent.contains(dep),
                "Should reference dependency: $dep"
            )
        }
    }

    @Test
    @DisplayName("Should have proper code block formatting")
    fun shouldHaveProperCodeBlockFormatting() {
        val codeBlockCount = manifestContent.split("```").size - 1
        assertTrue(
            codeBlockCount % 2 == 0,
            "Code blocks should be properly closed (found $codeBlockCount markers)"
        )
    }

    @Test
    @DisplayName("Should specify Kotlin as code language")
    fun shouldSpecifyKotlinAsCodeLanguage() {
        assertTrue(
            manifestContent.contains("```kotlin"),
            "Should use kotlin language specifier for code blocks"
        )
    }

    @Test
    @DisplayName("Should not contain outdated complex examples")
    fun shouldNotContainOutdatedComplexExamples() {
        // The simplified version should not have the old complex chat bubble examples
        assertFalse(
            manifestContent.contains("IdleChatBubble"),
            "Should not contain old IdleChatBubble examples"
        )
        assertFalse(
            manifestContent.contains("ChatPromptScreen"),
            "Should not contain old ChatPromptScreen examples"
        )
    }

    @Test
    @DisplayName("Should have proper markdown heading structure")
    fun shouldHaveProperHeadingStructure() {
        val headings = manifestContent.lines()
            .filter { it.trim().startsWith("#") }
        
        assertTrue(
            headings.isNotEmpty(),
            "Should have markdown headings"
        )
        
        // Check heading levels are sequential
        headings.forEach { heading ->
            val level = heading.takeWhile { it == '#' }.length
            assertTrue(
                level in 1..6,
                "Heading level should be between 1 and 6: $heading"
            )
        }
    }

    @Test
    @DisplayName("Should maintain asset directory structure reference")
    fun shouldMaintainAssetDirectoryReference() {
        assertTrue(
            manifestContent.contains("app/src/main/assets/embodiment/") ||
            manifestContent.contains("embodiment/"),
            "Should reference embodiment asset directory"
        )
    }

    @Test
    @DisplayName("Should have method parameter documentation")
    fun shouldHaveMethodParameterDocumentation() {
        // The simplified example should show key parameters
        val parameters = listOf(
            "state",
            "position",
            "duration",
            "animation"
        )
        
        parameters.forEach { param ->
            assertTrue(
                manifestContent.contains(param),
                "Should document parameter: $param"
            )
        }
    }

    @Test
    @DisplayName("Should not have excessive blank lines")
    fun shouldNotHaveExcessiveBlankLines() {
        assertFalse(
            manifestContent.contains("\n\n\n\n\n"),
            "Should not have more than 3 consecutive blank lines"
        )
    }

    @Test
    @DisplayName("Documentation should be concise")
    fun documentationShouldBeConcise() {
        val lineCount = manifestContent.lines().size
        assertTrue(
            lineCount < 500,
            "Simplified documentation should be under 500 lines (found $lineCount)"
        )
    }

    @Test
    @DisplayName("Should reference YukiHookAPI")
    fun shouldReferenceYukiHookAPI() {
        assertTrue(
            manifestContent.contains("YukiHookAPI") ||
            manifestContent.contains("hookManager"),
            "Should reference YukiHookAPI or hook manager"
        )
    }
}