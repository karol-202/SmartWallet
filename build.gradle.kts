plugins {
    id(Plugins.KOTLIN_JVM) version Versions.KOTLIN apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false

    id(Plugins.KOTLIN_KAPT) version Versions.KOTLIN apply false

    id(Plugins.ANDROID_APP) version Versions.ANDROID_GRADLE_PLUGIN apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.ANDROID_GRADLE_PLUGIN apply false
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
