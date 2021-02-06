plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
}

android {
    compileSdkVersion(AndroidConf.SDK_COMPILE)

    defaultConfig {
        minSdkVersion(AndroidConf.SDK_MIN)
        targetSdkVersion(AndroidConf.SDK_TARGET)
    }
}
