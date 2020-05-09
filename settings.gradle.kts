pluginManagement {
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
        gradlePluginPortal()
    }
}
rootProject.name="LegacyFukkit"
