import com.android.build.api.dsl.ApplicationProductFlavor
import java.io.FileInputStream
import java.util.Properties
import java.util.regex.Matcher
import java.util.regex.Pattern

plugins {
	alias(libs.plugins.androidApplication)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	id("org.jetbrains.kotlin.kapt")
	alias(libs.plugins.googleAndroidLibraries)
}

// Load keystore
val keystorePropertiesFileDev = rootProject.file("keystore.dev.properties")
val keystorePropertiesDev = Properties()
keystorePropertiesDev.load(FileInputStream(keystorePropertiesFileDev))

val getCurrentFlavor = {
	val tskReqStr: String = gradle.startParameter.taskRequests.toString()
	println("Gradle task: -----> $tskReqStr")
	val pattern = if (tskReqStr.contains("assemble")) {
		Pattern.compile("assemble(\\w+)(Release|Debug)")
	} else if (tskReqStr.contains("bundle")) {
		Pattern.compile("bundle(\\w+)(Release|Debug)")
	} else {
		Pattern.compile("generate(\\w+)(Release|Debug)")
	}
	val matcher: Matcher = pattern.matcher(tskReqStr)
	if (matcher.find()) {
		println("MATCHED -------> ${matcher.group(1).lowercase()}")
		matcher.group(1).lowercase()
	} else {
		println("NO MATCH FOUND ------> Return default flavor")
		"dev"
	}
}

android {
	namespace = "vn.travel.app"
	compileSdk = libs.versions.compileSdk.get().toInt()
	
	defaultConfig {
		minSdk = libs.versions.minSdk.get().toInt()
		targetSdk = libs.versions.targetSdk.get().toInt()
		versionCode = libs.versions.versionCode.get().toInt()
		versionName = libs.versions.versionName.get()
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	signingConfigs {
		create(getCurrentFlavor()) {
			val keystoreProperties = keystorePropertiesDev
			storeFile = file(keystoreProperties.getProperty("KEY_FILE"))
			storePassword = keystoreProperties.getProperty("KEY_PASS")
			keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
			keyPassword = keystoreProperties.getProperty("KEY_PASS")
		}
	}
	
	buildTypes {
		debug {
			isDebuggable = true
			isShrinkResources = false
			isMinifyEnabled = false
			signingConfig = signingConfigs.getByName(getCurrentFlavor())
		}
		release {
			isDebuggable = false
			isShrinkResources = true
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
			)
			signingConfig = signingConfigs.getByName(getCurrentFlavor())
		}
	}
	flavorDimensions += "environment"
	productFlavors {
		fun loadConfig(flavor: ApplicationProductFlavor) {
			println("App ------ productFlavors: -----> ${flavor.name}")
			val appProperties = Properties().apply {
				load(FileInputStream(rootProject.file("env.${flavor.name}.properties")))
			}
			println("application.id: -----> " + appProperties.getProperty("application.id"))
			println("application.name: -----> " + appProperties.getProperty("application.name"))
			flavor.applicationId = appProperties.getProperty("application.id")
			flavor.manifestPlaceholders["applicationName"] =
				appProperties.getProperty("application.name")
		}
		
		create("dev") {
			dimension = "environment"
			loadConfig(this)
		}
	}
	
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	
	kotlinOptions {
		jvmTarget = "1.8"
	}
	
	buildFeatures {
		dataBinding = true
		viewBinding = true
		buildConfig = true
	}
}

secrets {
	// Optionally specify a different file name containing your secrets.
	// The plugin defaults to "local.properties"
	propertiesFileName = "secrets.properties"
	
	// A properties file containing default secret values. This file can be
	// checked in version control.
	defaultPropertiesFileName = "local.defaults.properties"
	
	// Configure which keys should be ignored by the plugin by providing regular expressions.
	// "sdk.dir" is ignored by default.
	ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
	ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}

dependencies {
	implementation(project(":domain"))
	implementation(project(":data"))
	
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.core.splashscreen)
	implementation(libs.androidx.swiperefreshlayout)
	implementation(libs.androidx.paging.runtime)
	implementation(libs.androidx.navigation.fragment.ktx)
	implementation(libs.androidx.navigation.ui.ktx)
	implementation(libs.androidx.constraintlayout)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	
	implementation(libs.google.material)
	implementation(libs.google.maps)
	testImplementation(libs.junit)
	
	implementation(libs.google.gson)
	
	implementation(libs.load.image)
	implementation(libs.load.gif)
	
	implementation(libs.di.koin)
	
	implementation(libs.logger.timber)
	
	implementation(libs.components.dotsindicator)
	implementation(libs.components.photoview)
}
