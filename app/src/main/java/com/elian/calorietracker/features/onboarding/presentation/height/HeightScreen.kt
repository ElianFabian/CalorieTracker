package com.elian.calorietracker.features.onboarding.presentation.height

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
fun HeightScreen(
	height: String,
	onHeightChange: (height: String) -> Unit,
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
				text = stringResource(R.string.WhatsYourHeight),
				style = MaterialTheme.typography.titleLarge
			)
			Spacer(modifier = Modifier.height(spacing.medium))
			UnitTextField(
				value = height,
				onValueChange = onHeightChange,
				unit = stringResource(id = R.string.val__unit_cm)
			)
		}
		ActionButton(
			text = stringResource(id = R.string.Next__verb),
			onClick = onNextClick,
			modifier = Modifier.align(Alignment.BottomEnd)
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	HeightScreen(
		height = "180",
		onHeightChange = {},
		onNextClick = {},
	)
}