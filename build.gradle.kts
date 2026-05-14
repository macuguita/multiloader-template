plugins {
	`maven-publish`
	id("mod")
	alias(libs.plugins.dotenv)
	alias(libs.plugins.mod.publish)
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
	implementation(libs.kaleido.config)
	include(libs.kaleido.config)
}

tasks.processResources {
	inputs.properties(
		"version" to version,
		"yumi_version" to libs.versions.yumi.get(),
		"minecraft_fabric_version_range" to prop("deps.minecraft_fabric_version_range"),
		"minecraft_neoforge_version_range" to prop("deps.minecraft_neoforge_version_range")
	)

	filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/jarjar/metadata.json")) {
		expand(
			"version" to version,
			"yumi_version" to libs.versions.yumi.get(),
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
			version = libs.versions.mod.get() + "+${libs.versions.minecraft.get()}"
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
