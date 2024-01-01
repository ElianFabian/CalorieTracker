package com.elian.calorietracker.features.tracker.presentation.search

import com.elian.calorietracker.features.tracker.domain.model.TrackableFood

data class TrackableFoodUiState(
	val food: TrackableFood,
	val amount: String = "",
)
