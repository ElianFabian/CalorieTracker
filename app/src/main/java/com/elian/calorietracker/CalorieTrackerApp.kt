package com.elian.calorietracker

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.core.os.ConfigurationCompat
import com.elian.calorietracker.core.util.AppBuildConfig
import com.elian.calorietracker.core.util.setupLocale
import com.elian.calorietracker.di.provideGlobalServices
import com.zhuinden.simplestack.GlobalServices
import java.util.Locale

class CalorieTrackerApp : Application() {

	lateinit var globalServices: GlobalServices
		private set


	override fun onCreate() {
		super.onCreate()

		setupLocale()

		globalServices = provideGlobalServices()
	}
}
