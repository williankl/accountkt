package williankl.accountkt.buildSrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import williankl.accountkt.buildSrc.helpers.applyCommonPlugins
import williankl.accountkt.buildSrc.helpers.applyKotlinOptions
import williankl.accountkt.buildSrc.helpers.applyRepositories
import williankl.accountkt.buildSrc.helpers.setupMultiplatformTargets

internal class MultiplatformModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlin.multiplatform")
            plugins.apply("com.android.library")

            applyCommonPlugins()
            applyKotlinOptions()
            applyRepositories()
            setupMultiplatformTargets()
        }
    }
}