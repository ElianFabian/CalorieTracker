package com.elian.calorietracker.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
	@SerialName("male")
	Male,

	@SerialName("female")
	Female,
}