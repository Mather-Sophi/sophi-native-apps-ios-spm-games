package io.sophi.app.models

data class ContentData(
    val id: String = java.util.UUID.randomUUID().toString(),
    val headline: String,
    val plainText: String,
    val byline: String
)
