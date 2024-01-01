package com.elian.calorietracker

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.elian.calorietracker.core.presentation.RootContainer
import com.elian.calorietracker.di.dataStore
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

	private val backPressedCallback = object : OnBackPressedCallback(false) {
		override fun handleOnBackPressed() {
			backstack.goBack()
		}
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		onBackPressedDispatcher.addCallback(backPressedCallback)

		setContent {
			RootContainer()
		}

		val container = androidContentFrame.apply { 
			id = R.id.MainFragmentContainer
		}

		val fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, container.id)

		val showOnboarding = runBlocking {
			dataStore.data.first().showOnboarding
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