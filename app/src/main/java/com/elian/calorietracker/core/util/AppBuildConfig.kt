package com.elian.calorietracker.core.util

import com.elian.calorietracker.BuildConfig

object AppBuildConfig {
	val isDebug = BuildConfig.DEBUG
	val isRelease = !isDebug
	const val ApplicationId = BuildConfig.APPLICATION_ID
	const val BuildType = BuildConfig.BUILD_TYPE
	const val VersionCode = BuildConfig.VERSION_CODE
	const val VersionName = BuildConfig.VERSION_NAME

	const val DefaultLanguage = BuildConfig.DEFAULT_LANGUAGE
	val SupportedLanguages = BuildConfig.SUPPORTED_LANGUAGES.filterNotNull()
}