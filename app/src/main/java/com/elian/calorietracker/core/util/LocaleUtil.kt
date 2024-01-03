package com.elian.calorietracker.core.util

import android.app.Application
import androidx.core.os.ConfigurationCompat
import java.util.Locale

fun Application.setupLocale() {
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