package com.elian.calorietracker.features.tracker

import android.content.Context
import androidx.room.Room
import com.elian.calorietracker.core.domain.app_preferences.AppPreferences
import com.elian.calorietracker.di.ServiceModule
import com.elian.calorietracker.di.lookupApplicationContext
import com.elian.calorietracker.features.tracker.data.remote.local.TrackerDatabase
import com.elian.calorietracker.features.tracker.domain.use_case.CalculateMealNutrientsUseCase
import com.elian.calorietracker.features.tracker.domain.use_case.DeleteTrackedFoodUseCase
import com.elian.calorietracker.features.tracker.domain.use_case.GetFoodsForDateUseCase
import com.elian.calorietracker.features.tracker.presentation.tracker_overview.TrackerOverviewViewModel
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup

object TrackerOverviewServiceModule : ServiceModule {
	override fun bindServices(serviceBinder: ServiceBinder) {

		val context = serviceBinder.lookupApplicationContext()
		val preferences = serviceBinder.lookup<AppPreferences>()

		val database = provideTrackerDatabase(context)
		val trackerDao = database.trackerDao

		val getFoodsForDate = GetFoodsForDateUseCase(
			trackerDao = trackerDao,
		)
		val deleteTrackedFood = DeleteTrackedFoodUseCase(
			trackerDao = trackerDao,
		)
		val calculateMealNutrients = CalculateMealNutrientsUseCase(
			preferences = preferences,
		)

		val viewModel = TrackerOverviewViewModel(
			backstack = serviceBinder.backstack,
			getFoodsForDate = getFoodsForDate,
			deleteTrackedFood = deleteTrackedFood,
			calculateMealNutrients = calculateMealNutrients,
		)

		with(serviceBinder) {
			add(viewModel)
			add(database)
		}
	}
}


private fun provideTrackerDatabase(context: Context): TrackerDatabase {
	return Room.databaseBuilder(
		context,
		TrackerDatabase::class.java,
		"tracker_db",
	).fallbackToDestructiveMigration()
		.build()
}