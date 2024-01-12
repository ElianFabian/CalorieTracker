package com.elian.calorietracker.features.onboarding.presentation.welcome

import androidx.compose.runtime.Composable
import com.elian.calorietracker.core.presentation.simplestack.ComposeFragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.features.onboarding.presentation.OnboardingAction
import com.elian.calorietracker.features.onboarding.di.OnboardingServiceModule
import com.elian.calorietracker.features.onboarding.presentation.OnboardingViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data object WelcomeKey : ComposeFragmentKey(
	serviceModule = OnboardingServiceModule,
) {
	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {
		@Composable
		override fun Content() {

			val onboardingViewModel = rememberService<OnboardingViewModel>()

			WelcomeScreen(
				onNextClick = {
					onboardingViewModel.onAction(OnboardingAction.Next)
				},
			)
		}
	}
}