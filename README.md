[![Kotlin Version](https://img.shields.io/badge/Kotlin-v2.4.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform Version](https://img.shields.io/badge/Compose_Multiplatform-v1.12.1-blue.svg?logo=jetpackcompose)](https://www.jetbrains.com/compose-multiplatform)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg)](https://developer.android.com/tools/releases/platforms?hl=ru#6.0)
[![Publish status](https://github.com/LDRAlighieri/Composites/actions/workflows/publish.yml/badge.svg)](https://github.com/LDRAlighieri/Composites/actions)

<p align="center">
<img src="https://user-images.githubusercontent.com/48987500/218184621-5bab06f6-36a6-4a22-b25f-e3f41d7bd441.png" />
</p>

# Composites (work in progress 🚧🔧️👷⛏🚧)

✨ Composites are a collection of tools and handy libraries that make it easier to use [Jetpack Compose][compose].  
Please consider giving this repository a star ⭐ if you like the project.


## Articles
* [Compose these composites][compose-these-composites]
* [Reach out to infinity][reach-out-to-infinity]


## Modules
* [composites-carbon] &mdash; Lightweight annotation processor for generating Route objects that help with navigation based on the Navigation Component
* [composites-fiberglass] &mdash; A tool for building complex screens based on simple blocks.


## Current versions

| Module                                           | Version                                                                                                                                                                                                          |
|--------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [composites-carbon-core][composites-carbon]      | [![Maven Central](https://img.shields.io/maven-central/v/ru.ldralighieri.composites/composites-carbon-core.svg)](https://mvnrepository.com/artifact/ru.ldralighieri.composites/composites-carbon-core)           |
| [composites-carbon-processor][composites-carbon] | [![Maven Central](https://img.shields.io/maven-central/v/ru.ldralighieri.composites/composites-carbon-processor.svg)](https://mvnrepository.com/artifact/ru.ldralighieri.composites/composites-carbon-processor) |
| [composites-fiberglass]                          | [![Maven Central](https://img.shields.io/maven-central/v/ru.ldralighieri.composites/composites-fiberglass.svg)](https://mvnrepository.com/artifact/ru.ldralighieri.composites/composites-fiberglass)             |


## Using in your projects

Use the Kotlin DSL instructions for your module:

* [Carbon setup and navigation examples](composites-carbon/README.md#using-in-your-projects), including the Navigation dependency and task ordering for common source generation.
* [Fiberglass setup and examples](composites-fiberglass/README.md#using-in-your-projects). Fiberglass can be used independently and does not require KSP.

Configure dependency repositories in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

### Snapshot builds

To use the development version, replace `0.6.0` with `0.7.0-SNAPSHOT` in the module instructions, keeping Carbon core and processor on the same version.
Add the [Central Portal snapshot repository](https://central.sonatype.org/publish/publish-portal-snapshots/#consuming-via-gradle) to the repositories above:

```kotlin
maven("https://central.sonatype.com/repository/maven-snapshots/") {
    content { includeGroup("ru.ldralighieri.composites") }
}
```

Snapshots are updated in place and may differ from a particular checkout.


## Run `App`:

- Android: use the `androidApp` run configuration
- iOS: use the `iosApp` run configuration
- Desktop: `./gradlew :app:desktopApp:run`
- Desktop Hot Reload: `./gradlew :app:desktopApp:hotRun`
- Web (WASM): `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`
- Web (JS): `./gradlew :app:webApp:jsBrowserDevelopmentRun` (use this to test the JS target independently)


## If you're finding performance issues

Make sure to try running your app or sample app in [release mode][performance].


## Missed or forgot something?

If I forgot something or you have any ideas what can be added or corrected, please create an issue or contact me directly.


## License

```
Copyright 2023-2025 Vladimir Raupov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[compose]: https://developer.android.com/jetpack/compose
[compose-these-composites]: https://medium.com/@ldralighieri/compose-these-composites-8ea923e4a34c
[reach-out-to-infinity]: https://medium.com/@ldralighieri/reach-out-to-infinity-bba17019a938
[composites-carbon]: https://github.com/LDRAlighieri/Composites/tree/main/composites-carbon
[composites-fiberglass]: https://github.com/LDRAlighieri/Composites/tree/main/composites-fiberglass
[performance]: https://developer.android.com/jetpack/compose/performance#build-release
