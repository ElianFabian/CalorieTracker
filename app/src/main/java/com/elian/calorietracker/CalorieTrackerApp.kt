package com.elian.calorietracker

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.core.os.ConfigurationCompat
import com.elian.calorietracker.core.util.AppBuildConfig
import com.elian.calorietracker.di.provideGlobalServices
import com.zhuinden.simplestack.GlobalServices
import java.util.Locale

class CalorieTrackerApp : Application() {

	lateinit var globalServices: GlobalServices
		private set


	override fun onCreate() {
		super.onCreate()

		setupLocale()

		globalServices = provideGlobalServices()
	}
}

private fun Context.setupLocale() {
	val userLocale = ConfigurationCompat.getLocales(resources.configuration)[0]
	val isUserLocaleSupported = AppBuildConfig.SUPPORTED_LANGUAGES.any { supportedLanguage ->
		userLocale?.language.orEmpty().startsWith(supportedLanguage)
	}

	val localeToSet = if (isUserLocaleSupported) {
		userLocale
	}
	else Locale(AppBuildConfig.DEFAULT_LANGUAGE)

	val configuration = resources.configuration
	configuration.setLocale(localeToSet)
	createConfigurationContext(configuration)
}

inline val Activity.globalServices: GlobalServices
	get() {
		return (application as CalorieTrackerApp).globalServices
	}