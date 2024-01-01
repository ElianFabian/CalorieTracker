package com.elian.calorietracker.features.tracker.presentation.search

import com.elian.calorietracker.features.tracker.domain.model.TrackableFood

sealed interface SearchAction {
	data class EnterQuery(val query: String) : SearchAction
	data class TrackFood(val trackableFood: TrackableFood) : SearchAction
	data class EnterFoodAmount(val food: TrackableFood, val amount: String) : SearchAction
	data object Search : SearchAction
}