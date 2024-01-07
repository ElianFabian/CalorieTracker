package com.elian.calorietracker.features.tracker.domain.model

import java.time.LocalDate

data class TrackedFood(
	val name: String,
	val imageUrl: String?,
	val mealType: MealType,
	val date: LocalDate,
	val carbsInGrams: Int,
	val proteinsInGrams: Int,
	val fatsInGrams: Int,
	val amountInGrams: Int,
	val caloriesInKcal: Int,
	val id: Long? = null,
)
