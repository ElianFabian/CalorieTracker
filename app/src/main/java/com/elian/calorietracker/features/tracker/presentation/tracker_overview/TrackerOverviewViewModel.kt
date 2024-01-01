package com.elian.calorietracker.features.tracker.presentation.tracker_overview

import com.elian.calorietracker.core.util.simplestack.ServiceScope
import com.elian.calorietracker.features.tracker.domain.use_case.CalculateMealNutrientsUseCase
import com.elian.calorietracker.features.tracker.domain.use_case.DeleteTrackedFoodUseCase
import com.elian.calorietracker.features.tracker.domain.use_case.GetFoodsForDateUseCase
import com.elian.calorietracker.features.tracker.presentation.search.SearchKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.ScopedServices
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class TrackerOverviewViewModel(
	private val backstack: Backstack,
	private val getFoodsForDate: GetFoodsForDateUseCase,
	private val deleteTrackedFood: DeleteTrackedFoodUseCase,
	private val calculateMealNutrients: CalculateMealNutrientsUseCase,
	private val serviceScope: ServiceScope = ServiceScope(),
) : ScopedServices.Registered by serviceScope {

	private val _eventFlow = MutableSharedFlow<TrackerOverviewEvent>()
	val eventFlow = _eventFlow.asSharedFlow()

	private val _state = MutableStateFlow(TrackerOverviewState())
	val state = _state.asStateFlow()

	private var _foodsForDateJob: Job? = null


	init {
		serviceScope.launch {
			_state
				.map { it.date }
				.distinctUntilChanged()
				.collect { date ->
					setFoodsForDate(date)
				}
		}
	}


	fun onAction(action: TrackerOverviewAction) {
		when (action) {
			is TrackerOverviewAction.SelectPreviousDay -> {
				_state.update {
					it.copy(
						date = it.date.minusDays(1)
					)
				}
			}
			is TrackerOverviewAction.SelectNextDay     -> {
				_state.update {
					it.copy(
						date = it.date.plusDays(1)
					)
				}
			}
			is TrackerOverviewAction.AddMeal           -> {
				backstack.goTo(
					SearchKey(
						date = _state.value.date,
						mealType = action.mealType,
					)
				)
			}
			is TrackerOverviewAction.DeleteTrackedFood -> {
				serviceScope.launch {
					deleteTrackedFood(action.trackedFood)
				}
			}
		}
	}

	private fun setFoodsForDate(date: LocalDate) {
		_foodsForDateJob?.cancel()

		_foodsForDateJob = serviceScope.launch {
			getFoodsForDate(date)
				.collect { foods ->
					val result = calculateMealNutrients(foods)

					val previousFoodsForDate = _state.value.trackedFoods
					val wasFoodAdded = foods.size > previousFoodsForDate.size
					if (wasFoodAdded) {
						//onFoodWasAdded()
					}

					_state.update { state ->
						state.copy(
							trackedFoods = foods,
							nutrients = NutrientsState(
								totalCarbsInGrams = result.totalCarbsInGrams,
								totalProteinInGrams = result.totalProteinInGrams,
								totalFatInGrams = result.totalFatInGrams,
								totalCaloriesInKcal = result.totalCaloriesInKcal,
								carbsGoalInGrams = result.carbsGoalInGrams,
								proteinGoalInGrams = result.proteinGoalInGrams,
								fatGoalInGrams = result.fatGoalInGrams,
								caloriesGoalInKcal = result.caloriesGoalInKcal,
							),
							meals = state.meals.map { meal ->
								val nutrientsForMeal = result.mealNutrients[meal.mealType]
									?: return@map meal.copy(
										carbsInGrams = 0,
										proteinInGrams = 0,
										fatInGrams = 0,
										caloriesInKcal = 0,
									)

								meal.copy(
									carbsInGrams = nutrientsForMeal.totalCarbsInGrams,
									proteinInGrams = nutrientsForMeal.totalProteinInGrams,
									fatInGrams = nutrientsForMeal.totalFatInGrams,
									caloriesInKcal = nutrientsForMeal.totalCaloriesInKcal,
								)
							}
						)
					}
				}
		}
	}

	// I decided that it's better to preserve the scroll position when a new food is added.
	private fun onFoodWasAdded() {
		serviceScope.launch {
			_eventFlow.emit(TrackerOverviewEvent.ScrollToTop)
		}
	}
}

