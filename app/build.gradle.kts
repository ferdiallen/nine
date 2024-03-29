import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.8.10"
}

android {
    val baseUrlKey = gradleLocalProperties(rootDir).getProperty("BASE_URL")
    compileSdk = 33
    namespace = "com.example.nineintelligence"
    defaultConfig {
        multiDexEnabled = true
        applicationId = "com.example.nineintelligence"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "0.95"
        buildConfigField("String", "BASE_URL", baseUrlKey)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    //noinspection GradleDependency
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.compose.material:material-icons-extended")

    //Accompanist
    val accompanistVersion = "0.28.0"
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-webview:$accompanistVersion")

    //Koin
    val koinCoreVer = "3.3.3"
    val koinAndroid = "3.3.3"
    val koinCompose = "3.4.2"
    val koinKtor = "3.3.1"

    implementation("io.insert-koin:koin-core:$koinCoreVer")
    implementation("io.insert-koin:koin-android:$koinAndroid")
    implementation("io.insert-koin:koin-androidx-compose:$koinCompose")
    implementation("io.insert-koin:koin-ktor:$koinKtor")
    implementation("io.insert-koin:koin-androidx-workmanager:$koinAndroid")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    val materialDialog = "0.6.2"
    implementation("com.marosseleng.android:compose-material3-datetime-pickers:$materialDialog")

    //Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    //Multidex
    val multidex_version = "2.0.1"
    implementation("androidx.multidex:multidex:$multidex_version")

    //Media 3
    val media3_version = "1.0.1"
    // For media playback using ExoPlayer
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    // For building media playback UIs
    implementation("androidx.media3:media3-ui:$media3_version")

    //Ktor
    val ktorVersion = "2.3.0"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")

    //Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //CanaryLeak
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")

    //Immutable List
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
}