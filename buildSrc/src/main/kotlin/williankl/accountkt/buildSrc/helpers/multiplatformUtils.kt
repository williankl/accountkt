package williankl.accountkt.buildSrc.helpers

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import williankl.accountkt.buildSrc.helpers.findAndroidExtension
import williankl.accountkt.buildSrc.helpers.setupAndroid

public fun Project.addJvmTarget() {
    setupMultiplatformTargets(withJvm = true)
}

public fun DependencyHandlerScope.commonMainLyricistImplementation(
    lyricistDependency: Provider<MinimalExternalModuleDependency>
) {
    val actualDependency = lyricistDependency.get()
    add(
        configurationName = "kspCommonMainMetadata",
        dependencyNotation = "${actualDependency.module}:${actualDependency.version}"
    )
}

public fun Project.applyMultiModuleKsp(packageName: String) {
    configure<KspExtension> {
        arg("lyricist.packageName", packageName)
        arg("lyricist.moduleName", project.name)
        arg("lyricist.internalVisibility", "true")
    }
}

public fun Project.applyCommonMainCodeGeneration() {
    tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }

    configure<KotlinMultiplatformExtension> {
        sourceSets.getByName("commonMain") {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
}

internal fun Project.setupMultiplatformTargets(withJvm: Boolean = false) {
    if (withJvm) applyJvmTarget()
    applyAndroidTarget()
    applyIOSTarget()
    setDependencies(withJvm)
}

private fun Project.applyAndroidTarget() {
    findAndroidExtension().apply {
        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        setupAndroid()
    }

    extensions.configure<KotlinMultiplatformExtension>() {
        androidTarget()
    }
}

private fun Project.applyJvmTarget() {
    extensions.configure<KotlinMultiplatformExtension>() {
        jvm("jvm")
    }
}

private fun Project.applyIOSTarget() {
    extensions.configure<KotlinMultiplatformExtension>() {
        applyDefaultHierarchyTemplate()
        iosX64()
        iosArm64()
    }
}

private fun Project.setDependencies(withJvm: Boolean) {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets {
            val commonMain by getting

            if (withJvm) {
                val jvmMain by getting {
                    dependsOn(commonMain)
                }
            }

            val androidMain by getting {
                dependsOn(commonMain)
            }

            val iosMain by getting {
                dependsOn(commonMain)
            }
        }
    }
}