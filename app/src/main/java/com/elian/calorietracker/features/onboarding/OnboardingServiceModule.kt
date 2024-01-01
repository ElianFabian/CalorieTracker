package com.elian.calorietracker.features.onboarding

import com.elian.calorietracker.di.ServiceModule
import com.elian.calorietracker.features.onboarding.presentation.OnboardingViewModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup

object OnboardingServiceModule : ServiceModule {
	override fun bindServices(serviceBinder: ServiceBinder) {
		val onboardingViewModel = OnboardingViewModel(
			backstack = serviceBinder.backstack,
			preferences = serviceBinder.lookup(),
		)

		with(serviceBinder) {
			add(onboardingViewModel)
		}
	}
}