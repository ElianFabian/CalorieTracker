package com.elian.calorietracker.features.tracker.presentation.search

import com.elian.calorietracker.R
import com.elian.calorietracker.core.util.UiText
import com.elian.calorietracker.core.util.ext.filterDigits
import com.elian.calorietracker.core.util.simplestack.ServiceScope
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.model.Resource
import com.elian.calorietracker.features.tracker.domain.use_case.SearchFoodUseCase
import com.elian.calorietracker.features.tracker.domain.use_case.TrackFoodUseCase
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.ScopedServices
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class SearchViewModel(
	dateArg: LocalDate,
	mealTypeArg: MealType,
	private val backstack: Backstack,
	private val searchFood: SearchFoodUseCase,
	private val trackFood: TrackFoodUseCase,
	private val serviceScope: ServiceScope = ServiceScope(),
) : ScopedServices.Registered by serviceScope {

	private val _messageFlow = MutableSharedFlow<UiText>()
	val messageFlow = _messageFlow.asSharedFlow()

	private val _state = MutableStateFlow(
		SearchState(
			mealType = mealTypeArg,
			date = dateArg,
		)
	)
	val state = _state.asStateFlow()


	fun onAction(action: SearchAction) {
		when (action) {
			is SearchAction.EnterQuery -> {
				_state.update {
					it.copy(query = action.query)
				}
			}
			is SearchAction.Search -> {
				serviceScope.launch {
					_state.update {
						it.copy(isSearching = true)
					}

					val result = searchFood(
						query = _state.value.query,
					)

					when (result) {
						is Resource.Error -> {
							_messageFlow.emit(result.errorMessage)
						}
						is Resource.Success -> {
							_state.update {
								it.copy(
									trackableFoods = result.data.orEmpty().map { food ->
										TrackableFoodUiState(food = food)
									}
								)
							}
						}
					}

					_state.update {
						it.copy(isSearching = false)
					}
				}
			}
			is SearchAction.EnterFoodAmount -> {
				_state.update {
					it.copy(
						trackableFoods = it.trackableFoods.map { food ->
							if (food.food == action.food) {
								food.copy(amount = action.amount.filterDigits())
							}
							else food
						}
					)
				}
			}
			is SearchAction.TrackFood -> {
				serviceScope.launch {
					val state = _state.value

					val trackableFoodUiState = state.trackableFoods.firstOrNull {
						it.food == action.trackableFood
					}
					if (trackableFoodUiState == null) {
						showMessage(UiText(R.string.CouldntTrackTheSelectedFood))
						return@launch
					}

					val amount = trackableFoodUiState.amount.toIntOrNull()
					if (amount == null) {
						showMessage(UiText(R.string.PleaseEnterAValidAmount))
						return@launch
					}

					trackFood(
						food = action.trackableFood,
						mealType = state.mealType,
						date = state.date,
						amount = amount,
					)

					backstack.goBack()
				}
			}
		}
	}


	private suspend fun showMessage(message: UiText) {
		_messageFlow.emit(message)
	}
}