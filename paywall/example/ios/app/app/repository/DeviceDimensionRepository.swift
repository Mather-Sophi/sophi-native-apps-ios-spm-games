import Foundation
import Paywall

class NativeDeviceDimensionRepository: DeviceDimensionRepository {
    func getAll() -> DeviceDimensions {
        return DeviceDimensions(
            hourOfDay: Int32(Calendar.current.component(.hour, from: Date())),
            os: .ios,
            type: .native,
            viewer: "sophi-example-app"
        )
    }
}
