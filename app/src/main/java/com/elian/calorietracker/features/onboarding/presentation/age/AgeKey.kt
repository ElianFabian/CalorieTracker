package com.elian.calorietracker.features.onboarding.presentation.age

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elian.calorietracker.core.presentation.ext.collectAsEffectWithLifecycle
import com.elian.calorietracker.core.presentation.simplestack.ComposeFragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.core.util.toString
import com.elian.calorietracker.features.onboarding.presentation.OnboardingAction
import com.elian.calorietracker.features.onboarding.presentation.OnboardingViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data object AgeKey : ComposeFragmentKey() {

	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {

		@Composable
		override fun Content() {

			val onboardingViewModel = rememberService<OnboardingViewModel>()

			onboardingViewModel.messageFlow
				.collectAsEffectWithLifecycle { message ->
					Toast.makeText(context, message.toString(requireContext()), Toast.LENGTH_SHORT).show()
				}

			val state by onboardingViewModel.state.collectAsStateWithLifecycle()

			AgeScreen(
				age = state.age,
				onAgeChange = { age ->
					onboardingViewModel.onAction(OnboardingAction.EnterAge(age))
				},
				onNextClick = {
					onboardingViewModel.onAction(OnboardingAction.Next)
				},
			)
		}
	}
}
