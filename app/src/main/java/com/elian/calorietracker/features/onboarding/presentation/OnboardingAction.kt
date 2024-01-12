package com.elian.calorietracker.features.onboarding.presentation

import com.elian.calorietracker.core.domain.model.ActivityLevel
import com.elian.calorietracker.core.domain.model.Gender
import com.elian.calorietracker.core.domain.model.GoalType

sealed interface OnboardingAction {
	data class SelectGender(val gender: Gender) : OnboardingAction
	data class EnterAge(val age: String) : OnboardingAction
	data class EnterHeight(val height: String) : OnboardingAction
	data class EnterWeight(val weight: String) : OnboardingAction
	data class SelectActivityLevel(val activityLevel: ActivityLevel) : OnboardingAction
	data class SelectGoalType(val goalType: GoalType) : OnboardingAction
	data class EnterCarbsRatio(val carbsRatio: String) : OnboardingAction
	data class EnterProteinRatio(val proteinRatio: String) : OnboardingAction
	data class EnterFatRatio(val fatRatio: String) : OnboardingAction

	data object Next : OnboardingAction
	data object Finish : OnboardingAction
}