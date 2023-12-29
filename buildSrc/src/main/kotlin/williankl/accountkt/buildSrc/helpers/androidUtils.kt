package williankl.accountkt.buildSrc.helpers

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

public fun Project.applyAndroidCompose(){
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val composeCompilerVersion = libs.findVersion("android-compose-compiler").get().requiredVersion

    configure<BaseExtension> {
        buildFeatures.apply {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = composeCompilerVersion
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
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}

internal fun Project.findAndroidExtension(): BaseExtension =
    extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<com.android.build.gradle.AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")