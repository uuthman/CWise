import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mannodermaus)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.example.cwise"
    compileSdk = 35

    room {
        schemaDirectory("$projectDir/schemas")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    defaultConfig {
        applicationId = "com.example.cwise"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.cwise.HiltTestRunner"
    }
    val properties = gradleLocalProperties(rootDir,rootProject.providers)
    val apiKey = properties.getProperty("API_KEY")
    val baseUrl = properties.getProperty("BASE_URL")
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","API_KEY",apiKey)
            buildConfigField("String","BASE_URL",baseUrl)
        }

        debug {
            buildConfigField("String","API_KEY",apiKey)
            buildConfigField("String","BASE_URL",baseUrl)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assert.k)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)

    ksp(libs.hilt.compiler)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.moshi)
    ksp(libs.moshi.adapters)

    androidTestImplementation(libs.hilt.android.test)
    kspAndroidTest(libs.hilt.android.compiler)

    androidTestImplementation(libs.androidx.junit.runner)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.assert.k)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.turbine)

    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assert.k)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    ksp(libs.moshi.codegen)
}