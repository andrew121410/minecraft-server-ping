plugins {
    java
    alias(libs.plugins.freefair.lombok.plugin)
    alias(libs.plugins.shadow.plugin)
    `maven-publish`
}

group = "br.com.azalim"
version = "1.0.10"
description = "A simple library to ping Minecraft servers"

java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        url = uri("https://libraries.minecraft.net")
    }
}

dependencies {
    implementation(libs.google.gson.lib)
    implementation(libs.google.guava.lib)
    implementation(libs.dnsjava.lib)
    implementation(libs.bungeecord.api.lib)
}

tasks {
    build {
        dependsOn("shadowJar")
        dependsOn("processResources")
    }

    jar {
        enabled = false
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    shadowJar {
        // This will relocate the dependencies to avoid conflicts
        enableAutoRelocation = true
        relocationPrefix = "br.com.azalim.mcserverping.shaded"

        exclude("META-INF/**")

        archiveBaseName.set("MCServerPing")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks["shadowJar"])
        }
    }
}