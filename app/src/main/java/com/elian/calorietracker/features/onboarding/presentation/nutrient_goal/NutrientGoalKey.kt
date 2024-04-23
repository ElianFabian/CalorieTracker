package com.elian.calorietracker.features.onboarding.presentation.nutrient_goal

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elian.calorietracker.core.presentation.ext.collectAsEffectWithLifecycle
import com.elian.calorietracker.core.presentation.simplestack.FragmentKey
import com.elian.calorietracker.core.presentation.simplestack.ComposeKeyedFragment
import com.elian.calorietracker.core.util.ext.simplestack.rememberService
import com.elian.calorietracker.features.onboarding.presentation.OnboardingAction
import com.elian.calorietracker.features.onboarding.presentation.OnboardingViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data object NutrientGoalKey : FragmentKey() {

	override fun instantiateFragment() = Fragment()

	class Fragment : ComposeKeyedFragment() {

		@Composable
		override fun Content() {
			val onboardingViewModel = rememberService<OnboardingViewModel>()

			onboardingViewModel.messageFlow
				.collectAsEffectWithLifecycle { message ->
					Toast.makeText(context, message.asString(requireContext()), Toast.LENGTH_SHORT).show()
				}

			val state by onboardingViewModel.state.collectAsStateWithLifecycle()

			NutrientGoalScreen(
				carbsRatio = state.carbsRatio,
				proteinRatio = state.proteinRatio,
				fatRatio = state.fatRatio,
				onCarbsRationChange = { carbsRation ->
					onboardingViewModel.onAction(OnboardingAction.EnterCarbsRatio(carbsRation))
				},
				onProteinRationChange = { proteinRation ->
					onboardingViewModel.onAction(OnboardingAction.EnterProteinRatio(proteinRation))
				},
				onFatRationChange = { fatRation ->
					onboardingViewModel.onAction(OnboardingAction.EnterFatRatio(fatRation))
				},
				onFinish = {
					onboardingViewModel.onAction(OnboardingAction.Finish)
				},
			)
		}
	}
}