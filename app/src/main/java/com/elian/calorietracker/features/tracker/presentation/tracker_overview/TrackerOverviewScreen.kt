package com.elian.calorietracker.features.tracker.presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.components.AddButton
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.components.DaySelector
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.components.ExpandableMeal
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.components.NutrientsHeader
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.components.TrackedFoodItem
import com.elian.calorietracker.ui.theme.LocalSpacing
import java.time.LocalDate

@Composable
fun TrackerOverviewScreen(
	onPreviousDayClick: () -> Unit,
	onNextDayClick: () -> Unit,
	onAddMealClick: (mealType: MealType) -> Unit,
	onDeleteTrackedFoodClick: (trackedFood: TrackedFood) -> Unit,
	date: LocalDate,
	meals: List<Meal>,
	trackedFoods: List<TrackedFood>,
	nutrients: NutrientsState,
	lazyListState: LazyListState = rememberLazyListState(),
) {
	val spacing = LocalSpacing.current

	LazyColumn(
		contentPadding = PaddingValues(bottom = spacing.medium),
		state = lazyListState,
		modifier = Modifier
			.fillMaxSize()
	) {
		item {
			NutrientsHeader(nutrients = nutrients)
			Spacer(Modifier.height(spacing.medium))
			DaySelector(
				date = date,
				onPreviousDayClick = {
					onPreviousDayClick()
				},
				onNextDayClick = {
					onNextDayClick()
				},
				modifier = Modifier.padding(
					horizontal = spacing.medium,
				),
			)
			Spacer(Modifier.height(spacing.medium))
		}
		items(meals) { meal ->
			ExpandableMeal(
				nameResId = meal.nameResId,
				imageResId = meal.imageResId,
				caloriesInGrams = meal.caloriesInKcal,
				carbsInGrams = meal.carbsInGrams,
				proteinsInGrams = meal.proteinInGrams,
				fatsInGrams = meal.fatInGrams,
				expandedContent = {
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = spacing.small)
					) {
						trackedFoods
							.filter { it.mealType == meal.mealType }
							.forEach { food ->
								TrackedFoodItem(
									trackedFood = food,
									onDeleteClick = {
										onDeleteTrackedFoodClick(food)
									},
								)
								Spacer(Modifier.height(spacing.medium))
							}
						AddButton(
							text = stringResource(
								id = R.string.AddMeal,
								formatArgs = arrayOf(
									meal.mealType.name,
								)
							),
							onClick = {
								onAddMealClick(meal.mealType)
							},
							modifier = Modifier
								.fillMaxWidth()
						)
					}
				},
				modifier = Modifier
					.fillMaxWidth()
			)
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	TrackerOverviewScreen(
		onAddMealClick = {},
		onPreviousDayClick = {},
		onNextDayClick = {},
		onDeleteTrackedFoodClick = {},
		date = LocalDate.now(),
		meals = DefaultMeals,
		trackedFoods = emptyList(),
		nutrients = NutrientsState(),
	)
}