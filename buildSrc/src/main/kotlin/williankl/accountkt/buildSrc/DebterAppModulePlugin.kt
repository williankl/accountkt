package williankl.accountkt.buildSrc

import williankl.accountkt.buildSrc.helpers.applyCommonPlugins
import williankl.accountkt.buildSrc.helpers.applyKotlinOptions
import williankl.accountkt.buildSrc.helpers.applyRepositories
import williankl.accountkt.buildSrc.helpers.setupAndroid
import williankl.accountkt.buildSrc.helpers.setupAndroidApp
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class DebterAppModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("com.android.application")
            plugins.apply("org.jetbrains.kotlin.android")

            applyCommonPlugins()
            applyKotlinOptions()
            applyRepositories()
            setupAndroidApp("debter")
            setupAndroid()
        }
    }
}