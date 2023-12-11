package williankl.accountkt.buildSrc.helpers

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.SourceTask
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.KotlinterExtension
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

internal fun Project.applyCodeSafetyFeatures(activated: Boolean) {
    applyKotlinter(activated = activated)
    applyStrictApi(activated = activated)
    applyDetekt(activated = activated)
}

internal fun Project.applyKotlinter(activated: Boolean) {
    configure<KotlinterExtension> {
        ignoreFailures = activated.not()
        reporters = arrayOf("checkstyle", "plain")
    }

    fun SourceTask.applyKspTaskDependency() {
        source = (source - fileTree(buildDir)).asFileTree
        val kspTaskExists = project.tasks.findByName("kspCommonMainKotlinMetadata") != null
        if (name != "kspCommonMainKotlinMetadata" && kspTaskExists) {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }

    tasks.withType<SourceTask>().forEach { sourceTask ->
        sourceTask.applyKspTaskDependency()
    }
}

internal fun Project.applyStrictApi(activated: Boolean) {
    kotlinExtension.apply {
        explicitApi()
        explicitApi = if (activated) ExplicitApiMode.Strict else ExplicitApiMode.Disabled
    }
}

internal fun Project.applyDetekt(activated: Boolean) {
    configure<DetektExtension> {
        buildUponDefaultConfig = activated
        ignoreFailures = activated.not()
        config = files("$rootDir/config/default-detekt.yml")
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            txt.required.set(true)
        }
    }
}