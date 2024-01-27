import java.util.Locale

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-parcelize")
	id("com.google.devtools.ksp")
	id("org.jetbrains.kotlin.plugin.serialization")
}

android {
	namespace = "com.elian.calorietracker"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.elian.calorietracker"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}

		val defaultLocale = Locale("en")
		val appLocales = getAppLocales(defaultLocale)
		val appLanguages = appLocales.joinToString(",") { locale ->
			"\"${locale}\""
		}

		buildConfigField("String", "DEFAULT_LANGUAGE", "\"$defaultLocale\"")
		buildConfigField("String[]", "SUPPORTED_LANGUAGES", "new String[]{$appLanguages}")
		resourceConfigurations += appLocales.map { it.toString() }
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
			//signingConfig = signingConfigs.getByName("debug")
		}
	}
	compileOptions {
		isCoreLibraryDesugaringEnabled = true

		sourceCompatibility = JavaVersion.VERSION_18
		targetCompatibility = JavaVersion.VERSION_18
	}
	kotlinOptions {
		jvmTarget = "18"
		languageVersion = "1.9"

		freeCompilerArgs += listOf(
			"-opt-in=kotlin.ExperimentalStdlibApi",
		)
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
	testOptions {
		unitTests.isIncludeAndroidResources = true
	}
}

dependencies {

	val retrofitVersion = "2.9.0"
	implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
	implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
	implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

	val okHttpVersion = "5.0.0-alpha.2"
	implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
	implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

	val roomVersion = "2.5.2" // A greater version of room does not compile.
	implementation("androidx.room:room-runtime:$roomVersion")
	implementation("androidx.room:room-ktx:$roomVersion")
	annotationProcessor("androidx.room:room-compiler:$roomVersion")
	ksp("androidx.room:room-compiler:$roomVersion")

	implementation("com.github.Zhuinden:simple-stack:2.8.0")
	implementation("com.github.Zhuinden:simple-stack-extensions:2.3.3")
	//implementation("com.github.Zhuinden:simple-stack-compose-integration:0.12.2")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
	implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

	implementation("io.coil-kt:coil-compose:2.5.0")

	implementation("androidx.activity:activity-compose:1.8.2")
	implementation(platform("androidx.compose:compose-bom:2023.03.00"))
	implementation("androidx.lifecycle:lifecycle-runtime-compose")
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")

	implementation("androidx.datastore:datastore:1.0.0")

	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

	coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kotlin.sourceSets.all {
	languageSettings.enableLanguageFeature("DataObjects")
	languageSettings.enableLanguageFeature("EnumEntries")
}


tasks.withType(Test::class) {
	android.sourceSets.getByName("androidTest").res.srcDirs("src/androidTest/res")
}



fun getAppLocales(defaultLocale: Locale): List<Locale> {
	val foundLocales = mutableListOf(defaultLocale)

	project.android.sourceSets.getByName("main").res.srcDirs.forEach { file ->
		fileTree(file) {
			include("values-*")
		}.visit {
			if (isDirectory) {

				val hasStringsXmlFile = this.file.listFiles().any {
					it.name == "strings.xml"
				}

				var config = name.replaceFirst("values-", "")
				if (config.startsWith("mcc") || config.startsWith("mnc")) {
					val splitPosition = config.indexOf('-')
					if (splitPosition >= 0) {
						config = config.substring(splitPosition + 1)
					}
				}

				kotlin.runCatching {
					val locale: Locale
					if (config.startsWith("b+")) {
						val splitPosition = config.indexOf('-')
						if (splitPosition >= 0) {
							config = config.substring(0, splitPosition)
						}
						locale = Locale.forLanguageTag(config.substring(2))
					}
					else {
						val parts = config.split('-')
						locale = if (parts.size == 1 || !parts[1].startsWith('r')) {
							Locale(parts[0])
						}
						else {
							Locale(parts[0], parts[1].substring(1))
						}
					}

					if (locale.isO3Language != null && hasStringsXmlFile) {
						foundLocales.add(locale)
					}
				}
			}
		}
	}
	return foundLocales
}