package com.elian.calorietracker.di

import android.app.Application
import android.content.Context
import com.elian.calorietracker.core.data.app_preferences.AppPreferencesImpl
import com.elian.calorietracker.core.data.app_preferences.dataStore
import com.elian.calorietracker.core.domain.app_preferences.AppPreferences
import com.elian.calorietracker.core.util.ResourceManager
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

	val resourceManager = ResourceManager(this)

	val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

	val preferences: AppPreferences = AppPreferencesImpl(dataStore)

	return GlobalServices.builder()
		.add(preferences)
		.add(applicationContext, ApplicationContextTag)
		.add(resourceManager)
		.add(applicationScope, ApplicationScopeTag)
		.build()
}

fun ServiceBinder.lookupAppContext(): Context {
	return lookup(ApplicationContextTag)
}

fun ServiceBinder.lookupAppScope(): CoroutineScope {
	return lookup(ApplicationScopeTag)
}
