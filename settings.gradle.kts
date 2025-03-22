
pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven {
			url = uri("https://repo.sleeping.town")
			content {
				includeGroup("com.unascribed.nilgradle")
			}
		}
	}
}
