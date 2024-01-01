package com.elian.calorietracker.features.tracker.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun UnitDisplay(
	modifier: Modifier = Modifier,
	amount: Int,
	unit: String,
	amountTextSize: TextUnit = 20.sp,
	amountColor: Color = MaterialTheme.colorScheme.onBackground,
	unitTextSize: TextUnit = 14.sp,
	unitColor: Color = MaterialTheme.colorScheme.onBackground,
) {
	val spacing = LocalSpacing.current

	Row(
		modifier = modifier
	) {
		Text(
			text = "$amount",
			style = MaterialTheme.typography.titleSmall,
			fontSize = amountTextSize,
			color = amountColor,
			modifier = Modifier.alignBy(LastBaseline)
		)
		Spacer(Modifier.width(spacing.extraSmall))
		Text(
			text = unit,
			style = MaterialTheme.typography.bodySmall,
			fontSize = unitTextSize,
			color = unitColor,
			modifier = Modifier.alignBy(LastBaseline)
		)
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	UnitDisplay(
		amount = 100,
		unit = "g",
	)
}