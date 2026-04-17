plugins {
	kotlin("jvm") version "2.3.10"
	`kotlin-dsl`
}

repositories {
	gradlePluginPortal()
	mavenCentral()

	exclusiveContent {
		forRepository {
			maven {
				name = "FabricMC's Maven"
				url = uri("https://maven.fabricmc.net/")
			}
		}
		filter {
			includeGroupAndSubgroups("net.fabricmc")
			includeGroupAndSubgroups("fabric-loom")
		}
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

kotlin {
	compilerOptions {
		jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
		languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3
		apiVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3
	}
}

dependencies {
	// https://maven.fabricmc.net/fabric-loom/fabric-loom.gradle.plugin/
	implementation("fabric-loom:fabric-loom.gradle.plugin:1.16.+")
	// https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-gradle-plugin
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.10")
	// https://plugins.gradle.org/plugin/co.uzzu.dotenv.gradle
	implementation("co.uzzu.dotenv.gradle:co.uzzu.dotenv.gradle.gradle.plugin:4.0.0")
	// https://plugins.gradle.org/plugin/me.modmuss50.mod-publish-plugin
	implementation("me.modmuss50.mod-publish-plugin:me.modmuss50.mod-publish-plugin.gradle.plugin:1.1.0")
}
