plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
}

android {
    compileSdkVersion(AndroidConf.SDK_COMPILE)

    defaultConfig {
        minSdkVersion(AndroidConf.SDK_MIN)
        targetSdkVersion(AndroidConf.SDK_TARGET)
    }

    kapt {
        arguments {
            arg("room.incremental", true)
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_COROUTINES_API)
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":res"))

    implementation(Deps.KOTLIN_STDLIB)

    implementation(Deps.ANDROID_ROOM_KTX)
    kapt(Deps.ANDROID_ROOM_COMPILER)

    implementation(Deps.ANDROID_PREFERENCE)

    implementation(Deps.KOIN_VIEWMODEL)

    coreLibraryDesugaring(Deps.CORE_LIBRARY_DESUGARING)
}
