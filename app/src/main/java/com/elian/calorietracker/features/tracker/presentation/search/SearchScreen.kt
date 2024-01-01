package com.elian.calorietracker.features.tracker.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.TrackableFood
import com.elian.calorietracker.features.tracker.domain.model.nameResId
import com.elian.calorietracker.features.tracker.presentation.search.components.SearchTextField
import com.elian.calorietracker.features.tracker.presentation.search.components.TrackableFoodItem
import com.elian.calorietracker.ui.theme.LocalSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
	mealType: MealType,
	query: String,
	onQueryChange: (query: String) -> Unit,
	onSearchClick: () -> Unit,
	onTrackClick: (food: TrackableFood) -> Unit,
	onTrackableFoodAmountChange: (food: TrackableFood, amount: String) -> Unit,
	trackableFoods: List<TrackableFoodUiState>,
	isSearching: Boolean,
) {
	val spacing = LocalSpacing.current

	val keyboardController = LocalSoftwareKeyboardController.current

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(
				start = spacing.medium,
				end = spacing.medium,
				top = spacing.medium,
			)
	) {
		Text(
			text = stringResource(
				id = R.string.Add_meal,
				formatArgs = arrayOf(
					stringResource(mealType.nameResId),
				),
			),
			style = MaterialTheme.typography.titleMedium,
		)
		Spacer(Modifier.height(spacing.medium))
		SearchTextField(
			value = query,
			onValueChange = { query ->
				onQueryChange(query)
			},
			onSearchClick = {
				keyboardController?.hide()
				onSearchClick()
			},
		)
		//Spacer(Modifier.height(spacing.spaceMedium))
		LazyColumn(
			contentPadding = PaddingValues(
				bottom = spacing.medium,
				top = spacing.medium,
			),
			verticalArrangement = Arrangement.spacedBy(spacing.small),
			modifier = Modifier
				.fillMaxSize()
		) {
			items(trackableFoods) { uiFood ->
				TrackableFoodItem(
					trackableFoodUiState = uiFood,
					onAmountChange = { amount ->
						onTrackableFoodAmountChange(uiFood.food, amount)
					},
					onTrackClick = {
						keyboardController?.hide()
						onTrackClick(uiFood.food)
					},
					modifier = Modifier
						.fillMaxWidth()
				)
			}
		}
	}
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.fillMaxSize()
	) {
		when {
			isSearching              -> CircularProgressIndicator()
			trackableFoods.isEmpty() -> {
				Text(
					text = stringResource(R.string.NoResults),
					style = MaterialTheme.typography.bodyMedium,
					textAlign = TextAlign.Center,
				)
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	SearchScreen(
		mealType = MealType.Breakfast,
		query = "",
		onQueryChange = {},
		onSearchClick = {},
		onTrackClick = {},
		onTrackableFoodAmountChange = { _, _ -> },
		trackableFoods = emptyList(),
		isSearching = false,
	)
}