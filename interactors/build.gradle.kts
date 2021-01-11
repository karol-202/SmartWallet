plugins {
    id(Plugins.KOTLIN_JVM)
}

dependencies {
    api(project(":domain"))

    implementation(Deps.KOTLIN_STDLIB)
    implementation(Deps.KOTLIN_COROUTINES_CORE)
}
