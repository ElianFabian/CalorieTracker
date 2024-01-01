package com.elian.calorietracker.features.tracker.data.remote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elian.calorietracker.features.tracker.data.remote.local.converter.LocalDateConverter
import com.elian.calorietracker.features.tracker.data.remote.local.entity.TrackedFoodEntity

@Database(
	entities = [TrackedFoodEntity::class],
	version = 2,
)
@TypeConverters(
	LocalDateConverter::class,
)
abstract class TrackerDatabase : RoomDatabase() {

	abstract val trackerDao: TrackerDao
}