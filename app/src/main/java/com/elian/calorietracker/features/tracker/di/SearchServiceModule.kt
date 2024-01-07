package com.elian.calorietracker.features.tracker.di

import com.elian.calorietracker.di.ServiceModule
import com.elian.calorietracker.features.tracker.data.remote.OpenFoodApi
import com.elian.calorietracker.features.tracker.data.remote.local.TrackerDatabase
import com.elian.calorietracker.features.tracker.domain.model.MealType
import com.elian.calorietracker.features.tracker.domain.use_case.SearchFoodUseCase
import com.elian.calorietracker.features.tracker.domain.use_case.TrackFoodUseCase
import com.elian.calorietracker.features.tracker.presentation.search.SearchViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.time.LocalDate

class SearchServiceModule(
	private val date: LocalDate,
	private val mealType: MealType,
) : ServiceModule {
	override fun bindServices(serviceBinder: ServiceBinder) {
		val okHttpClient = provideOkHttpClient()
		val openApiFood = provideOpenFoodApi(okHttpClient)
		val database = serviceBinder.lookup<TrackerDatabase>()

		val searchFood = SearchFoodUseCase(
			api = openApiFood,
		)
		val trackFood = TrackFoodUseCase(
			trackerDao = database.trackerDao,
		)

		val searchViewModel = SearchViewModel(
			dateArg = date,
			mealTypeArg = mealType,
			backstack = serviceBinder.backstack,
			searchFood = searchFood,
			trackFood = trackFood,
		)

		with(serviceBinder) {
			add(searchViewModel)
		}
	}
}


private fun provideOkHttpClient(): OkHttpClient {

	return OkHttpClient.Builder()
		.addInterceptor(
			HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			}
		)
		.build()
}

@OptIn(ExperimentalSerializationApi::class)
private fun provideOpenFoodApi(client: OkHttpClient): OpenFoodApi {

	val contentType = "application/json".toMediaType()

	val json = Json {
		ignoreUnknownKeys = true
		explicitNulls = false
	}

	return Retrofit.Builder()
		.baseUrl(OpenFoodApi.BASE_URL)
		.addConverterFactory(json.asConverterFactory(contentType))
		.client(client)
		.build()
		.create()
}