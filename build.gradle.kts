plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("xyz.wagyourtail.jvmdowngrader") version "1.2.2"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

javafx {
    version = "17"
    modules("javafx.base", "javafx.graphics", "javafx.controls")
}

val hmclVersion = "3.6.11.272"

group = "io.github.prismwork"
version = "0.1.0+hmcl.$hmclVersion"

base {
    archivesName.set("nihil")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.sleeping.town")
        content {
            includeGroup("com.unascribed")
        }
    }
    ivy {
        url = uri("https://github.com")
        patternLayout {
            artifact("[organisation]/[module]/releases/download/v[revision]/[module]-[revision].jar")
            setM2compatible(true)
        }
        metadataSources {
            artifact()
        }
    }
}

dependencies {
    implementation("com.unascribed:nilloader:1.3.4")
    implementation("HMCL-dev:HMCL:$hmclVersion")
}

tasks.processResources {
    inputs.property("version", version)

    filesMatching("nihil.nilmod.css") {
        expand("version" to inputs.properties["version"])
    }
}

tasks.jar {
    finalizedBy(tasks.downgradeJar)
    archiveClassifier = "j17"
}

tasks.assemble {
    dependsOn(tasks.shadeDowngradedApi)
}

tasks.downgradeJar {
    archiveClassifier = ""
}

tasks.shadeDowngradedApi {
    archiveClassifier = "shaded"
}
