package com.elian.calorietracker

import android.app.Application
import com.elian.calorietracker.core.util.setupLocale
import com.elian.calorietracker.di.provideGlobalServices
import com.zhuinden.simplestack.GlobalServices

class CalorieTrackerApp : Application() {

	lateinit var globalServices: GlobalServices
		private set


	override fun onCreate() {
		super.onCreate()

		setupLocale()

		globalServices = provideGlobalServices()
	}
}
