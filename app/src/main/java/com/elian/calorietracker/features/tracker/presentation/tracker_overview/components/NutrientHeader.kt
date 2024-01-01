package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.presentation.components.UnitDisplay
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.NutrientsState
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.totalCarbsCaloriesInKcal
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.totalFatCaloriesInKcal
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.totalProteinCaloriesInKcal
import com.elian.calorietracker.ui.CarbColor
import com.elian.calorietracker.ui.FatColor
import com.elian.calorietracker.ui.ProteinColor
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun NutrientsHeader(
	modifier: Modifier = Modifier,
	nutrients: NutrientsState,
) {
	val spacing = LocalSpacing.current

	val animatedTotalCalories = animateIntAsState(
		targetValue = nutrients.totalCaloriesInKcal,
		label = "totalCalories",
	)

	Column(
		modifier = modifier
			.fillMaxWidth()
			.clip(
				RoundedCornerShape(
					bottomStart = 50.dp,
					bottomEnd = 50.dp,
				)
			)
			.background(MaterialTheme.colorScheme.primary)
			.padding(
				horizontal = spacing.large,
				vertical = spacing.extraLarge,
			)
	) {
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier.fillMaxWidth()
		) {
			UnitDisplay(
				amount = animatedTotalCalories.value,
				unit = stringResource(R.string.val__unit_kcal),
				amountColor = MaterialTheme.colorScheme.onPrimary,
				amountTextSize = 40.sp,
				unitColor = MaterialTheme.colorScheme.onPrimary,
				modifier = Modifier.align(Alignment.Bottom)
			)
			Column {
				Text(
					text = "${stringResource(R.string.YourGoal)}:",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onPrimary,
				)
				UnitDisplay(
					amount = nutrients.caloriesGoalInKcal,
					unit = stringResource(R.string.val__unit_kcal),
					amountColor = MaterialTheme.colorScheme.onPrimary,
					amountTextSize = 40.sp,
					unitColor = MaterialTheme.colorScheme.onPrimary,
				)
			}
		}
		Spacer(Modifier.height(spacing.small))
		NutrientsBar(
			carbsCaloriesInKcal = nutrients.totalCarbsCaloriesInKcal,
			proteinCaloriesInKcal = nutrients.totalProteinCaloriesInKcal,
			fatCaloriesInKcal = nutrients.totalFatCaloriesInKcal,
			caloriesInKcal = nutrients.totalCaloriesInKcal,
			caloriesGoalInKcal = nutrients.caloriesGoalInKcal,
			modifier = Modifier
				.fillMaxWidth()
				.height(30.dp)
		)
		Spacer(Modifier.height(spacing.large))
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier.fillMaxWidth()
		) {
			NutrientCircularIndicator(
				value = nutrients.totalCarbsInGrams,
				total = nutrients.carbsGoalInGrams,
				name = stringResource(R.string.Carbs),
				color = CarbColor,
				modifier = Modifier.size(90.dp)
			)
			NutrientCircularIndicator(
				value = nutrients.totalProteinInGrams,
				total = nutrients.proteinGoalInGrams,
				name = stringResource(R.string.Proteins),
				color = ProteinColor,
				modifier = Modifier.size(90.dp)
			)
			NutrientCircularIndicator(
				value = nutrients.totalFatInGrams,
				total = nutrients.fatGoalInGrams,
				name = stringResource(R.string.Fats),
				color = FatColor,
				modifier = Modifier.size(90.dp)
			)
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	NutrientsHeader(
		nutrients = NutrientsState(
			totalCaloriesInKcal = 2000,
			caloriesGoalInKcal = 3000,
			totalCarbsInGrams = 100,
			carbsGoalInGrams = 200,
			totalProteinInGrams = 100,
			proteinGoalInGrams = 200,
			totalFatInGrams = 100,
			fatGoalInGrams = 200,
		)
	)
}