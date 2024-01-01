package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood
import com.elian.calorietracker.features.tracker.presentation.components.NutrientInfo
import com.elian.calorietracker.ui.theme.LocalSpacing
import java.time.LocalDate

@Composable
fun TrackedFoodItem(
	modifier: Modifier = Modifier,
	onDeleteClick: () -> Unit,
	trackedFood: TrackedFood,
) {
	val spacing = LocalSpacing.current

	Row(
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.clip(RoundedCornerShape(5.dp))
			.padding(spacing.extraSmall)
			.shadow(
				elevation = 1.dp,
				shape = RoundedCornerShape(5.dp),
			)
			.background(MaterialTheme.colorScheme.surface)
			.padding(end = spacing.medium)
			.height(100.dp)
	) {
		Image(
			painter = rememberAsyncImagePainter(
				model = ImageRequest.Builder(LocalContext.current)
					.data(trackedFood.imageUrl)
					.crossfade(true)
					.error(R.drawable.ic_burger)
					.fallback(R.drawable.ic_burger)
					.build(),
			),
			contentDescription = trackedFood.name,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.fillMaxHeight()
				.aspectRatio(1F)
				.clip(
					RoundedCornerShape(
						topStart = 5.dp,
						bottomStart = 5.dp,
					)
				)
		)
		Spacer(Modifier.width(spacing.medium))
		Column(
			modifier = Modifier
				.weight(1F)
		) {
			Text(
				text = trackedFood.name,
				style = MaterialTheme.typography.bodyMedium,
				overflow = TextOverflow.Ellipsis,
			)
			Spacer(Modifier.height(spacing.extraSmall))
			Text(
				text = stringResource(
					id = R.string.grams__dot__kcal,
					formatArgs = arrayOf(
						trackedFood.amount,
						trackedFood.caloriesInKcal,
					),
				),
			)
		}
		Spacer(Modifier.width(spacing.medium))
		Column(
			verticalArrangement = Arrangement.Center,
			modifier = Modifier
				.fillMaxHeight()
		) {
			Icon(
				imageVector = Icons.Default.Close,
				contentDescription = stringResource(R.string.Delete),
				modifier = Modifier
					.align(Alignment.End)
					.clickable { onDeleteClick() }
			)
			Spacer(Modifier.height(spacing.extraSmall))
			Row(
				verticalAlignment = Alignment.CenterVertically,
			) {
				NutrientInfo(
					name = stringResource(R.string.Carbs),
					amount = trackedFood.carbsInGrams,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 16.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodySmall,
				)
				Spacer(Modifier.width(spacing.small))
				NutrientInfo(
					name = stringResource(R.string.Proteins),
					amount = trackedFood.proteinsInGrams,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 16.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodySmall,
				)
				Spacer(Modifier.width(spacing.small))
				NutrientInfo(
					name = stringResource(R.string.Fats),
					amount = trackedFood.fatsInGrams,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 16.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodySmall,
				)
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	TrackedFoodItem(
		trackedFood = TrackedFood(
			id = 1,
			name = "Burger",
			amount = 100,
			caloriesInKcal = 200,
			carbsInGrams = 10,
			proteinsInGrams = 20,
			fatsInGrams = 30,
			imageUrl = "",
			date = LocalDate.now(),
			mealType = MealType.Breakfast,
		),
		onDeleteClick = {},
	)
}