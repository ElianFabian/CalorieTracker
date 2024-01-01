package com.elian.calorietracker.features.tracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun NutrientInfo(
	modifier: Modifier = Modifier,
	name: String,
	amount: Int,
	unit: String,
	amountTextSize: TextUnit = 18.sp,
	amountColor: Color = MaterialTheme.colorScheme.onBackground,
	unitTextSize: TextUnit = 12.sp,
	unitColor: Color = MaterialTheme.colorScheme.onBackground,
	nameTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
) {
	val spacing = LocalSpacing.current

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		UnitDisplay(
			amount = amount,
			unit = unit,
			amountTextSize = amountTextSize,
			amountColor = amountColor,
			unitTextSize = unitTextSize,
			unitColor = unitColor,
		)
		Spacer(Modifier.width(spacing.extraSmall))
		Text(
			text = name,
			color = MaterialTheme.colorScheme.onBackground,
			style = nameTextStyle,
			fontWeight = FontWeight.Light,
		)
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	NutrientInfo(
		name = "Calories",
		amount = 100,
		unit = "kcal",
	)
}