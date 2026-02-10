import Paywall
import SwiftUI

let decider: PaywallDecider? = getDecider()
@main
struct SophiApp: App {
    var body: some Scene {
        WindowGroup {
            HomeView()
        }
    }
}
