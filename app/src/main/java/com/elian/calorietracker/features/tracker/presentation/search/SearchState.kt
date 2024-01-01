package com.elian.calorietracker.features.tracker.presentation.search

import androidx.compose.runtime.Immutable
import com.elian.calorietracker.features.tracker.domain.model.MealType
import java.time.LocalDate

@Immutable
data class SearchState(
	val mealType: MealType,
	val date: LocalDate,
	val query: String = "",
	val isSearching: Boolean = false,
	val trackableFoods: List<TrackableFoodUiState> = emptyList(),
)
