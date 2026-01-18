plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.xh.androidtoolbox"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.xh.androidtoolbox"
        minSdk = 34
        targetSdk = 36
        // 建议只改 build.gradle, 每次上架 必须 +1，否则商店会拒绝上传。 保持线性递增即可，无需与 versionName 对齐。
        versionCode = 1
        // 推荐 语义化版本 主.次.修（2.3.0）。若做 灰度/测试，可加后缀 2.3.0-beta1、2.3.0.20260118。
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.8.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}