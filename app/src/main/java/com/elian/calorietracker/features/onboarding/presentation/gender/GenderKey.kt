package com.elian.calorietracker.features.onboarding.presentation.gender

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
data object GenderKey : FragmentKey() {

	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {
		@Composable
		override fun Content() {

			val onboardingViewModel = rememberService<OnboardingViewModel>()

			val state by onboardingViewModel.state.collectAsStateWithLifecycle()

			GenderScreen(
				selectedGender = state.gender,
				onGenderSelected = { gender ->
					onboardingViewModel.onAction(OnboardingAction.SelectGender(gender))
				},
				onNextClick = {
					onboardingViewModel.onAction(OnboardingAction.Next)
				},
			)
		}
	}
}