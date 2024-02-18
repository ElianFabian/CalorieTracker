package com.elian.calorietracker.core.data.app_preferences

import android.content.Context
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.elian.calorietracker.core.domain.model.AppPreferencesData
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.dataStore by dataStore(
	fileName = "app-preferences.json",
	serializer = AppPreferencesSerializer,
)

private object AppPreferencesSerializer : Serializer<AppPreferencesData> {

	override val defaultValue = AppPreferencesData()

	private val json = Json {
		encodeDefaults = true
	}

	override suspend fun readFrom(input: InputStream): AppPreferencesData {
		return try {
			json.decodeFromString(
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
			json.encodeToString(
				serializer = AppPreferencesData.serializer(),
				value = t,
			).toByteArray(Charsets.UTF_8)
		)
	}
}