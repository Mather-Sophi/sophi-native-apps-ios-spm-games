import Paywall
import os

let logger = Logger(
    subsystem: Bundle.main.bundleIdentifier!,
    category: "Decider"
)

func getDecider() -> PaywallDecider? {
    let deviceDimensions: DeviceDimensionRepository =
        NativeDeviceDimensionRepository()
    let deciders = PaywallDeciderRepository.companion.createNew(
        userRepository: userDimensions,
        deviceRepository: deviceDimensions
    )
    let thisDecider: PaywallDecider = deciders.getOneByHost(
        host: "test.sophi.io",
        settings: ["apiTimeoutInMilliSeconds": "1500"]
    )
    logger.debug("Decider initialized successfully")
    return thisDecider
}

func getDecision(contentId: String) async -> WallDecision? {
    guard let decider else {
        logger.error(
            "Skipping paywall decision for contentId: \(contentId) as decider is nil"
        )
        return nil
    }
    do {
        let result: WallDecision = try await decider.decide(
            contentId: contentId,
            assignedGroup: "control",
            contentProperties: nil,
            userProperties: nil
        )
        logger.debug("\(String(describing: result))")
        return result
    } catch {
        logger.error(
            "Unable to get paywall decision for contentId: \(contentId). Cause: \(error.localizedDescription)"
        )
        return nil
    }
}
