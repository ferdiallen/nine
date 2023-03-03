// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val agp_version by extra("7.4.1")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("com.android.tools.build:gradle:$agp_version")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
