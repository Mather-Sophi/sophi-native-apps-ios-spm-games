package io.sophi.app.repository

import io.sophi.app.models.ContentData

class ContentRepository {
    private val contents: List<ContentData> = listOf(
        ContentData(
            id = "1",
            headline = "Breaking News: Compose Navigation Arrives!",
            plainText = "Jetpack Compose now supports seamless navigation with NavController and NavHost.",
            byline = "By Compose Team"
        ),
        ContentData(
            id = "2",
            headline = "SwiftUI vs. Jetpack Compose: A Comparison",
            plainText = "Explore the differences and similarities between SwiftUI and Jetpack Compose for UI development.",
            byline = "By Dev Analyst"
        ),
        ContentData(
            id = "3",
            headline = "Kotlin Multiplatform: The Future of Mobile Apps",
            plainText = "Kotlin Multiplatform is gaining traction for cross-platform mobile development.",
            byline = "By KMP Advocate"
        )
    )

    fun getAllContent(): List<ContentData> {
        return contents
    }

    fun getOneById(id: String): ContentData? {
        return contents.find { it.id == id }
    }
}