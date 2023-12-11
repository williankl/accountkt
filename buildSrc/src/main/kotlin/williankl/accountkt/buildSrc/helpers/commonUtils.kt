package williankl.accountkt.buildSrc.helpers

import com.android.build.api.dsl.VariantDimension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import org.gradle.api.JavaVersion
import org.gradle.api.tasks.compile.JavaCompile


public fun Project.setupLyricist() {
    configure<KspExtension> {
        arg("lyricist.internalVisibility", "true")
        arg("lyricist.packageName", "williankl.bpProject")
        arg("lyricist.moduleName", project.name)
    }
}

public fun Project.fromLocalProperties(key: String): String {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    return properties.getProperty(key)
}

@Suppress("IMPLICIT_CAST_TO_ANY")
public inline fun <reified ValueT> VariantDimension.buildConfigField(
    name: String,
    value: ValueT
) {
    val resolvedValue = when (value) {
        is String -> "\"$value\""
        else -> value
    }.toString()
    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
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
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs += "-Xcontext-receivers"
        }
    }
}

internal fun Project.retrieveVersionFromCatalogs(key: String): String {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    return libs.findVersion(key).get().requiredVersion
}
