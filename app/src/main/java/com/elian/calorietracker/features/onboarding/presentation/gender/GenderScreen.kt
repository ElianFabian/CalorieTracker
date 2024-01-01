package com.elian.calorietracker.features.onboarding.presentation.gender

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
import androidx.compose.ui.tooling.preview.Preview
import com.elian.calorietracker.R
import com.elian.calorietracker.core.domain.model.Gender
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.onboarding.components.ActionButton
import com.elian.calorietracker.features.onboarding.components.SelectableButton
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun GenderScreen(
	selectedGender: Gender,
	onGenderSelected: (gender: Gender) -> Unit,
	onNextClick: () -> Unit,
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
				text = stringResource(R.string.WhatsYourGender),
				style = MaterialTheme.typography.titleLarge,
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			Row(
				verticalAlignment = Alignment.CenterVertically,
			) {
				SelectableButton(
					text = stringResource(R.string.Male),
					isSelected = selectedGender == Gender.Male,
					color = MaterialTheme.colorScheme.onPrimaryContainer,
					selectedTextColor = Color.White,
					onClick = {
						onGenderSelected(Gender.Male)
					},
				)
				Spacer(modifier = Modifier.width(spacing.medium))
				SelectableButton(
					text = stringResource(R.string.Female),
					isSelected = selectedGender == Gender.Female,
					color = MaterialTheme.colorScheme.onPrimaryContainer,
					selectedTextColor = Color.White,
					onClick = {
						onGenderSelected(Gender.Female)
					},
				)
			}
		}
		ActionButton(
			text = stringResource(R.string.Next__verb),
			onClick = onNextClick,
			modifier = Modifier
				.align(Alignment.BottomEnd)
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	GenderScreen(
		selectedGender = Gender.Male,
		onGenderSelected = {},
		onNextClick = {},
	)
}