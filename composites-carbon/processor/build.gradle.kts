plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // Projects
    implementation(projects.compositesCarbon.core)

    // Ksp devtools
    implementation(libs.google.ksp.api)

    // KotlinPoet
    implementation(libs.kotlinpoet.ksp)
}
