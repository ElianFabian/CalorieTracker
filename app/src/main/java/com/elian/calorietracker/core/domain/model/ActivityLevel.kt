package com.elian.calorietracker.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ActivityLevel {
	@SerialName("low")
	Low,

	@SerialName("medium")
	Medium,

	@SerialName("high")
	High,
}
