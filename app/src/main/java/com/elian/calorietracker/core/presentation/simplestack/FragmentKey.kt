package com.elian.calorietracker.core.presentation.simplestack

import android.os.Parcelable
import com.elian.calorietracker.di.ServiceModule
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

abstract class FragmentKey(
	private val serviceModule: ServiceModule? = null,
) : DefaultFragmentKey(), DefaultServiceProvider.HasServices {

	override fun getScopeTag(): String = toString()

	override fun bindServices(serviceBinder: ServiceBinder) {
		serviceModule?.bindServices(serviceBinder)
	}
}