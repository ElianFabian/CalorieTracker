package com.elian.calorietracker.core.domain.app_preferences

import com.elian.calorietracker.core.domain.model.AppPreferencesData
import kotlinx.coroutines.flow.Flow

interface AppPreferences {
	val data: Flow<AppPreferencesData>

	suspend fun updateData(
		transform: suspend (preferences: AppPreferencesData) -> AppPreferencesData
	): AppPreferencesData
}