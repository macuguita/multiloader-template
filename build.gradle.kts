plugins {
	`maven-publish`
	id("mod")
	id("co.uzzu.dotenv.gradle")
	id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String): String = providers.gradleProperty(name).get()

loom {
	accessWidenerPath.set(project.file("src/main/resources/modtemplate.classtweaker"))
}

fabricApi {
	configureDataGeneration {
		client = true
	}
}

dependencies {
	implementation("folk.sisby:kaleido-config:${prop("deps.kaleido_version")}")
	include("folk.sisby:kaleido-config:${prop("deps.kaleido_version")}")
}

tasks.processResources {
	inputs.properties(
		"version" to version,
		"yumi_version" to prop("deps.yumi_version"),
		"minecraft_fabric_version_range" to prop("deps.minecraft_fabric_version_range"),
		"minecraft_neoforge_version_range" to prop("deps.minecraft_neoforge_version_range")
	)

	filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/jarjar/metadata.json")) {
		expand(
			"version" to version,
			"yumi_version" to prop("deps.yumi_version"),
			"minecraft_fabric_version_range" to prop("deps.minecraft_fabric_version_range"),
			"minecraft_neoforge_version_range" to prop("deps.minecraft_neoforge_version_range")
		)
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			groupId = prop("props.maven_group")
			artifactId = prop("props.mod_id")
			version = prop("props.mod_version") + "+${property("deps.minecraft_version")}"
			from(components["java"])
		}
	}
	repositories {
		mavenLocal()
		maven {
			name = "macuguita"
			url = uri("https://maven.macuguita.com/releases")

			credentials {
				username = env.MAVEN_USERNAME.orNull()
				password = env.MAVEN_KEY.orNull()
			}
		}
	}
}
