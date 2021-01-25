import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.KOTLIN_JVM)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_COROUTINES_API)
    }
}

dependencies {
    implementation(project(":interactors"))

    implementation(Deps.KOTLIN_STDLIB)
    implementation(Deps.KOTLIN_COROUTINES_CORE)

    implementation(Deps.KOIN_CORE)
}
