plugins {
    id(Plugins.KOTLIN_JVM)
}

dependencies {
    implementation(project(":interactors"))

    implementation(Deps.KOTLIN_STDLIB)
    implementation(Deps.KOTLIN_COROUTINES_CORE)
}
