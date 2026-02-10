import Foundation
import Paywall

struct ContentData: Identifiable {
    let id: String
    let headline: String
    let byline: String
    let plainText: String
    let imageUrl: String?

    init(
        id: String,
        headline: String,
        byline: String,
        plainText: String,
        imageUrl: String?,
        decide: Bool = false
    ) {
        self.id = id
        self.headline = headline
        self.byline = byline
        self.plainText = plainText
        self.imageUrl = imageUrl
    }

}
