import Paywall

func getDecider() -> PaywallDecider?{
    do{
        let userDimensions: UserDimensionRepository = NativeUserDimensionRepository()
        let deviceDimensions: DeviceDimensionRepository = NativeDeviceDimensionRepository()
        let deciders = PaywallDeciderRepository.companion.createNew(userRepository: userDimensions, deviceRepository: deviceDimensions)
        let thisDecider: PaywallDecider = deciders.getOneByHost(
            host: "www.statesman.com",
            settings: ["apiTimeoutInMilliSeconds": "200"]
        )
        return thisDecider
    } catch let error {
        print("Failed to create Paywall Decider for {host} with {settings}", error.localizedDescription)
    }
    return nil
    
}
