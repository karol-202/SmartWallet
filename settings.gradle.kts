rootProject.name = "SmartWallet"

pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
	}

	resolutionStrategy {
		eachPlugin {
			// Android does not place its plugins into Gradle repo,
			// so their search location must be specified
			// in order to be able to use them with plugins {} dsl (without buildscript block)
			when(requested.id.id)
			{
				"com.android.application", "com.android.library" ->
					useModule("com.android.tools.build:gradle:${requested.version}")
			}
		}
	}
}

include(":domain")
include(":data")
include(":framework")
include(":interactors")
include(":presentation")
