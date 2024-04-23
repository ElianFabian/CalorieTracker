package com.elian.calorietracker.features.tracker.presentation.tracker_overview

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.elian.calorietracker.core.presentation.simplestack.FragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.features.tracker.di.TrackerOverviewServiceModule
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data object TrackerOverviewKey : FragmentKey(
	serviceModule = TrackerOverviewServiceModule,
) {
	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {

		private val lazyListState = LazyListState()

		override fun onPostCreate() {
			val viewModel = lookup<TrackerOverviewViewModel>()

			lifecycleScope.launch {
				// I had problems collecting the SharedFlow inside a Compose function.
				viewModel.eventFlow
					.collect { event ->
						when (event) {
							TrackerOverviewEvent.ScrollToTop -> {
								lazyListState.scrollToItem(0)
							}
						}
					}
			}
		}

		@Composable
		override fun Content() {
			val viewModel = rememberService<TrackerOverviewViewModel>()

			val state by viewModel.state.collectAsStateWithLifecycle()

			TrackerOverviewScreen(
				meals = state.meals,
				trackedFoods = state.trackedFoods,
				date = state.date,
				nutrients = state.nutrients,
				lazyListState = lazyListState,
				onPreviousDayClick = {
					viewModel.onAction(TrackerOverviewAction.SelectPreviousDay)
				},
				onNextDayClick = {
					viewModel.onAction(TrackerOverviewAction.SelectNextDay)
				},
				onAddMealClick = { mealType ->
					viewModel.onAction(
						TrackerOverviewAction.AddMeal(
							mealType = mealType,
						)
					)
				},
				onDeleteTrackedFoodClick = { trackedFood ->
					viewModel.onAction(TrackerOverviewAction.DeleteTrackedFood(trackedFood))
				},
			)
		}
	}
}