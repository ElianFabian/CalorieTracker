package com.elian.calorietracker.features.tracker.data.remote.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elian.calorietracker.features.tracker.data.remote.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TrackerDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

	@Delete
	suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

	@Query(
		"""
            SELECT *
            FROM TrackedFoodEntity
            WHERE date = :date
        """
	)
	fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFoodEntity>>
}