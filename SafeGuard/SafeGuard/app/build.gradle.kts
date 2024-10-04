plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2" apply false

}

android {
    namespace = "com.example.safeguard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.safeguard"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {
    dependencies {

        implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
        implementation ("com.google.firebase:firebase-auth:23.0.0")


        implementation("com.google.firebase:firebase-analytics")


        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.firebase:firebase-firestore")
    }
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(kotlin("script-runtime"))
}