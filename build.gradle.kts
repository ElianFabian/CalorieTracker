buildscript { 
	dependencies { 
		classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
	}
}

plugins {
	id("com.android.application") version "8.1.3" apply false
	id("org.jetbrains.kotlin.android") version "1.8.10" apply false
	id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}