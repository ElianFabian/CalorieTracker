package com.elian.calorietracker.core.data.app_preferences

import androidx.datastore.core.DataStore
import com.elian.calorietracker.core.domain.app_preferences.AppPreferences
import com.elian.calorietracker.core.domain.model.AppPreferencesData

class AppPreferencesImpl(
	private val dataStore: DataStore<AppPreferencesData>,
) : AppPreferences {
	override val data = dataStore.data

	override suspend fun updateData(
		transform: suspend (preferences: AppPreferencesData) -> AppPreferencesData
	): AppPreferencesData {
		return dataStore.updateData(transform)
	}
}