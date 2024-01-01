package com.elian.calorietracker.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AppPreferencesData(
	val user: UserData = UserData(),
	val showOnboarding: Boolean = true,
)

@Serializable
data class UserData(
	val gender: Gender = Gender.Male,
	val age: Int = 0,
	val heightInCm: Int = 0,
	val weightInKg: Float = 0F,
	val activityLevel: ActivityLevel = ActivityLevel.Medium,
	val goalType: GoalType = GoalType.KeepWeight,
	val carbsRatio: Float = 0F,
	val proteinsRatio: Float = 0F,
	val fatRatio: Float = 0F,
)
