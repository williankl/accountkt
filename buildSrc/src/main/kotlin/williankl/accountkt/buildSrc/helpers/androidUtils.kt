package williankl.accountkt.buildSrc.helpers

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType

internal fun Project.setupAndroidApp(appName: String) {
    configure<BaseExtension> {
        buildTypes.getByName("debug").apply {
            applicationIdSuffix = ".stg"
        }

        buildTypes.getByName("release").apply {
            minifyEnabled(true)
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }

        defaultConfig {
            applicationId = "williankl.$appName"
            versionCode = retrieveVersionFromCatalogs("versionCode-$appName").toInt()
            versionName = retrieveVersionFromCatalogs("versionName-$appName")
        }
    }
}

internal fun Project.setupAndroid(){
    configure<BaseExtension> {
        val (compileSdkVersion, targetSdkVersion, minSdkVersion) = Triple(
            retrieveVersionFromCatalogs("android-compileSdk").toInt(),
            retrieveVersionFromCatalogs("android-targetSdk").toInt(),
            retrieveVersionFromCatalogs("android-minSdk").toInt(),
        )

        compileSdkVersion(compileSdkVersion)

        viewBinding {
            isEnabled = true
        }

        defaultConfig{
            minSdk = minSdkVersion
            targetSdk = targetSdkVersion
            vectorDrawables.useSupportLibrary = true
            renderscriptTargetApi = targetSdkVersion
            renderscriptSupportModeEnabled = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}

internal fun Project.findAndroidExtension(): BaseExtension =
    extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<com.android.build.gradle.AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")