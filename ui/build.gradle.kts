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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Versions.KOTLIN
        kotlinCompilerExtensionVersion = Versions.ANDROID_COMPOSE
    }
}

dependencies {
    implementation(project(":presentation"))

    implementation(Deps.KOTLIN_STDLIB)

    implementation(Deps.ANDROID_COMPOSE_UI)
    implementation(Deps.ANDROID_COMPOSE_UI_TOOLING)
    implementation(Deps.ANDROID_COMPOSE_FOUNDATION)
    implementation(Deps.ANDROID_COMPOSE_MATERIAL)
    implementation(Deps.ANDROID_COMPOSE_MATERIAL_ICONS_CORE)

    implementation(Deps.KOIN_VIEWMODEL)
}
