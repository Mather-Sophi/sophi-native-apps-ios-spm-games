package io.sophi.paywall.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ContentDimensionsTest {

    @Test
    fun `ContentDimensions created with properties`() {
        val properties = mapOf(
            "section" to "news",
            "sectionCategory" to "high"
        )
        val contentDimensions = ContentDimensions(
            id = "content123",
            properties = properties
        )

        assertEquals(contentDimensions.id, "content123")
        assertEquals(contentDimensions.properties?.get("section"), "news")
        assertEquals(contentDimensions.properties?.get("sectionCategory"), "high")
    }

    @Test
    fun `ContentDimensions created without properties`() {
        val contentDimensions = ContentDimensions(id = "content456")

        assertEquals(contentDimensions.id, "content456")
        assertNull(contentDimensions.properties?.get("section"))
        assertNull(contentDimensions.properties?.get("sectionCategory"))
    }

    @Test
    fun `ContentDimensions created with partial properties`() {
        val properties = mapOf(
            "section" to "sports"
        )
        val contentDimensions = ContentDimensions(
            id = "content789",
            properties = properties
        )

        assertEquals(contentDimensions.id, "content789")
        assertEquals(contentDimensions.properties?.get("section"), "sports")
        assertNull(contentDimensions.properties?.get("sectionCategory"))
    }

}