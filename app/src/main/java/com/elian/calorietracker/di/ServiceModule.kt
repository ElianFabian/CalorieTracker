package com.elian.calorietracker.di

import com.zhuinden.simplestack.ServiceBinder

interface ServiceModule {
	fun bindServices(serviceBinder: ServiceBinder)
}