package com.elian.calorietracker.features.onboarding.presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.elian.calorietracker.R
import com.elian.calorietracker.core.domain.model.GoalType
import com.elian.calorietracker.features.onboarding.components.ActionButton
import com.elian.calorietracker.features.onboarding.components.SelectableButton
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun GoalScreen(
	goalType: GoalType,
	onGoalTypeSelected: (goalType: GoalType) -> Unit,
	onNextClick: () -> Unit,
) {
	val spacing = LocalSpacing.current

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(spacing.large)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = stringResource(id = R.string.DoYouWantToLoseKeepOrGainWeight),
				style = MaterialTheme.typography.titleLarge
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			Row(
				verticalAlignment = Alignment.CenterVertically,
			) {
				SelectableButton(
					text = stringResource(id = R.string.Lose),
					isSelected = goalType == GoalType.LoseWeight,
					color = MaterialTheme.colorScheme.onPrimaryContainer,
					selectedTextColor = Color.White,
					onClick = {
						onGoalTypeSelected(GoalType.LoseWeight)
					},
				)
				Spacer(modifier = Modifier.width(spacing.medium))
				SelectableButton(
					text = stringResource(id = R.string.Keep),
					isSelected = goalType == GoalType.KeepWeight,
					color = MaterialTheme.colorScheme.onPrimaryContainer,
					selectedTextColor = Color.White,
					onClick = {
						onGoalTypeSelected(GoalType.KeepWeight)
					},
				)
				Spacer(modifier = Modifier.width(spacing.medium))
				SelectableButton(
					text = stringResource(id = R.string.Gain),
					isSelected = goalType == GoalType.GainWeight,
					color = MaterialTheme.colorScheme.onPrimaryContainer,
					selectedTextColor = Color.White,
					onClick = {
						onGoalTypeSelected(GoalType.GainWeight)
					},
				)
			}
		}
		ActionButton(
			text = stringResource(id = R.string.Next__verb),
			onClick = onNextClick,
			modifier = Modifier.align(Alignment.BottomEnd)
		)
	}
}