package com.elian.calorietracker.core.presentation

import com.elian.calorietracker.core.domain.app_preferences.AppPreferences
import kotlinx.coroutines.flow.map

class MainViewModel(
	preferences: AppPreferences,
) {
	val showOnboarding = preferences.data.map { it.showOnboarding }
}