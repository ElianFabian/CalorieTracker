package com.elian.calorietracker.di

import android.app.Activity
import android.app.Application
import android.content.Context
import com.elian.calorietracker.CalorieTrackerApp
import com.elian.calorietracker.core.data.ResourceManagerImpl
import com.elian.calorietracker.core.data.app_preferences.AppPreferencesImpl
import com.elian.calorietracker.core.data.app_preferences.dataStore
import com.elian.calorietracker.core.domain.ResourceManager
import com.elian.calorietracker.core.domain.app_preferences.AppPreferences
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/*
 * Not all the code in here is really used in the project,
 * they are just here for the sake of learning.
 */

private const val ApplicationContextTag = "applicationContext"
private const val ApplicationScopeTag = "applicationScope"

fun Application.provideGlobalServices(): GlobalServices {

	val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

	val resourceManager: ResourceManager = ResourceManagerImpl(this)
	val preferences: AppPreferences = AppPreferencesImpl(dataStore)

	return GlobalServices.builder()
		.add(applicationContext, ApplicationContextTag)
		.add(applicationScope, ApplicationScopeTag)
		.add(preferences)
		.add(resourceManager)
		.build()
}

fun ServiceBinder.lookupApplicationContext(): Context {
	return lookup(ApplicationContextTag)
}

fun ServiceBinder.lookupApplicationScope(): CoroutineScope {
	return lookup(ApplicationScopeTag)
}

inline val Activity.globalServices: GlobalServices
	get() {
		return (application as CalorieTrackerApp).globalServices
	}