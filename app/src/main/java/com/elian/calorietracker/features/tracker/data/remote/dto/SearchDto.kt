package com.elian.calorietracker.features.tracker.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
	@SerialName("products")
	val products: List<Product>,
) {
	@Serializable
	data class Product(
		@SerialName("image_front_thumb_url")
		val imageFrontThumbUrl: String?,
		@SerialName("nutriments")
		val nutriments: Nutriments,
		@SerialName("product_name")
		val productName: String?,
	) {
		@Serializable
		data class Nutriments(
			@SerialName("carbohydrates_100g")
			val carbohydratesPer100g: Double?,
			@SerialName("energy-kcal_100g")
			val energyKcalPer100g: Double?,
			@SerialName("fat_100g")
			val fatPer100g: Double?,
			@SerialName("proteins_100g")
			val proteinsPer100g: Double?,
		)
	}
}