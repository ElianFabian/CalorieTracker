package com.elian.calorietracker.features.onboarding.presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.onboarding.components.ActionButton
import com.elian.calorietracker.features.onboarding.components.UnitTextField
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun NutrientGoalScreen(
	onCarbsRationChange: (carbsRation: String) -> Unit = {},
	onProteinRationChange: (proteinRation: String) -> Unit = {},
	onFatRationChange: (fatRation: String) -> Unit = {},
	onFinish: () -> Unit = {},
	carbsRatio: String,
	proteinRatio: String,
	fatRatio: String,
) {
	val spacing = LocalSpacing.current

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(spacing.large)
	) {
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.fillMaxSize()
		) {
			Text(
				text = stringResource(R.string.WhatAreYourNutrientGoals),
				style = MaterialTheme.typography.titleLarge,
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			UnitTextField(
				value = carbsRatio,
				onValueChange = onCarbsRationChange,
				unit = stringResource(R.string.Percent_Carbs),
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			UnitTextField(
				value = proteinRatio,
				onValueChange = onProteinRationChange,
				unit = stringResource(R.string.Percent_Proteins),
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			UnitTextField(
				value = fatRatio,
				onValueChange = onFatRationChange,
				unit = stringResource(R.string.Percent_Fats),
			)
		}
		ActionButton(
			text = stringResource(R.string.Finish__verb),
			onClick = onFinish,
			modifier = Modifier
				.align(Alignment.BottomEnd)
		)
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	NutrientGoalScreen(
		carbsRatio = "50",
		proteinRatio = "30",
		fatRatio = "20",
	)
}