plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "dduwcom.mobile.project"
    compileSdk = 33

    defaultConfig {
        applicationId = "dduwcom.mobile.project"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit with Gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // GooglePlayService Location Library
    implementation("com.google.android.gms:play-services-location:21.0.1")
    // GoogleMap
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Papago Open api
    implementation("com.android.volley:volley:1.2.1")

    // Room
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    ksp("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
}