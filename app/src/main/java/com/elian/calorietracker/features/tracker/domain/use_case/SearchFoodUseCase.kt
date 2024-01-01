package com.elian.calorietracker.features.tracker.domain.use_case

import com.elian.calorietracker.features.tracker.data.remote.OpenFoodApi
import com.elian.calorietracker.features.tracker.domain.model.safeApiCall

class SearchFoodUseCase(
	private val api: OpenFoodApi,
) {
	suspend operator fun invoke(
		query: String,
		page: Int = 1,
		pageSize: Int = 40,
	) = safeApiCall {
		val response = api.searchFood(
			query = query.trim(),
			page = page,
			pageSize = pageSize,
		)

		val products = response.products

		products.filter { product ->
			// WORKAROUND: not all the food from the API respects that the energy in kcal
			// is equal to 4 * carbohydrates + 4 * proteins + 9 * fat
			// so we filter out the ones that don't respect this with a 1% margin

			val nutriments = product.nutriments
			val carbohydratesPer100g = nutriments.carbohydratesPer100g ?: return@filter false
			val proteins100g = nutriments.proteinsPer100g ?: return@filter false
			val fat100g = nutriments.fatPer100g ?: return@filter false
			val energyInKcalPer100g = nutriments.energyKcalPer100g ?: return@filter false

			val calculatedCalories = carbohydratesPer100g * 4 +
				proteins100g * 4 +
				fat100g * 9

			val errorRangePercentage = 0.01F

			val lowerBound = calculatedCalories * (1 - errorRangePercentage)
			val upperBound = calculatedCalories * (1 + errorRangePercentage)

			energyInKcalPer100g in lowerBound..upperBound
		}
	}
}