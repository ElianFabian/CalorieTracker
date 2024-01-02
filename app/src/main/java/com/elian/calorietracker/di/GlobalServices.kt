package com.elian.calorietracker.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.elian.calorietracker.core.domain.model.AppPreferencesData
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

fun Application.provideGlobalServices(
	block: GlobalServices.Builder.() -> Unit = {},
): GlobalServices {

	return GlobalServices.builder()
		.add(dataStore)
		.add(applicationContext, "appContext")
		.apply(block)
		.build()
}

fun ServiceBinder.lookupAppContext(): Context {
	return lookup("appContext")
}


val Context.dataStore by dataStore(
	fileName = "app-preferences.json",
	serializer = AppPreferencesSerializer,
)

typealias AppPreferences = DataStore<AppPreferencesData>

private object AppPreferencesSerializer : Serializer<AppPreferencesData> {

	override val defaultValue = AppPreferencesData()

	override suspend fun readFrom(input: InputStream): AppPreferencesData {
		return try {
			Json.decodeFromString(
				deserializer = AppPreferencesData.serializer(),
				string = input.readBytes().decodeToString(),
			)
		}
		catch (e: Exception) {
			e.printStackTrace()
			defaultValue
		}
	}

	@Suppress("BlockingMethodInNonBlockingContext")
	override suspend fun writeTo(t: AppPreferencesData, output: OutputStream) {
		output.write(
			Json.encodeToString(
				serializer = AppPreferencesData.serializer(),
				value = t,
			).toByteArray(Charsets.UTF_8)
		)
	}
}