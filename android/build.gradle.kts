plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.example.kyaracter.android"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    viewBinding.isEnabled = true

    sourceSets {
        getByName("main") {
            java.srcDir("build/generated/source/navigation-args")
        }
    }
}

dependencies {
    implementation(project(":presentation:uimodel"))
    implementation(project(":domain"))

    // todo DIするまで一時的に依存させる
    implementation(project(":infra:repository"))
    implementation(project(":infra:db"))
    implementation(project(":model"))

    // navigation
    val navVersion = "2.4.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
//    implementation("org.jetbrains.kotlinx:kotlinx-stdlib-jdk8")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
}