import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

allprojects {
    group = "org.tatrman.esotools"
    version = "0.0.1"

    repositories {
        mavenCentral()
        maven("https://jitpack.io") 
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    val libs = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")
    
    dependencies {
        "implementation"(libs.findLibrary("kotlin-stdlib").get())
        "implementation"(libs.findLibrary("kotlinx-coroutines").get())
        
        "testImplementation"(libs.findLibrary("kotlin-test").get())
        "testImplementation"(libs.findLibrary("kotest-runner-junit5").get())
        "testImplementation"(libs.findLibrary("kotest-assertions-core").get())
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
    
    // Configure Kotlin Toolchain
    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }
}
