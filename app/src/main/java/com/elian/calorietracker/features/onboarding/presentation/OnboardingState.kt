package com.elian.calorietracker.features.onboarding.presentation

import android.os.Parcelable
import com.elian.calorietracker.core.domain.model.ActivityLevel
import com.elian.calorietracker.core.domain.model.Gender
import com.elian.calorietracker.core.domain.model.GoalType
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardingState(
	val gender: Gender = Gender.Male,
	val age: String = "25",
	val height: String = "180",
	val weight: String = "80",
	val activityLevel: ActivityLevel = ActivityLevel.Medium,
	val goalType: GoalType = GoalType.KeepWeight,
	val carbsRatio: String = "40",
	val proteinRatio: String = "30",
	val fatRatio: String = "30",
) : Parcelable