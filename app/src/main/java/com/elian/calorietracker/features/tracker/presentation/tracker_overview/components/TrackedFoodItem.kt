package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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

	val height = 115.dp
	val cornerShape = RoundedCornerShape(5.dp)

	Row(
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.shadow(
				elevation = 5.dp,
				shape = cornerShape,
			)
			.clip(cornerShape)
			.background(MaterialTheme.colorScheme.surface)
			.border(
				width = 1.dp,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1F),
				shape = cornerShape,
			)
		
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
				.height(height)
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
			verticalArrangement = Arrangement.SpaceEvenly,
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.heightIn(max = height)
				.fillMaxHeight()
				.padding(
					end = spacing.small / 2,
					top = spacing.small / 2,
					bottom = spacing.small / 2,
				)
		) {
			Row(
				horizontalArrangement = Arrangement.SpaceEvenly,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(
					text = trackedFood.name,
					style = MaterialTheme.typography.bodyMedium,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis,
					modifier = Modifier
						.weight(1F)
				)
				Icon(
					imageVector = Icons.Default.Close,
					contentDescription = stringResource(R.string.Delete),
					modifier = Modifier
						.align(Alignment.Top)
						.clickable { onDeleteClick() }
				)
			}
			Row(
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(
					text = stringResource(
						id = R.string.grams__dot__kcal,
						formatArgs = arrayOf(
							trackedFood.amountInGrams,
							trackedFood.caloriesInKcal,
						),
					),
					fontSize = 14.sp,
				)
			}
			Row(
				verticalAlignment = Alignment.Bottom,
				horizontalArrangement = Arrangement.Center,
				modifier = Modifier
					.fillMaxWidth()
			) {
				NutrientInfo(
					name = stringResource(R.string.Carbs),
					amount = trackedFood.carbsInGrams,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 15.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodySmall,
				)
				Spacer(Modifier.width(spacing.small))
				NutrientInfo(
					name = stringResource(R.string.Proteins),
					amount = trackedFood.proteinsInGrams,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 15.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodySmall,
				)
				Spacer(Modifier.width(spacing.small))
				NutrientInfo(
					name = stringResource(R.string.Fats),
					amount = trackedFood.fatsInGrams,
					unit = stringResource(R.string.val__unit_g),
					amountTextSize = 15.sp,
					unitTextSize = 12.sp,
					nameTextStyle = MaterialTheme.typography.bodySmall,
				)
			}
		}
	}
}


private val trackedFood = TrackedFood(
	id = 1,
	name = "Super extra burger with cheese",
	amountInGrams = 100,
	caloriesInKcal = 200,
	carbsInGrams = 10,
	proteinsInGrams = 20,
	fatsInGrams = 30,
	imageUrl = "",
	date = LocalDate.now(),
	mealType = MealType.Breakfast,
)

@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	TrackedFoodItem(
		trackedFood = trackedFood,
		onDeleteClick = {},
		modifier = Modifier
			.padding(16.dp)
	)
}

@Preview(showBackground = true)
@Composable
private fun PreviewLongText() = BasePreview {
	TrackedFoodItem(
		trackedFood = trackedFood.copy(
			name = "Super extra burger with cheese. Omega OMG!",
		),
		onDeleteClick = {},
		modifier = Modifier
			.padding(16.dp)
	)
}