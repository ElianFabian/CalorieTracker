package com.elian.calorietracker.features.tracker.presentation.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.domain.model.TrackableFood
import com.elian.calorietracker.features.tracker.presentation.components.NutrientInfo
import com.elian.calorietracker.features.tracker.presentation.search.TrackableFoodUiState
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun TrackableFoodItem(
	modifier: Modifier = Modifier,
	onAmountChange: (amount: String) -> Unit,
	onTrackClick: () -> Unit,
	trackableFoodUiState: TrackableFoodUiState,
) {
	val food = trackableFoodUiState.food
	val spacing = LocalSpacing.current

	var isExpanded by remember { mutableStateOf(false) }

	Column(
		modifier = modifier
			.clip(RoundedCornerShape(5.dp))
			.padding(spacing.extraSmall)
			.shadow(
				elevation = 1.dp,
				shape = RoundedCornerShape(5.dp),
			)
			.background(MaterialTheme.colorScheme.surface)
			.clickable {
				isExpanded = !isExpanded
			}
			.padding(end = spacing.medium)
	) {
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
		) {
			Row(
				modifier = Modifier
					.weight(1F)
			) {
				Image(
					painter = rememberImagePainter(
						data = food.imageUrl,
						builder = {
							crossfade(true)
							error(R.drawable.ic_burger)
							fallback(R.drawable.ic_burger)
						}
					),
					contentDescription = food.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.size(100.dp)
						.clip(RoundedCornerShape(topStart = 5.dp))
				)
				Spacer(Modifier.width(spacing.medium))
				Column(
					modifier = Modifier
						.align(Alignment.CenterVertically)
				) {
					Text(
						text = food.name,
						style = MaterialTheme.typography.bodyLarge,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
					)
					Spacer(Modifier.width(spacing.small))
					Text(
						text = stringResource(
							id = R.string.kcal_Kcal__Per__100g,
							formatArgs = arrayOf(
								food.caloriesPer100g,
							),
						),
						style = MaterialTheme.typography.bodyMedium,
					)
				}
			}
			Row {
				NutrientInfo(
					name = stringResource(R.string.Carbs),
					amount = food.carbsPer100g,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 16.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodyMedium,
				)
				Spacer(Modifier.width(spacing.small))
				NutrientInfo(
					name = stringResource(R.string.Proteins),
					amount = food.proteinPer100g,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 16.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodyMedium,
				)
				Spacer(Modifier.width(spacing.small))
				NutrientInfo(
					name = stringResource(R.string.Fats),
					amount = food.fatPer100g,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 16.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodyMedium,
				)
			}
		}
		AnimatedVisibility(
			visible = isExpanded,
		) {
			Row(
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.padding(spacing.medium)
			) {
				Row {
					BasicTextField(
						value = trackableFoodUiState.amount,
						onValueChange = onAmountChange,
						keyboardOptions = KeyboardOptions(
							imeAction = if (trackableFoodUiState.amount.isNotBlank()) {
								ImeAction.Done
							}
							else ImeAction.Default,
							keyboardType = KeyboardType.Number,
						),
						keyboardActions = KeyboardActions(
							onDone = {
								onTrackClick()
								defaultKeyboardAction(ImeAction.Done)
							}
						),
						singleLine = true,
						modifier = Modifier
							.border(
								shape = RoundedCornerShape(5.dp),
								width = 0.5.dp,
								color = MaterialTheme.colorScheme.onSurface,
							)
							.alignBy(LastBaseline)
							.padding(spacing.medium)
					)
					Spacer(Modifier.width(spacing.extraSmall))
					Text(
						text = stringResource(R.string.val__unit_g),
						style = MaterialTheme.typography.bodyLarge,
						modifier = Modifier
							.alignBy(LastBaseline)
					)
				}
				IconButton(
					onClick = onTrackClick,
					enabled = trackableFoodUiState.amount.isNotBlank(),
				) {
					Icon(
						imageVector = Icons.Default.Check,
						contentDescription = stringResource(R.string.Track),
					)
				}
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	TrackableFoodItem(
		trackableFoodUiState = TrackableFoodUiState(
			food = TrackableFood(
				name = "Burger",
				imageUrl = "",
				caloriesPer100g = 100,
				carbsPer100g = 100,
				proteinPer100g = 100,
				fatPer100g = 100,
			),
			amount = "155",
		),
		onTrackClick = {},
		onAmountChange = {},
	)
}