package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.presentation.components.NutrientInfo
import com.elian.calorietracker.features.tracker.presentation.components.UnitDisplay
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun ExpandableMeal(
	modifier: Modifier = Modifier,
	expandedContent: @Composable () -> Unit,
	@StringRes
	nameResId: Int,
	@DrawableRes
	imageResId: Int,
	caloriesInGrams: Int,
	carbsInGrams: Int,
	proteinsInGrams: Int,
	fatsInGrams: Int,
) {
	val spacing = LocalSpacing.current

	var isExpanded by rememberSaveable {
		mutableStateOf(false)
	}

	Column(
		modifier = modifier
			.padding(spacing.medium)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.clickable {
					isExpanded = !isExpanded
				}
		) {
			Image(
				painter = painterResource(imageResId),
				contentDescription = stringResource(nameResId),
			)
			Spacer(Modifier.width(spacing.medium))
			Column(
				modifier = Modifier
					.weight(1F)
			) {
				Row(
					horizontalArrangement = Arrangement.SpaceBetween,
					modifier = Modifier
						.fillMaxWidth()
				) {
					Text(
						text = stringResource(nameResId),
						style = MaterialTheme.typography.titleSmall
					)
					Icon(
						imageVector = if (isExpanded) {
							Icons.Default.KeyboardArrowUp
						}
						else Icons.Default.KeyboardArrowDown,
						contentDescription = stringResource(
							if (isExpanded) R.string.Collapse else R.string.Expand,
						),
					)
				}
				Spacer(Modifier.height(spacing.small))
				Row(
					horizontalArrangement = Arrangement.SpaceBetween,
					modifier = Modifier
						.fillMaxWidth()
				) {
					UnitDisplay(
						amount = caloriesInGrams,
						unit = stringResource(R.string.val__unit_kcal),
						amountTextSize = 28.sp,
					)
					Row {
						NutrientInfo(
							name = stringResource(R.string.Carbs),
							amount = carbsInGrams,
							unit = stringResource(R.string.val__unit_g),
						)
						Spacer(Modifier.width(spacing.small))
						NutrientInfo(
							name = stringResource(R.string.Proteins),
							amount = proteinsInGrams,
							unit = stringResource(R.string.val__unit_g),
						)
						Spacer(Modifier.width(spacing.small))
						NutrientInfo(
							name = stringResource(R.string.Fats),
							amount = fatsInGrams,
							unit = stringResource(R.string.val__unit_g),
						)
					}
				}
			}
		}
		Spacer(Modifier.height(spacing.medium))
		AnimatedVisibility(
			visible = isExpanded,
		) {
			expandedContent()
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	ExpandableMeal(
		imageResId = R.drawable.ic_breakfast,
		nameResId = R.string.Breakfast,
		caloriesInGrams = 100,
		carbsInGrams = 100,
		proteinsInGrams = 100,
		fatsInGrams = 100,
		expandedContent = {

		},
	)
}