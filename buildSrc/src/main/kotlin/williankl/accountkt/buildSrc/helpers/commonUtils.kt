package williankl.accountkt.buildSrc.helpers

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import org.gradle.api.tasks.compile.JavaCompile

public fun Project.retrieveVersionFromCatalogs(key: String): String {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    return libs.findVersion(key).get().requiredVersion
}

public fun Project.fromLocalProperties(key: String): String {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    return properties.getProperty(key)
}


internal fun Project.applyRepositories() {
    repositories.apply {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io/")
        maven("https://repo.repsy.io/mvn/chrynan/public")
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

internal fun Project.applyCommonPlugins() {
    plugins.apply("org.jmailen.kotlinter")
    plugins.apply("io.gitlab.arturbosch.detekt")
}

internal fun Project.applyKotlinOptions() {
    applyCodeSafetyFeatures(true)

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs += "-Xcontext-receivers"
        }
    }
}
