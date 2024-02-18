package com.elian.calorietracker.core.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.elian.calorietracker.R
import com.elian.calorietracker.core.data.app_preferences.dataStore
import com.elian.calorietracker.di.globalServices
import com.elian.calorietracker.features.onboarding.presentation.welcome.WelcomeKey
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.TrackerOverviewKey
import com.zhuinden.simplestack.BackHandlingModel
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.lifecyclektx.observeAheadOfTimeWillHandleBackChanged
import com.zhuinden.simplestackextensions.navigatorktx.androidContentFrame
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.get
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

	private val backPressedCallback = object : OnBackPressedCallback(false) {
		override fun handleOnBackPressed() {
			backstack.goBack()
		}
	}
	
	private val viewModel by lazy { globalServices.get<MainViewModel>() }


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		onBackPressedDispatcher.addCallback(backPressedCallback)

		val containerId = R.id.MainFragmentContainer

		setContent {
			RootContainer(
				containerId = containerId,
			)
		}

		val container = androidContentFrame.apply {
			id = containerId
		}

		val fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, containerId)

		val showOnboarding = runBlocking {
			viewModel.showOnboarding.first()
		}

		Navigator.configure()
			.setBackHandlingModel(BackHandlingModel.AHEAD_OF_TIME)
			.setStateChanger(
				SimpleStateChanger { stateChange ->
					fragmentStateChanger.handleStateChange(stateChange)
				}
			)
			.setScopedServices(DefaultServiceProvider())
			.setGlobalServices(globalServices)
			.install(
				this,
				container,
				History.single(
					if (showOnboarding) WelcomeKey else TrackerOverviewKey
				),
			)

		backPressedCallback.isEnabled = backstack.willHandleAheadOfTimeBack()
		backstack.observeAheadOfTimeWillHandleBackChanged(this) { willHandleBack ->
			backPressedCallback.isEnabled = willHandleBack
		}
	}
}