package com.elian.calorietracker.features.tracker.presentation.tracker_overview

import androidx.compose.runtime.Immutable
import com.elian.calorietracker.core.util.CaloriesPerGramOfCarbs
import com.elian.calorietracker.core.util.CaloriesPerGramOfFat
import com.elian.calorietracker.core.util.CaloriesPerGramOfProtein
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood
import java.time.LocalDate

@Immutable
data class TrackerOverviewState(
	val date: LocalDate = LocalDate.now(),
	val nutrients: NutrientsState = NutrientsState(),
	val trackedFoods: List<TrackedFood> = emptyList(),
	val meals: List<Meal> = DefaultMeals,
)

data class NutrientsState(
	val totalCaloriesInKcal: Int = 0,
	val caloriesGoalInKcal: Int = 0,
	val totalCarbsInGrams: Int = 0,
	val carbsGoalInGrams: Int = 0,
	val totalProteinInGrams: Int = 0,
	val proteinGoalInGrams: Int = 0,
	val totalFatInGrams: Int = 0,
	val fatGoalInGrams: Int = 0,
)

inline val NutrientsState.totalCarbsCaloriesInKcal: Int
	get() = totalCarbsInGrams * CaloriesPerGramOfCarbs

inline val NutrientsState.totalProteinCaloriesInKcal: Int
	get() = totalProteinInGrams * CaloriesPerGramOfProtein

inline val NutrientsState.totalFatCaloriesInKcal: Int
	get() = totalFatInGrams * CaloriesPerGramOfFat