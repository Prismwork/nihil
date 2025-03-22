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

group = "io.github.prismwork"
version = "0.1.0+hmcl.${property("hmcl_version")}"

base {
    archivesName.set("${property("modid")}")
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
    implementation("com.unascribed:nilloader:${property("nilloader_version")}")
    implementation("HMCL-dev:HMCL:${property("hmcl_version")}")
    implementation("org.jetbrains:annotations:24.0.0")
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
