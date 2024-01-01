package com.elian.calorietracker.features.tracker.presentation.tracker_overview

import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood

sealed interface TrackerOverviewAction {
	data object SelectPreviousDay : TrackerOverviewAction
	data object SelectNextDay : TrackerOverviewAction
	data class AddMeal(
		val mealType: MealType,
	) : TrackerOverviewAction

	data class DeleteTrackedFood(
		val trackedFood: TrackedFood,
	) : TrackerOverviewAction
}