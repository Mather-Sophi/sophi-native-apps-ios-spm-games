package io.sophi.paywall.enums

enum class ReferrerSource(val value: String, val code: Int) {
    GOOGLE("google", 0),
    YAHOO("yahoo", 1),
    DUCKDUCKGO("duckduckgo", 2),
    BING("bing", 3),
    FACEBOOK("facebook", 4),
    INSTAGRAM("instagram", 5),
    X("x", 6),
    T("t", 6),
    LINKEDIN("linkedin", 7),
    REDDIT("reddit", 8),
    NEWSLETTER("newsletter", 9);
}