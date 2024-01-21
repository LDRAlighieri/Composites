[![Kotlin Version](https://img.shields.io/badge/Kotlin-v1.9.22-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose BOM Version](https://img.shields.io/badge/Compose-v2023.10.01-blue.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
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
* [composites-carbon] &mdash; Annotation processor for generating Route objects that help with navigation based on the Navigation Component
* [composites-fiberglass] &mdash; A tool for building complex screens based on simple blocks.


## Current versions

| Module                                           | Version                                                                                                                                                                                                                                                    |
|--------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [composites-carbon-core][composites-carbon]      | [![Maven Central](https://img.shields.io/nexus/s/ru.ldralighieri.composites/composites-carbon-core?server=https://oss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/ru/ldralighieri/composites/composites-carbon-core/)           |
| [composites-carbon-processor][composites-carbon] | [![Maven Central](https://img.shields.io/nexus/s/ru.ldralighieri.composites/composites-carbon-processor?server=https://oss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/ru/ldralighieri/composites/composites-carbon-processor/) |
| [composites-fiberglass]                          | [![Maven Central](https://img.shields.io/maven-central/v/ru.ldralighieri.composites/composites-fiberglass.svg)](https://mvnrepository.com/artifact/ru.ldralighieri.composites/composites-fiberglass)                                                                        |


## Using in your projects

Add one or more dependencies:

```kotlin
dependencies {
    implementation("ru.ldralighieri.composites:composites-fiberglass:0.3.1")
}
```

Make sure that you have `mavenCentral()` in the list of repositories:

```kotlin
repositories {
    mavenCentral()
}
```

Snapshot build:  
[![Maven Central](https://img.shields.io/nexus/s/ru.ldralighieri.composites/composites-fiberglass?server=https://oss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/ru/ldralighieri/composites/)
```kotlin
repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
   implementation("ru.ldralighieri.composites:{module}:0.4.0-SNAPSHOT")
}
```


## If you're finding performance issues

Make sure to try running your app or sample app in [release mode][performance].


## Missed or forgot something?

If I forgot something or you have any ideas what can be added or corrected, please create an issue or contact me directly.


## License

```
Copyright 2023-2024 Vladimir Raupov

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
