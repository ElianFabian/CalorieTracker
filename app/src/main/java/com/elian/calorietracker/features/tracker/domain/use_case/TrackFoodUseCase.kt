package com.elian.calorietracker.features.tracker.domain.use_case

import com.elian.calorietracker.features.tracker.data.remote.local.TrackerDao
import com.elian.calorietracker.features.tracker.data.remote.mapper.toTrackedFoodEntity
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackableFood
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood
import java.time.LocalDate

class TrackFoodUseCase(
	private val trackerDao: TrackerDao,
) {
	suspend operator fun invoke(
		food: TrackableFood,
		amount: Int,
		mealType: MealType,
		date: LocalDate,
	) {
		val foodToInsert = food.run {
			TrackedFood(
				name = name,
				imageUrl = imageUrl,
				mealType = mealType,
				amountInGrams = amount,
				date = date,
				carbsInGrams = ((carbsPer100g / 100f) * amount).toInt(),
				proteinsInGrams = ((proteinPer100g / 100f) * amount).toInt(),
				fatsInGrams = ((fatPer100g / 100f) * amount).toInt(),
				caloriesInKcal = ((caloriesPer100g / 100f) * amount).toInt(),
			)
		}

		trackerDao.insertTrackedFood(foodToInsert.toTrackedFoodEntity())
	}
}