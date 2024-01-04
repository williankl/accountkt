# KashKt
## About
This app is a simple solution to checking the currency value against any other currency. These values are updated daily, and use this [Exchange api](https://www.exchangerate-api.com/) as source of truth.  
The main reason this app was created was to study the Kotlin Multiplatform feature, which builds Kotlin native code into some targets, such as iOS and Android.

## Multiplatform Tech Stack
- [Jetpack Compose](https://github.com/JetBrains/compose-multiplatform) -> UI building
- [Voyager](https://github.com/adrielcafe/voyager) -> Navigation and Bottom sheet handling
- [Lyricist](https://github.com/adrielcafe/lyricist) -> Localization handling
- [Ktor Client](https://ktor.io/docs/welcome.html) -> Networking in general
- [CashApp SqlDelight](https://github.com/cashapp/sqldelight) -> Internal storage using SQLite
- [Kodein](https://github.com/kosi-libs/Kodein) -> Dependency injection framework
- [Kamel](https://github.com/Kamel-Media/Kamel) -> Async media loading
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) -> Serializing models
- [Kotlin Datetime](https://github.com/Kotlin/kotlinx-datetime) -> Handling timestamps and date differences
- [Moko Resources](https://github.com/icerockdev/moko-resources) -> Common resource handling (soon to be deprecated, hopefully)
- [Compose Icons](https://github.com/DevSrSouza/compose-icons) -> To use some different icons from the default ones

## Structure
The module structure and logic is as follows:
```
app
 - android: base android application code. Should have near to none logic, only initializations, such as the MainActivity and the Application classes.
 - ios: same as the android initialization module.
data
 - currency-service: this is the service that fetches the information from the api, it builds it's own networking client as there is no need to a more complex one.
 - currency-storage: uses SQL delight to build an interface for the internal storage as is needed by the application.
features
 - currency-feature: uses the services implemented at the data module to link the information coming from the api with the internal storage, providing a single source of truth to the classes using it.
 - shared-preferences: build an interface exposing the user preference to simpler things, that should not need to be stored in a proper internal storage feature.
ui
 - application: the whole application design, holding the user interface itself and the viewmodels that will use the features built in the features module.
 - design
 - - core: holds the core components for the application design system, such as text components, colors, etc...
 - - components: more complex components built to a specific use-case in for the app's UI.
```
## How can you test it?
. For both platform you will need a file called `local.properties` on the base project directory with the following values added:
1. `apiKey={your-api-key-here}` -> the key should come [from this api](https://www.exchangerate-api.com/)
2. `sdk.dir={your-android-sdk-location-here}`

. For Android you can simply clone it into AndroidStudio and run the application under the `:app:android` module. If for some reason your IDE generate a project `build.gradle` file, be sure to delete it.

. For iOS you will need to have a MacOS platform, where you can build the project through xCode. The xCode project that should be build is at `/app/ios` directory. If you would rather do it through the AndroidStudio, there is this [Plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) that can build the project within it, but a MacOS system is still required.

## Google play | App store
You can check the application on the following links:
1. [Google play](https://play.google.com/store/apps/details?id=williankl.accountkt)
2. App store - To be published
