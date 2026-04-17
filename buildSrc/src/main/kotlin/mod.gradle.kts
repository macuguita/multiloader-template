plugins {
	idea
	id("net.fabricmc.fabric-loom")
}

tasks.withType<Wrapper> {
	gradleVersion = "9.4.0"
	distributionSha256Sum = "b21468753cb43c167738ee04f10c706c46459cf8f8ae6ea132dc9ce589a261f2"
	distributionType = Wrapper.DistributionType.ALL
}

repositories {
	mavenLocal()
	mavenCentral()
	val exclusiveRepos: List<Triple<String, String, List<String>>> = listOf(
		Triple("macuguita Maven", "https://maven.macuguita.com/releases/", listOf("com.macuguita", "folk.sisby", "org.quiltmc")),
		Triple("Neoforge", "https://maven.neoforged.net/releases", listOf("net.neoforged", "cpw.mods", "new.minecraftforge")),
		Triple("Terraformers (Mod Menu)", "https://maven.terraformersmc.com/releases/", listOf("com.terraformersmc", "dev.emi")),
		Triple("Modrinth", "https://api.modrinth.com/maven", listOf("maven.modrinth")),
	)

	exclusiveRepos.forEach { (name, url, groups) ->
		if (groups.isNotEmpty()) {
			exclusiveContent {
				forRepository {
					maven {
						this.name = name
						setUrl(url)
					}
				}
				filter {
					groups.forEach { includeGroupAndSubgroups(it) }
				}
			}
		} else {
			maven {
				this.name = name
				setUrl(url)
			}
		}
	}
}

fun prop(name: String): String = rootProject.providers.gradleProperty(name).get()

version = prop("props.mod_version")
group = prop("props.maven_group")

base {
	archivesName.set(prop("props.mod_id"))
}

val fabric: SourceSet by sourceSets.creating {
	this.compileClasspath += sourceSets.main.get().compileClasspath
	this.runtimeClasspath += sourceSets.main.get().runtimeClasspath
}

val neoforge: SourceSet by sourceSets.creating {
	this.compileClasspath += sourceSets.main.get().compileClasspath
	this.runtimeClasspath += sourceSets.main.get().runtimeClasspath
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${prop("deps.minecraft_version")}")
	api("dev.yumi.mc.core:yumi-mc-foundation:${prop("deps.yumi_version")}")
	include("dev.yumi.mc.core:yumi-mc-foundation:${prop("deps.yumi_version")}")
	implementation("dev.yumi.mc.core:yumi-mc-foundation:${prop("deps.yumi_version")}")

	compileOnly("net.fabricmc:fabric-loader:${prop("deps.fabric_loader_version")}")
	localRuntime("net.fabricmc:fabric-loader:${prop("deps.fabric_loader_version")}")

	"fabricCompileOnly"("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric_api_version")}")
	localRuntime("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric_api_version")}")

	"neoforgeCompileOnly"("net.neoforged:neoforge:${prop("deps.neoforge_version")}:universal")
	"neoforgeCompileOnly"("net.neoforged.fancymodloader:loader:${prop("deps.neoforge_loader_version")}")

	"neoforgeImplementation"(sourceSets.main.get().output)
	"fabricImplementation"(sourceSets.main.get().output)

	localRuntime(fabric.output)
}

java {
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
	inputs.property("projectName", project.name)

	from("LICENSE") {
		rename { "${it}_${project.name}" }
	}

	from(fabric.output)
	from(neoforge.output)
}

tasks.named<Jar>("sourcesJar") {
	from(fabric.java.sourceDirectories)
	from(fabric.resources.sourceDirectories)
	from(neoforge.java.sourceDirectories)
	from(neoforge.resources.sourceDirectories)
}

fun shouldBeExcluded(file: File): Boolean {
	if (file.isDirectory) {
		val excludedFolderNames = setOf("run", "build", ".kotlin")

		return file.name in excludedFolderNames
	}

	return false
}

idea {
	module {
		isDownloadSources = true
		isDownloadJavadoc = true

		excludeDirs.addAll(
			rootDir.walkTopDown().filter(::shouldBeExcluded)
		)
	}
}
