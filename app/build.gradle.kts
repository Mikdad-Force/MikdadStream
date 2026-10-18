import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

val apiBaseUrl: String = (findProperty("STREAM_API_URL") as String?) ?: "https://mikdad-api.mikdadtv.workers.dev"

val appVersionCode: Int = (findProperty("VERSION_CODE") as String?)?.toIntOrNull() ?: 40
val appVersionName: String = (findProperty("VERSION_NAME") as String?) ?: "2.1.3"

val googleClientId: String = (findProperty("GOOGLE_CLIENT_ID") as String?)
    ?: "350158650454-osksv8riv4q7dfpr4ff2o509l6git6ot.apps.googleusercontent.com"

val forceTv: Boolean = (findProperty("FORCE_TV") as String?)?.toBoolean() ?: false

val updateRepo: String = (findProperty("UPDATE_REPO") as String?) ?: "Mikdad-Force/MikdadStream"

val keystoreFile = rootProject.file("mikdadstream.keystore")
val releaseStorePassword: String = (findProperty("STREAM_STORE_PASSWORD") as String?) ?: "mikdadstream"
val releaseKeyPassword: String = (findProperty("STREAM_KEY_PASSWORD") as String?) ?: "mikdadstream"
val releaseKeyAlias: String = (findProperty("STREAM_KEY_ALIAS") as String?) ?: "mikdadstream"

android {
    namespace = "com.mikdadstream.app"
    compileSdk = 36

    signingConfigs {
        create("release") {
            if (keystoreFile.exists()) {
                storeFile = keystoreFile
                storePassword = releaseStorePassword
                keyAlias = releaseKeyAlias
                keyPassword = releaseKeyPassword
            } else {
                // Fallback: generate a temp keystore via keytool so the APK is still installable
                val tempDir = File(rootProject.buildDir, "tmp-signing")
                tempDir.mkdirs()
                val ksFile = File(tempDir, "temp.keystore")
                if (!ksFile.exists()) {
                    exec {
                        commandLine(
                            "keytool",
                            "-genkeypair",
                            "-v",
                            "-keystore", ksFile.absolutePath,
                            "-alias", "temp",
                            "-keyalg", "RSA",
                            "-keysize", "2048",
                            "-validity", "10000",
                            "-storepass", "tempPass",
                            "-keypass", "tempPass",
                            "-dname", "CN=temp, OU=temp, O=temp, L=temp, ST=temp, C=US"
                        )
                    }
                }
                storeFile = ksFile
                storePassword = "tempPass"
                keyAlias = "temp"
                keyPassword = "tempPass"
            }
        }
    }

    defaultConfig {
        applicationId = "com.mikdadstream.app"
        minSdk = 24
        targetSdk = 34
        versionCode = appVersionCode
        versionName = appVersionName
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"$googleClientId\"")
        buildConfigField("Boolean", "FORCE_TV", "$forceTv")
        buildConfigField("String", "UPDATE_REPO", "\"$updateRepo\"")
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ""
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
    implementation(composeBom)
    debugImplementation(platform("androidx.compose:compose-bom:2024.12.01"))

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.navigation:navigation-compose:2.8.2")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-exoplayer-hls:1.4.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")
    implementation("androidx.media3:media3-session:1.4.1")

    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("com.google.android.gms:play-services-auth:21.2.0")

    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")

    implementation("com.google.zxing:core:3.5.3")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
}
