package com.elian.calorietracker.features.onboarding.presentation.goal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elian.calorietracker.core.presentation.simplestack.ComposeFragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.features.onboarding.presentation.OnboardingAction
import com.elian.calorietracker.features.onboarding.presentation.OnboardingViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data object GoalKey : ComposeFragmentKey() {

	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {

		@Composable
		override fun Content() {
			val onboardingViewModel = rememberService<OnboardingViewModel>()

			val state by onboardingViewModel.state.collectAsStateWithLifecycle()

			GoalScreen(
				goalType = state.goalType,
				onGoalTypeSelected = { goalType ->
					onboardingViewModel.onAction(OnboardingAction.SelectGoalType(goalType))
				},
				onNextClick = {
					onboardingViewModel.onAction(OnboardingAction.Next)
				},
			)
		}
	}
}