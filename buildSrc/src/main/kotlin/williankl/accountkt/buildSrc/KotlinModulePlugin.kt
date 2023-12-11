package williankl.accountkt.buildSrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import williankl.accountkt.buildSrc.helpers.applyCommonPlugins
import williankl.accountkt.buildSrc.helpers.applyKotlinOptions
import williankl.accountkt.buildSrc.helpers.applyRepositories

internal class KotlinModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlin.jvm")

            applyCommonPlugins()
            applyKotlinOptions()
            applyRepositories()
        }
    }
}