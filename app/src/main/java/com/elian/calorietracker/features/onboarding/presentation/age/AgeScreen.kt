package com.elian.calorietracker.features.onboarding.presentation.age

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
fun AgeScreen(
	age: String,
	onAgeChange: (age: String) -> Unit,
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
				text = stringResource(id = R.string.WhatsYourAge),
				style = MaterialTheme.typography.titleLarge,
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			UnitTextField(
				value = age,
				onValueChange = { age ->
					onAgeChange(age)
				},
				unit = stringResource(R.string.Years)
			)
		}
		ActionButton(
			text = stringResource(R.string.Next__verb),
			onClick = {
				onNextClick()
			},
			modifier = Modifier
				.align(Alignment.BottomEnd)
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	AgeScreen(
		age = "25",
		onAgeChange = {},
		onNextClick = {},
	)
}