plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.compose.compiler)
}

val versionNum: String? by project
val versionMajor = properties["multiplatformkickstarter.version.major"].toString().toInt()
val versionMinor = properties["multiplatformkickstarter.version.minor"].toString().toInt()
val versionPatch = properties["multiplatformkickstarter.version.patch"].toString().toInt()

fun versionCode(): Int {
    versionNum?.let {
        return (versionMajor * 1000000) + (versionMinor * 1000) + it.toInt()
    } ?: return 1
}

android {
    namespace = "com.multiplatformkickstarter.app.android"
    compileSdk = properties["multiplatformkickstarter.android.compileSdk"].toString().toInt()
    defaultConfig {
        applicationId = "com.multiplatformkickstarter.app.android"
        minSdk = properties["multiplatformkickstarter.android.minSdk"].toString().toInt()
        targetSdk = properties["multiplatformkickstarter.android.targetSdk"].toString().toInt()
        versionCode = versionCode()
        versionName = "$versionMajor.$versionMinor.$versionPatch"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.tracing.ktx)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(libs.androidx.ui.tooling)
}
