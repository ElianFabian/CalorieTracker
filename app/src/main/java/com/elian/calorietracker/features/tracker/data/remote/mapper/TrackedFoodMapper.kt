package com.elian.calorietracker.features.tracker.data.remote.mapper

import com.elian.calorietracker.features.tracker.data.remote.local.entity.TrackedFoodEntity
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood

fun TrackedFoodEntity.toTrackedFood(): TrackedFood? {
	return TrackedFood(
		name = name,
		mealType = MealType.fromKeyOrNull(mealType) ?: return null,
		imageUrl = imageUrl,
		date = date,
		carbsInGrams = carbs,
		proteinsInGrams = protein,
		fatsInGrams = fat,
		amountInGrams = amount,
		caloriesInKcal = calories,
		id = id,
	)
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
	return TrackedFoodEntity(
		name = name,
		mealType = mealType.key,
		imageUrl = imageUrl,
		date = date,
		carbs = carbsInGrams,
		protein = proteinsInGrams,
		fat = fatsInGrams,
		amount = amountInGrams,
		calories = caloriesInKcal,
		id = id,
	)
}