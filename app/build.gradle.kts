plugins {
    id(Plugins.ANDROID_APP)
    id(Plugins.KOTLIN_ANDROID)
}

android {
    compileSdkVersion(AndroidConf.SDK_COMPILE)

    defaultConfig {
        minSdkVersion(AndroidConf.SDK_MIN)
        targetSdkVersion(AndroidConf.SDK_TARGET)

        applicationId = AndroidConf.APPLICATION_ID
        versionCode = AndroidConf.VERSION_CODE
        versionName = AndroidConf.VERSION_NAME
    }

    buildTypes["release"].apply {
        isMinifyEnabled = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":framework"))
    implementation(project(":interactors"))
    implementation(project(":presentation"))
    implementation(project(":ui"))

    implementation(Deps.KOIN_VIEWMODEL)

    coreLibraryDesugaring(Deps.CORE_LIBRARY_DESUGARING)
}
