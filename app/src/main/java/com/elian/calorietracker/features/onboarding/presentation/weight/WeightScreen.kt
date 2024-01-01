package com.elian.calorietracker.features.onboarding.presentation.weight


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
import com.elian.calorietracker.R
import com.elian.calorietracker.features.onboarding.components.ActionButton
import com.elian.calorietracker.features.onboarding.components.UnitTextField
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun WeightScreen(
	weight: String,
	onWeightChange: (weight: String) -> Unit,
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
				text = stringResource(id = R.string.WhatsYourWeight),
				style = MaterialTheme.typography.titleLarge
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			UnitTextField(
				value = weight,
				onValueChange = onWeightChange,
				unit = stringResource(id = R.string.val__unit_kg)
			)
		}
		ActionButton(
			text = stringResource(id = R.string.Next__verb),
			onClick = onNextClick,
			modifier = Modifier.align(Alignment.BottomEnd)
		)
	}
}