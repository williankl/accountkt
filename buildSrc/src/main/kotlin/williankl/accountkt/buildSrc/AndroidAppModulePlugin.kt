package williankl.accountkt.buildSrc

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import williankl.accountkt.buildSrc.helpers.applyCommonPlugins
import williankl.accountkt.buildSrc.helpers.applyKotlinOptions
import williankl.accountkt.buildSrc.helpers.applyRepositories
import williankl.accountkt.buildSrc.helpers.retrieveVersionFromCatalogs
import williankl.accountkt.buildSrc.helpers.setupAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jmailen.gradle.kotlinter.KotlinterExtension

internal class AndroidAppModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("com.android.application")
            plugins.apply("org.jetbrains.kotlin.android")

            applyCommonPlugins()
            applyKotlinOptions()
            applyRepositories()
            setupAndroidApp()
            setupAndroid()
        }
    }

    private fun Project.setupAndroidApp() {
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
                applicationId = "williankl.accountkt"
                versionCode = retrieveVersionFromCatalogs("android-versionCode").toInt()
                versionName = retrieveVersionFromCatalogs("android-versionName")
            }
        }
    }
}