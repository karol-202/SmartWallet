plugins {
    id(Plugins.KOTLIN_JVM) version Versions.KOTLIN apply false
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
