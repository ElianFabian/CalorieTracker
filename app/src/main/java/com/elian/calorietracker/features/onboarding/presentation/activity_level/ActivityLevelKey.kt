package com.elian.calorietracker.features.onboarding.presentation.activity_level

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elian.calorietracker.core.presentation.simplestack.FragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.features.onboarding.presentation.OnboardingAction
import com.elian.calorietracker.features.onboarding.presentation.OnboardingViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data object ActivityLevelKey : FragmentKey() {
	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {

		@Composable
		override fun Content() {
			val onboardingViewModel = rememberService<OnboardingViewModel>()

			val state by onboardingViewModel.state.collectAsStateWithLifecycle()

			ActivityLevelScreen(
				activityLevel = state.activityLevel,
				onActivityLevelSelected = { level ->
					onboardingViewModel.onAction(OnboardingAction.SelectActivityLevel(level))
				},
				onNextClick = {
					onboardingViewModel.onAction(OnboardingAction.Next)
				},
			)
		}
	}
}