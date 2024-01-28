package com.elian.calorietracker.core.util

import android.app.Application
import androidx.core.os.ConfigurationCompat
import java.util.Locale

fun Application.setupLocale() {
	val userLocale = ConfigurationCompat.getLocales(resources.configuration)[0]
	val isUserLocaleSupported = AppBuildConfig.SupportedLanguages.any { supportedLanguage ->
		userLocale?.language.orEmpty().startsWith(supportedLanguage)
	}

	val localeToSet = if (isUserLocaleSupported) {
		userLocale
	}
	else Locale(AppBuildConfig.DefaultLanguage)

	val configuration = resources.configuration
	configuration.setLocale(localeToSet)
	createConfigurationContext(configuration)
}