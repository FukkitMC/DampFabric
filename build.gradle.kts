import java.util.function.Function

buildscript {

    repositories {
        jcenter()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "FukkitMC"
            url = uri("../fukkit-repo")
        }
    }

    dependencies {
         configurations.classpath.resolutionStrategy.dependencySubstitution{
             substitute(module("io.github.fukkitmc:tiny-remapper:0.2.1.0")).with(module("io.github.fukkitmc:tiny-remapper:test-0.2.1.2"))
         }
    }

}

plugins {
    id("fabric-loom") version "gs0.2.7-SNAPSHOT"
    id("io.github.fukkitmc.crusty") version "1.1.7"
}


group = "io.github.fukkitmc"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    maven {
        name = "spigotandbukkit"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/public")
    }

    maven {
        name = "legacy-fabric"
        url = uri("https://dl.bintray.com/legacy-fabric/Legacy-Fabric-Maven")
    }

}

dependencies {
    compile("com.google.guava:guava:23.5-jre")
    minecraft("net.minecraft:minecraft:1.8.9")

    mappings(fukkit.mappings("1.8.8", "net.fabricmc:intermediary:1.8.9"))
    modImplementation("net.fabricmc:fabric-loader-1.8.9:0.8.2+build.202005040913") {
        exclude(module = "guava")
    }

    implementation("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")
    implementation("org.yaml:snakeyaml:1.26")
    implementation("org.xerial:sqlite-jdbc:3.7.2")
    implementation("mysql:mysql-connector-java:5.1.14")
    implementation("net.sf.trove4j:trove4j:3.0.3")
    implementation("net.sf.jopt-simple:jopt-simple:3.2")
    implementation("jline:jline:2.12")


}

minecraft {
    intermediaryUrl = Function {
        "https://dl.bintray.com/legacy-fabric/Legacy-Fabric-Maven/net/fabricmc/intermediary/1.8.9/intermediary-1.8.9-v2.jar"
    }

    accessWidener = File("src/main/resources/legacyfukkit.aw")

    loadDefinitions("definitions/fields.json")
    loadDefinitions("definitions/redirects.json")
}
