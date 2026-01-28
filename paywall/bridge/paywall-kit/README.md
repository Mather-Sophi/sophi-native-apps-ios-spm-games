# sophi-react-native-paywall-kit

The sophi-react-native-paywall-kit is a Paywall SDK designed for native applications. It is implemented using React Native's TurboModules to allow integration with both iOS and Android platforms.

## Using a GitHub Personal Access Token

Create a Personal Access Token (if you don't have one):

Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic). Generate a new token with read:packages scope

You can refer to the [documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-npm-registry#authenticating-with-a-personal-access-token) on how to authenticate using a personal access token. The `NAMESPACE` referred to in the document will be `Mather-Sophi`

In the same directory as your package.json file, create or edit an .npmrc file to include the line below specifying the GitHub Packages URL and the namespace.

```
@mather-sophi:registry=https://npm.pkg.github.com/
```

## Installation

Install the package using the `--save` flag.

```sh
npm install @mather-sophi/sophi-react-native-paywall-kit --save
```

### iOS Setup:

Run the following command to install CocoaPods dependencies under your ios folder:
```bash
cd ios && pod install
```

### Android Setup:

Ensure your `build.gradle` includes the required repositories to install android dependencies.

Add your GitHub Personal Access token under `gradle.properties`
```ini
# ~/.gradle/gradle.properties
gpr.user=GITHUB_USERNAME
gpr.key=GITHUB_PERSONAL_ACCESS_TOKEN
```

Update `build.gradle`
```groovy
// app/android/build.gradle
repositories {
  maven {
    name = "GitHubPackages"
    url = uri("https://maven.pkg.github.com/Mather-Sophi/io-sophi-paywall-native")
    credentials {
      username = project.findProperty("gpr.user") as String?: System.getenv("GITHUB_ACTOR")
      password = project.findProperty("gpr.key") as String?: System.getenv("GITHUB_TOKEN")
    }
  }
}
```

## Usage


Import the package:
```js
import { PaywallDeciderRepository, UserDimensionRepository, DeviceDimensionRepository } from "@mather-sophi/sophi-react-native-paywall-kit";
```
Implement the `UserDimensionRepository` and `DeviceDimensionRepository`:
```js
class NativeDeviceDimensionRepository implements DeviceDimensionRepository {
  getAll(): DeviceDimensions {
    // implement
  }
}

class NativeUserDimensionRepository implements UserDimensionRepository {
  getAll(): UserDimensions {
    // implement
  }
}


const userDimensionRepository = new NativeUserDimensionRepository();
const deviceDimensionRepository = new NativeDeviceDimensionRepository();
```

Initialize the `PaywallDeciderRepository` using user and device dimenstions and get the decider using the `getOneByHost` method
```js
const repository = PaywallDeciderRepository.createNew(userDimensionsRepository, deviceDimensionsRepository);
const decider = repository.getOneByHost('www.example.com', { apiTimeout: 'value' });
```

To get the decision call the `decider.decide` method

```js
const decision = decider.decide('content-123', 'control');
console.log('Paywall Decision:', decision);
```
