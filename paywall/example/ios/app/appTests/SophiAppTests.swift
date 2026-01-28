import Testing
@testable import app

struct SophiAppTests {

    @Test func decision() async throws {
        let decider = getDecider()
        let contentProperties: [String: Any] = [:]
        let userProperties: [String: Any] = [:]
        let decision = try await decider?.decide(contentId: "1234", assignedGroup: "control", contentProperties: contentProperties, userProperties: userProperties)

        #expect(decision != nil)
        // Write your test here and use APIs like `#expect(...)` to check expected conditions.
    }

}
