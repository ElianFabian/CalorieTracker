package com.elian.calorietracker.features.tracker.data.remote.local.converter

import androidx.room.TypeConverter
import java.time.LocalDate

@Suppress("unused")
class LocalDateConverter {

	@TypeConverter
	fun fromLocalDate(localDate: LocalDate): Long {
		return localDate.toEpochDay()
	}

	@TypeConverter
	fun toLocalDate(epochDay: Long): LocalDate {
		return LocalDate.ofEpochDay(epochDay)
	}
}