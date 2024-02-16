package com.elian.calorietracker.features.tracker.presentation.search

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elian.calorietracker.core.presentation.ext.collectAsEffectWithLifecycle
import com.elian.calorietracker.core.presentation.simplestack.FragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.core.util.toString
import com.elian.calorietracker.features.tracker.di.SearchServiceModule
import com.elian.calorietracker.features.tracker.domain.model.MealType
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class SearchKey(
	val date: LocalDate,
	val mealType: MealType,
) : FragmentKey(
	serviceModule = SearchServiceModule(
		date = date,
		mealType = mealType,
	),
) {
	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {

		@Composable
		override fun Content() {

			val viewModel = rememberService<SearchViewModel>()

			val context = LocalContext.current

			viewModel.messageFlow.collectAsEffectWithLifecycle { message ->
				Toast.makeText(context, message.toString(requireContext()), Toast.LENGTH_SHORT).show()
			}

			val state by viewModel.state.collectAsStateWithLifecycle()

			SearchScreen(
				mealType = state.mealType,
				query = state.query,
				onQueryChange = { query ->
					viewModel.onAction(SearchAction.EnterQuery(query))
				},
				onSearchClick = {
					viewModel.onAction(SearchAction.Search)
				},
				onTrackClick = { food ->
					viewModel.onAction(SearchAction.TrackFood(food))
				},
				onTrackableFoodAmountChange = { food, amount ->
					viewModel.onAction(SearchAction.EnterFoodAmount(food, amount))
				},
				trackableFoods = state.trackableFoods,
				isSearching = state.isSearching,
			)
		}
	}
}