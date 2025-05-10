
# Carbon

Lightweight annotation processor ([Kotlin Symbol Processing, KSP][ksp]) for generating Route objects that help with navigation based on the [Navigation Component][navigation].
Allows you to significantly reduce routine and time spent on creating `Route` objects manually.


## Modules
* [composites-carbon-core] &mdash; Core models
* [composites-carbon-processor] &mdash; Annotation processor, parser and generator


## Roadmap

- [X] KSP `Route` objects generation
- [X] Default arguments support (without reflection)
- [ ] Serializable support
- [ ] Generate `NavGraphBuilder.composable` extension
- [ ] Arrays support
- [ ] Parcelable and Serializable support (using parcelable and serializable is not best practice. It is recommended to use primitives)
- [ ] Optional. Default arguments (with reflection)


## Using in your projects

Android only:

```groovy
dependencies {
    implementation("ru.ldralighieri.composites:composites-carbon-core:0.5.0")
    ksp("ru.ldralighieri.composites:composites-carbon-processor:0.5.0")
}
```

Multiplatform:

```groovy
kotlin {
    sourceSets {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

        dependencies {
            implementation("ru.ldralighieri.composites:composites-carbon-core:0.5.0")
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", "ru.ldralighieri.composites:composites-carbon-processor:0.5.0")
}

// https://github.com/google/ksp/issues/567
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
```

Make sure that you have `mavenCentral()` in the list of repositories:

```kotlin
repositories {
    mavenCentral()
}
```


## Example

The arguments class/object is the basis for generating the Route object:
```kotlin
@CarbonRoute(route = "composites/fiberglass", deeplinkSchema = "composites")
data class CompositesFiberglassArgs(
    @DefaultValue("Fiberglass composites") val title: String
)
```
The generator currently only supports primitives and strings as arguments.

The generated `Route` object will look as follows:
```kotlin
public object CompositesFiberglassRoute { 
    public const val route: String = "composites/fiberglass/{title}"

    public val arguments: List<NamedNavArgument> = listOf(
        navArgument("title") { 
            type = StringType
            nullable = false
            defaultValue = "Fiberglass composites"
        },
    )

    public val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink {
            uriPattern = "composites://$route"
        }
    )

  public fun create(title: String = "Fiberglass composites"): Destination.Compose = 
      Destination.Compose("composites/fiberglass/$title")

  public fun parseArguments(navBackStackEntry: NavBackStackEntry): CompositesFiberglassArgs = 
      CompositesFiberglassArgs(
          title = navBackStackEntry.savedStateHandle.get<String>("title") ?: "Fiberglass composites",
      )

  public fun parseArguments(savedStateHandle: SavedStateHandle): CompositesFiberglassArgs = 
      CompositesFiberglassArgs(
          title = savedStateHandle.get<String>("title") ?: "Fiberglass composites",
      )
}
```
The object contains the components necessary for navigation:
- `route`, `arguments` and `deeplink` are used for navigation
- `create` creates a Destination class for [navigator]
- `parseArguments` and `parseArguments` for parsing the argument class from `NavBackStackEntry` and `SavedStateHandle` respectively

If the base object contains no arguments:
```kotlin
@CarbonRoute(route = "composites")
data object CompositesArgs
```

Then the generated `Route` object will not contain `parseArguments` and `parseArguments` methods:
```kotlin
public object CompositesRoute {
    public const val route: String = "composites"
    
    public val arguments: List<NamedNavArgument> = emptyList()
    
    public val deepLinks: List<NavDeepLink> = emptyList()
    
    public fun create(): Destination.Compose = Destination.Compose("composites")
}
```

Using a simple [navigator], you can implement navigation based on the Route object:
```kotlin
val navigator: Navigator = LocalNavigator.current

LazyColumn {
    item(key = "fiberglass") {
        CompositeItem(
            title = "Fiberglass",
            onClick = {
                navigator.navigateTo(
                    CompositesFiberglassRoute.create(title = "Not a default title")
                )
            }
        )
    }
}

composable(
    route = CompositesFiberglassRoute.route,
    arguments = CompositesFiberglassRoute.arguments,
    deepLinks = CompositesFiberglassRoute.deepLinks,
) { navBackStackEntry ->
    FiberglassRootScreen(args = CompositesFiberglassRoute.parseArguments(navBackStackEntry))
}
```

A more complex example can be found in the [demo application][demo]


[ksp]: https://kotlinlang.org/docs/ksp-overview.html
[navigator]: https://github.com/LDRAlighieri/Composites/blob/master/sample/src/main/kotlin/ru/ldralighieri/composites/sample/navigation/Navigator.kt
[composites-carbon-core]: https://github.com/LDRAlighieri/Composites/tree/main/composites-carbon/core
[composites-carbon-processor]: https://github.com/LDRAlighieri/Composites/tree/main/composites-carbon/processor
[navigation]: https://developer.android.com/guide/navigation
[demo]: https://github.com/LDRAlighieri/Composites/blob/master/sample/src/main/kotlin/ru/ldralighieri/composites/sample/navigation/AppNavHost.kt
