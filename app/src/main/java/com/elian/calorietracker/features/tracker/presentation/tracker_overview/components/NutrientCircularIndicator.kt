package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.presentation.components.UnitDisplay

@Composable
fun NutrientCircularIndicator(
	modifier: Modifier = Modifier,
	value: Int,
	total: Int,
	name: String,
	color: Color,
	strokeWidth: Dp = 8.dp,
) {
	val background = MaterialTheme.colorScheme.background
	val goalExceedColor = MaterialTheme.colorScheme.error

	fun targetValue() = if (total > 0) {
		val newValue = value.toFloat() / total

		if (newValue.isNaN()) {
			0F
		}
		else newValue
	}
	else 0F

	val angleRatio = remember {
		Animatable(targetValue())
	}

	LaunchedEffect(value) {
		angleRatio.animateTo(
			targetValue = targetValue(),
			animationSpec = tween(
				durationMillis = 300,
			),
		)
	}

	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
	) {
		Canvas(
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1F)
		) {
			val indicatorColor = if (value <= total) background else goalExceedColor

			drawArc(
				color = indicatorColor,
				startAngle = 0F,
				sweepAngle = 360F,
				useCenter = false,
				size = size,
				style = Stroke(
					width = strokeWidth.toPx(),
					cap = StrokeCap.Round,
				)
			)

			if (value <= total) {
				drawArc(
					color = color,
					startAngle = 90F,
					sweepAngle = 360F * angleRatio.value,
					useCenter = false,
					size = size,
					style = Stroke(
						width = strokeWidth.toPx(),
						cap = StrokeCap.Round,
					)
				)
			}
		}
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth()
		) {
			val textColor = if (value <= total) {
				MaterialTheme.colorScheme.onPrimary
			}
			else goalExceedColor

			UnitDisplay(
				amount = value,
				unit = stringResource(R.string.val__unit_g),
				amountColor = textColor,
				unitColor = textColor,
			)
			Text(
				text = name,
				color = textColor,
				style = MaterialTheme.typography.bodySmall,
				fontWeight = FontWeight.Light,
			)
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	NutrientCircularIndicator(
		value = 100,
		total = 200,
		name = "Carbs",
		color = MaterialTheme.colorScheme.primary,
		modifier = Modifier
			.size(90.dp)
			.background(Color.Gray)
	)
}