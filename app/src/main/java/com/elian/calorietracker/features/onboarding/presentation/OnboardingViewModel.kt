package com.elian.calorietracker.features.onboarding.presentation

import com.elian.calorietracker.R
import com.elian.calorietracker.core.domain.model.UserData
import com.elian.calorietracker.core.util.UiText
import com.elian.calorietracker.core.util.ext.filterDigits
import com.elian.calorietracker.core.util.simplestack.ServiceScope
import com.elian.calorietracker.di.AppPreferences
import com.elian.calorietracker.features.onboarding.OnboardingAction
import com.elian.calorietracker.features.onboarding.presentation.activity_level.ActivityLevelKey
import com.elian.calorietracker.features.onboarding.presentation.age.AgeKey
import com.elian.calorietracker.features.onboarding.presentation.gender.GenderKey
import com.elian.calorietracker.features.onboarding.presentation.goal.GoalKey
import com.elian.calorietracker.features.onboarding.presentation.height.HeightKey
import com.elian.calorietracker.features.onboarding.presentation.nutrient_goal.NutrientGoalKey
import com.elian.calorietracker.features.onboarding.presentation.weight.WeightKey
import com.elian.calorietracker.features.onboarding.presentation.welcome.WelcomeKey
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.TrackerOverviewKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.statebundle.StateBundle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
	private val backstack: Backstack,
	private val preferences: AppPreferences,
	private val serviceScope: ServiceScope = ServiceScope(),
) : ScopedServices.Registered by serviceScope,
	Bundleable {

	private val _messageFlow = MutableSharedFlow<UiText>()
	val messageFlow = _messageFlow.asSharedFlow()

	private val _state = MutableStateFlow(OnboardingState())
	val state = _state.asStateFlow()


	fun onAction(action: OnboardingAction) {
		when (action) {
			is OnboardingAction.SelectGender        -> {
				_state.update {
					it.copy(gender = action.gender)
				}
			}
			is OnboardingAction.EnterAge            -> {
				_state.update {
					it.copy(age = action.age.filterDigits())
				}
			}
			is OnboardingAction.EnterHeight         -> {
				_state.update {
					it.copy(height = action.height.filterDigits())
				}
			}
			is OnboardingAction.EnterWeight         -> {
				_state.update {
					it.copy(weight = action.weight.filterDigits())
				}
			}
			is OnboardingAction.SelectActivityLevel -> {
				_state.update {
					it.copy(activityLevel = action.activityLevel)
				}
			}
			is OnboardingAction.SelectGoalType      -> {
				_state.update {
					it.copy(goalType = action.goalType)
				}
			}
			is OnboardingAction.EnterCarbsRatio     -> {
				_state.update { state ->
					state.copy(carbsRatio = action.carbsRatio)
				}
			}
			is OnboardingAction.EnterProteinRatio   -> {
				_state.update { state ->
					state.copy(proteinRatio = action.proteinRatio)
				}
			}
			is OnboardingAction.EnterFatRatio       -> {
				_state.update { state ->
					state.copy(fatRatio = action.fatRatio)
				}
			}
			is OnboardingAction.Next                -> {
				val topKey = backstack.top<DefaultFragmentKey>()

				val state = state.value

				when (topKey) {
					is WelcomeKey       -> {
						backstack.goTo(GenderKey)
					}
					is GenderKey        -> {
						backstack.goTo(AgeKey)
					}
					is AgeKey           -> {
						if (state.age.isBlank()) {
							showMessage(UiText(R.string.PleaseEnterYourAge))
							return
						}
						backstack.goTo(HeightKey)
					}
					is HeightKey        -> {
						if (state.height.isBlank()) {
							showMessage(UiText(R.string.PleaseEnterYourHeight))
							return
						}
						backstack.goTo(WeightKey)
					}
					is WeightKey        -> {
						if (state.weight.isBlank()) {
							showMessage(UiText(R.string.PleaseEnterYourWeight))
							return
						}
						backstack.goTo(ActivityLevelKey)
					}
					is ActivityLevelKey -> {
						backstack.goTo(GoalKey)
					}
					is GoalKey          -> {
						backstack.goTo(NutrientGoalKey)
					}
				}
			}
			is OnboardingAction.Finish              -> {
				val state = state.value

				if (
					state.carbsRatio.isBlank()
					|| state.proteinRatio.isBlank()
					|| state.fatRatio.isBlank()
				) {
					showMessage(UiText(R.string.PleaseEnterYourNutrientGoals))
					return
				}

				serviceScope.launch {
					preferences.updateData { data ->
						val userData = state.run {
							UserData(
								gender = gender,
								age = age.toInt(),
								heightInCm = height.toInt(),
								weightInKg = weight.toFloat(),
								activityLevel = activityLevel,
								goalType = goalType,
								carbsRatio = carbsRatio.toFloat() / 100F,
								proteinsRatio = proteinRatio.toFloat() / 100F,
								fatRatio = fatRatio.toFloat() / 100F,
							)
						}

						data.copy(
							showOnboarding = false,
							user = userData,
						)
					}

					backstack.setHistory(listOf(TrackerOverviewKey), StateChange.REPLACE)
				}
			}
		}
	}


	private fun showMessage(message: UiText) {
		serviceScope.launch {
			_messageFlow.emit(message)
		}
	}

	override fun fromBundle(bundle: StateBundle?) {
		val restoredState = bundle?.getParcelable<OnboardingState>("state") ?: return

		_state.value = restoredState
	}

	override fun toBundle(): StateBundle {
		return StateBundle().apply {
			putParcelable("state", _state.value)
		}
	}
}