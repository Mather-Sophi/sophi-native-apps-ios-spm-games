package io.sophi.paywall.enums


enum class ReferrerMedium(val value: String, val code: Int) {
    CAMPAIGN("campaign", 0),
    DIRECT("direct", 1),
    INTERNAL("internal", 2),
    SEARCH("search", 3),
    SOCIAL("social", 4),
    OTHER("other", 5);
}