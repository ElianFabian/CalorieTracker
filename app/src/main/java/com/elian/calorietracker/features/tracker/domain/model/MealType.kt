package com.elian.calorietracker.features.tracker.domain.model

import androidx.annotation.StringRes
import com.elian.calorietracker.R

enum class MealType(val key: String) {
	Breakfast("breakfast"),
	Lunch("lunch"),
	Dinner("dinner"),
	Snacks("snacks");

	companion object {
		fun fromKeyOrNull(key: String): MealType? {
			return entries.firstOrNull { it.key == key }
		}
	}
}

@get:StringRes
inline val MealType.nameResId: Int
	get() = when (this) {
		MealType.Breakfast -> R.string.Breakfast
		MealType.Lunch     -> R.string.Lunch
		MealType.Dinner    -> R.string.Dinner
		MealType.Snacks    -> R.string.Snacks
	}