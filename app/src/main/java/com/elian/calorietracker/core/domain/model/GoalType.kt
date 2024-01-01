package com.elian.calorietracker.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GoalType {
	@SerialName("lose_weight")
	LoseWeight,

	@SerialName("keep_weight")
	KeepWeight,

	@SerialName("gain_weight")
	GainWeight,
}