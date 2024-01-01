package com.elian.calorietracker.features.onboarding.presentation.welcome

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.onboarding.components.ActionButton
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun WelcomeScreen(
	onNextClick: () -> Unit,
) {
	val spacing = LocalSpacing.current

	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.fillMaxSize()
			.padding(spacing.medium)
	) {
		Text(
			text = stringResource(R.string.val__WelcomeMessage),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.headlineMedium,
		)
		Spacer(modifier = Modifier.height(spacing.medium))
		ActionButton(
			text = stringResource(R.string.Next__verb),
			onClick = onNextClick,
			modifier = Modifier.align(Alignment.CenterHorizontally)
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	WelcomeScreen(
		onNextClick = {},
	)
}