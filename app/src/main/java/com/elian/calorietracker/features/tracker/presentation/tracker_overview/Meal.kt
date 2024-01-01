package com.elian.calorietracker.features.tracker.presentation.tracker_overview

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.elian.calorietracker.R
import com.elian.calorietracker.features.tracker.domain.model.MealType

data class Meal(
	@StringRes
	val nameResId: Int,
	@DrawableRes
	val imageResId: Int,
	val mealType: MealType,
	val carbsInGrams: Int = 0,
	val proteinInGrams: Int = 0,
	val fatInGrams: Int = 0,
	val caloriesInKcal: Int = 0,
)

val DefaultMeals = listOf(
	Meal(
		nameResId = R.string.Breakfast,
		imageResId = R.drawable.ic_breakfast,
		mealType = MealType.Breakfast,
	),
	Meal(
		nameResId = R.string.Lunch,
		imageResId = R.drawable.ic_lunch,
		mealType = MealType.Lunch,
	),
	Meal(
		nameResId = R.string.Dinner,
		imageResId = R.drawable.ic_dinner,
		mealType = MealType.Dinner,
	),
	Meal(
		nameResId = R.string.Snacks,
		imageResId = R.drawable.ic_snacks,
		mealType = MealType.Snacks,
	),
)