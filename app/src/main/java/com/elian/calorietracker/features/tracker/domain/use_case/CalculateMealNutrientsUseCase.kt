package com.elian.calorietracker.features.tracker.domain.use_case

import com.elian.calorietracker.core.domain.app_preferences.AppPreferences
import com.elian.calorietracker.core.domain.model.ActivityLevel
import com.elian.calorietracker.core.domain.model.Gender
import com.elian.calorietracker.core.domain.model.GoalType
import com.elian.calorietracker.core.util.CaloriesPerGramOfCarbs
import com.elian.calorietracker.core.util.CaloriesPerGramOfFat
import com.elian.calorietracker.core.util.CaloriesPerGramOfProtein
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood
import kotlinx.coroutines.flow.first
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
	private val preferences: AppPreferences,
) {
	suspend operator fun invoke(
		trackedFoods: List<TrackedFood>
	): Result {
		val allNutrients = trackedFoods
			.groupBy { it.mealType }
			.mapValues { (mealType, foods) ->
				MealNutrients(
					totalCarbsInGrams = foods.sumOf { it.carbsInGrams },
					totalProteinInGrams = foods.sumOf { it.proteinsInGrams },
					totalFatInGrams = foods.sumOf { it.fatsInGrams },
					totalCaloriesInKcal = foods.sumOf { it.caloriesInKcal },
					mealType = mealType,
				)
			}

		val mealsTotalCarbsInGrams = allNutrients.values.sumOf { it.totalCarbsInGrams }
		val mealsTotalProteinsInGrams = allNutrients.values.sumOf { it.totalProteinInGrams }
		val mealsTotalFatsInGrams = allNutrients.values.sumOf { it.totalFatInGrams }
		val mealsTotalCaloriesInKcal = allNutrients.values.sumOf { it.totalCaloriesInKcal }

		val userData = preferences.data.first().user

		val calorieRequirement = calculateDailyCalorieRequirement(
			gender = userData.gender,
			activityLevel = userData.activityLevel,
			goalType = userData.goalType,
			weightInKg = userData.weightInKg,
			heightInCm = userData.heightInCm,
			age = userData.age,
		)

		val carbsInGramsGoal = (calorieRequirement * userData.carbsRatio / CaloriesPerGramOfCarbs).roundToInt()
		val proteinInGramsGoal = (calorieRequirement * userData.proteinsRatio / CaloriesPerGramOfProtein).roundToInt()
		val fatInGramsGoal = (calorieRequirement * userData.fatRatio / CaloriesPerGramOfFat).roundToInt()

		return Result(
			carbsGoalInGrams = carbsInGramsGoal,
			proteinGoalInGrams = proteinInGramsGoal,
			fatGoalInGrams = fatInGramsGoal,
			caloriesGoalInKcal = calorieRequirement,
			totalCarbsInGrams = mealsTotalCarbsInGrams,
			totalProteinInGrams = mealsTotalProteinsInGrams,
			totalFatInGrams = mealsTotalFatsInGrams,
			totalCaloriesInKcal = mealsTotalCaloriesInKcal,
			mealNutrients = allNutrients,
		)
	}

	private fun calculateBmr(
		gender: Gender,
		weightInKg: Float,
		heightInCm: Int,
		age: Int,
	): Int {
		return when (gender) {
			Gender.Male   -> {
				66.47F + 13.75F * weightInKg + 5F * heightInCm - 6.75F * age
			}
			Gender.Female -> {
				665.09F + 9.56F * weightInKg + 1.84F * heightInCm - 4.67F * age
			}
		}.roundToInt()
	}

	private fun calculateDailyCalorieRequirement(
		activityLevel: ActivityLevel,
		goalType: GoalType,
		gender: Gender,
		weightInKg: Float,
		heightInCm: Int,
		age: Int,
	): Int {
		val activityFactor = when (activityLevel) {
			ActivityLevel.Low    -> 1.2F
			ActivityLevel.Medium -> 1.3F
			ActivityLevel.High   -> 1.4F
		}

		val caloriesExtra = when (goalType) {
			GoalType.LoseWeight -> -500
			GoalType.KeepWeight -> 0
			GoalType.GainWeight -> 500
		}

		val bmr = calculateBmr(
			gender = gender,
			weightInKg = weightInKg,
			heightInCm = heightInCm,
			age = age,
		)

		return (bmr * activityFactor + caloriesExtra).roundToInt()
	}

	data class MealNutrients(
		val totalCarbsInGrams: Int,
		val totalProteinInGrams: Int,
		val totalFatInGrams: Int,
		val totalCaloriesInKcal: Int,
		val mealType: MealType,
	)

	data class Result(
		val carbsGoalInGrams: Int,
		val proteinGoalInGrams: Int,
		val fatGoalInGrams: Int,
		val caloriesGoalInKcal: Int,
		val totalCarbsInGrams: Int,
		val totalProteinInGrams: Int,
		val totalFatInGrams: Int,
		val totalCaloriesInKcal: Int,
		val mealNutrients: Map<MealType, MealNutrients>,
	)
}