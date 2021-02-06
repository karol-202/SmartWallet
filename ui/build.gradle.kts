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

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        freeCompilerArgs = listOf(OptIn.EXPERIMENTAL_COROUTINES_API)
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.ANDROID_COMPOSE
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":res"))

    implementation(Deps.KOTLIN_STDLIB)

    implementation(Deps.ANDROID_COMPOSE_UI)
    implementation(Deps.ANDROID_COMPOSE_UI_TOOLING)
    implementation(Deps.ANDROID_COMPOSE_FOUNDATION)
    implementation(Deps.ANDROID_COMPOSE_MATERIAL)
    implementation(Deps.ANDROID_COMPOSE_MATERIAL_ICONS_EXTENDED)

    implementation(Deps.ANDROID_NAVIGATION_COMPOSE)

    implementation(Deps.KOIN_COMPOSE)

    implementation(Deps.SEALED_ENUM)
    kapt(Deps.SEALED_ENUM_COMPILER)

    implementation(Deps.COMPOSE_MATERIAL_DIALOGS_DATETIME)

    coreLibraryDesugaring(Deps.CORE_LIBRARY_DESUGARING)
}
