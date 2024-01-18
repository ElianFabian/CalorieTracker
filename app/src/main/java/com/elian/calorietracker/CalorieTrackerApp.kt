package com.elian.calorietracker

import android.app.Application
import com.elian.calorietracker.core.util.setupLocale
import com.elian.calorietracker.di.provideGlobalServices
import com.zhuinden.simplestack.GlobalServices

/* TODO List:
    - Implement a detail screen for the tracked food.
    - Implement allow multiple tracked foods to be added at once.
    - Implement a screen to edit user information.
    - Fix ui issues.
    - Learn about data store migrations.
    - Improve TrackerOverviewScreen performance.
    - Implement Timber?
    - Implement export data functionality?
    - Implement tests?
 */

class CalorieTrackerApp : Application() {

	lateinit var globalServices: GlobalServices
		private set


	override fun onCreate() {
		super.onCreate()

		setupLocale()

		globalServices = provideGlobalServices()
	}
}
