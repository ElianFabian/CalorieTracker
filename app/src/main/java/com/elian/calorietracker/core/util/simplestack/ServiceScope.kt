package com.elian.calorietracker.core.util.simplestack

import com.zhuinden.simplestack.ScopedServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class ServiceScope(
	private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
) : ScopedServices.Registered, CoroutineScope {

	override val coroutineContext: CoroutineContext = scope.coroutineContext

	override fun onServiceRegistered() {

	}

	override fun onServiceUnregistered() {
		scope.cancel()
	}
}