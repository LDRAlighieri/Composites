
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

Android only (`com.android.application` or `com.android.library`):

```kotlin
dependencies {
    implementation("ru.ldralighieri.composites:composites-carbon-core:0.6.0")
    ksp("ru.ldralighieri.composites:composites-carbon-processor:0.6.0")
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")
}
```

Multiplatform, with argument models in `commonMain`:

```kotlin
kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation("ru.ldralighieri.composites:composites-carbon-core:0.6.0")
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", "ru.ldralighieri.composites:composites-carbon-processor:0.6.0")
}

// https://github.com/google/ksp/issues/567
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

// KSP tasks also read the generated common sources and are not Kotlin compilation tasks.
tasks.withType<com.google.devtools.ksp.gradle.KspAATask>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
```

Make sure that you have `mavenCentral()` in the list of repositories:

```groovy
repositories {
    mavenCentral()
}
```


## Example

The arguments class/object is the basis for generating the Route object:
```kotlin
import ru.ldralighieri.composites.carbon.core.CarbonRoute
import ru.ldralighieri.composites.carbon.core.DefaultValue

@CarbonRoute(route = "composites/fiberglass", deeplinkSchema = "composites")
data class CompositesFiberglassArgs(
    @DefaultValue("Fiberglass") val title: String
)
```

The generated `Route` object is placed in the same package (imports omitted below):
```kotlin
public object CompositesFiberglassRoute { 
    public const val route: String = "composites/fiberglass/{title}"

    public val arguments: List<NamedNavArgument> = listOf(
        navArgument("title") { 
            type = StringType
            nullable = false
            defaultValue = "Fiberglass"
        },
    )

    public val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink {
            uriPattern = "composites://$route"
        }
    )

  public fun create(title: String = "Fiberglass"): Destination.Compose =
      Destination.Compose("composites/fiberglass/$title")

  public fun parseArguments(navBackStackEntry: NavBackStackEntry): CompositesFiberglassArgs = 
      CompositesFiberglassArgs(
          title = navBackStackEntry.savedStateHandle.get<String>("title") ?: "Fiberglass",
      )

  public fun parseArguments(savedStateHandle: SavedStateHandle): CompositesFiberglassArgs = 
      CompositesFiberglassArgs(
          title = savedStateHandle.get<String>("title") ?: "Fiberglass",
      )
}
```
The object contains the components necessary for navigation:
- `route`, `arguments` and `deepLinks` configure a destination in the navigation graph
- `create()` returns a `Destination.Compose` whose `route` can be passed to `NavController.navigate()` or the demo's [navigator]
- The two `parseArguments()` overloads reconstruct the argument model from `NavBackStackEntry` or `SavedStateHandle`

`deeplinkSchema = "composites"` generates `composites://composites/fiberglass/{title}`.
Register `deepLinks` in the navigation graph. External Android links also require a matching `VIEW` intent filter in the manifest; the demo's filters are currently commented out, so its generated patterns alone do not enable external launches.

If the base object contains no arguments:
```kotlin
@CarbonRoute(route = "composites")
data object CompositesArgs
```

Then the generated `Route` object will not contain either `parseArguments()` overload:
```kotlin
public object CompositesRoute {
    public const val route: String = "composites"
    
    public val arguments: List<NamedNavArgument> = emptyList()
    
    public val deepLinks: List<NavDeepLink> = emptyList()
    
    public fun create(): Destination.Compose = Destination.Compose("composites")
}
```

This composable registers both routes using Navigation Compose and Compose Material (`org.jetbrains.compose.material:material:1.12.1`):
```kotlin
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CarbonDemo() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CompositesRoute.route) {
        composable(route = CompositesRoute.route) {
            Button(
                onClick = {
                    navController.navigate(
                        CompositesFiberglassRoute.create(title = "Fiberglass").route
                    )
                }
            ) {
                Text("Fiberglass")
            }
        }
        composable(
            route = CompositesFiberglassRoute.route,
            arguments = CompositesFiberglassRoute.arguments,
            deepLinks = CompositesFiberglassRoute.deepLinks,
        ) { navBackStackEntry ->
            val args = CompositesFiberglassRoute.parseArguments(navBackStackEntry)
            Text(args.title)
        }
    }
}
```

A more complex example can be found in the [demo application][demo]


[ksp]: https://kotlinlang.org/docs/ksp-overview.html
[navigator]: https://github.com/LDRAlighieri/Composites/blob/main/shared/src/commonMain/kotlin/ru/ldralighieri/composites/shared/navigation/Navigator.kt
[composites-carbon-core]: https://github.com/LDRAlighieri/Composites/tree/main/composites-carbon/core
[composites-carbon-processor]: https://github.com/LDRAlighieri/Composites/tree/main/composites-carbon/processor
[navigation]: https://developer.android.com/guide/navigation
[demo]: https://github.com/LDRAlighieri/Composites/blob/main/shared/src/commonMain/kotlin/ru/ldralighieri/composites/shared/navigation/AppNavHost.kt
