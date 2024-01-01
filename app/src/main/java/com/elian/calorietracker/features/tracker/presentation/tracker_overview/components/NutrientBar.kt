package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.ui.CarbColor
import com.elian.calorietracker.ui.FatColor
import com.elian.calorietracker.ui.ProteinColor

@Composable
fun NutrientsBar(
	modifier: Modifier = Modifier,
	carbsCaloriesInKcal: Int,
	proteinCaloriesInKcal: Int,
	fatCaloriesInKcal: Int,
	caloriesInKcal: Int,
	caloriesGoalInKcal: Int,
) {
	val background = MaterialTheme.colorScheme.background
	val caloriesExceedColor = MaterialTheme.colorScheme.error

	val carbWidthRatio = remember {
		Animatable(carbsCaloriesInKcal.toFloat() / caloriesGoalInKcal)
	}
	val proteinWidthRatio = remember {
		Animatable(proteinCaloriesInKcal.toFloat() / caloriesGoalInKcal)
	}
	val fatWidthRatio = remember {
		Animatable(fatCaloriesInKcal.toFloat() / caloriesGoalInKcal)
	}

	LaunchedEffect(carbsCaloriesInKcal) {
		carbWidthRatio.animateTo(
			targetValue = carbsCaloriesInKcal.toFloat() / caloriesGoalInKcal,
		)
	}
	LaunchedEffect(proteinCaloriesInKcal) {
		proteinWidthRatio.animateTo(
			targetValue = proteinCaloriesInKcal.toFloat() / caloriesGoalInKcal,
		)
	}
	LaunchedEffect(fatCaloriesInKcal) {
		fatWidthRatio.animateTo(
			targetValue = fatCaloriesInKcal.toFloat() / caloriesGoalInKcal,
		)
	}

	Canvas(
		modifier = modifier
	) {
		if (caloriesInKcal <= caloriesGoalInKcal) {

			// Makes the cornerRadius be always rounded when the width is not enough
			val fixOffset = 20.dp.toPx()

			val carbsWidth = carbWidthRatio.value * size.width + fixOffset
			val proteinWidth = proteinWidthRatio.value * size.width + fixOffset
			val fatWidth = fatWidthRatio.value * size.width + fixOffset

			val backgroundIndicatorPath = Path().apply {
				addRoundRect(
					roundRect = RoundRect(
						rect = size.toRect(),
						cornerRadius = CornerRadius(100F),
					),
				)
			}

			drawPath(
				path = backgroundIndicatorPath,
				color = background,
			)
			clipPath(
				path = backgroundIndicatorPath,
				clipOp = ClipOp.Intersect,
			) {
				drawRoundRect(
					color = FatColor,
					size = Size(
						width = fatWidth + proteinWidth + carbsWidth,
						height = size.height,
					),
					cornerRadius = CornerRadius(100F),
					topLeft = Offset(-fixOffset * 3, 0F)
				)
				drawRoundRect(
					color = ProteinColor,
					size = Size(
						width = proteinWidth + carbsWidth,
						height = size.height,
					),
					cornerRadius = CornerRadius(100F),
					topLeft = Offset(-fixOffset * 2, 0F),
				)
				drawRoundRect(
					color = CarbColor,
					size = Size(
						width = carbsWidth,
						height = size.height,
					),
					cornerRadius = CornerRadius(100F),
					topLeft = Offset(-fixOffset, 0F),
				)
			}
		}
		else {
			drawRoundRect(
				color = caloriesExceedColor,
				size = size,
				cornerRadius = CornerRadius(100F),
			)
		}
	}
}


@Preview
@Composable
private fun Preview() = BasePreview {
	NutrientsBar(
		carbsCaloriesInKcal = 50 * 4,
		proteinCaloriesInKcal = 50 * 4,
		fatCaloriesInKcal = 50 * 9,
		caloriesInKcal = 500,
		caloriesGoalInKcal = 1000,
		modifier = Modifier
			.fillMaxWidth()
			.height(30.dp)
	)
}