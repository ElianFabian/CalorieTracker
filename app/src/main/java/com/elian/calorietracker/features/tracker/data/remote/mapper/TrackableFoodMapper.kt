package com.elian.calorietracker.features.tracker.data.remote.mapper

import com.elian.calorietracker.features.tracker.data.remote.dto.SearchDto
import com.elian.calorietracker.features.tracker.domain.model.TrackableFood

fun SearchDto.Product.toTrackableFood(): TrackableFood? {
	val carbsPer100g = nutriments.carbohydratesPer100g?.toInt()
	val proteinPer100g = nutriments.proteinsPer100g?.toInt()
	val fatPer100g = nutriments.fatPer100g?.toInt()
	val caloriesPer100g = nutriments.energyKcalPer100g?.toInt()

	return TrackableFood(
		name = productName ?: return null,
		carbsPer100g = carbsPer100g ?: return null,
		proteinPer100g = proteinPer100g ?: return null,
		fatPer100g = fatPer100g ?: return null,
		caloriesPer100g = caloriesPer100g ?: return null,
		imageUrl = imageFrontThumbUrl
	)
}