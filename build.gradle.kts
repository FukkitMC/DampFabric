import io.github.fukkitmc.gloom.definitions.ClassDefinition
import java.util.function.Function

plugins {
    id("fabric-loom") version "g0.2.7-20200411.095632-3"
    id("io.github.fukkitmc.crusty") version "1.0.0-legacy"
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
    minecraft("com.mojang:minecraft:1.8.9")

    mappings(fukkit.mappings("1.8.8", "1.8.9"))
    modImplementation("net.fabricmc:fabric-loader-1.8.9:0.8.2+build.202004210808") {
        exclude(module = "guava")
    }

	compile("org.spigotmc", "spigot-api", "1.8.8-R0.1-SNAPSHOT")
	compile("org.yaml", "snakeyaml", "1.26")

	implementation("net.sf.trove4j:trove4j:3.0.3")
	implementation("net.sf.jopt-simple:jopt-simple:3.2")
	implementation("jline:jline:2.12")

}

minecraft {
	intermediaryUrl = Function {
		"https://dl.bintray.com/legacy-fabric/Legacy-Fabric-Maven/net/fabricmc/intermediary/1.8.9/intermediary-1.8.9-v2.jar"
    }

//	loadDefinitions("definitions/access.json")
//	loadDefinitions("definitions/access_extra.json")
//	loadDefinitions("definitions/definitions.json")
	loadDefinitions("definitions/redirects.json")

    // Automatically add extras
    for (f in File("src/main/java/io/github/fukkitmc/dampfabric/extra").list() ?: return@minecraft) {
        val file = f.substring(0, f.length - 10)
        addDefinitions(ClassDefinition("net/minecraft/server/$file", setOf("io/github/fukkitmc/dampfabric/extra/${file}Extra"), setOf(), setOf(), setOf(), setOf(), setOf()))
    }
}
