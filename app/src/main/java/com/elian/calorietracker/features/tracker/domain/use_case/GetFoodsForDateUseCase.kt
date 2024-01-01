package com.elian.calorietracker.features.tracker.domain.use_case

import com.elian.calorietracker.features.tracker.data.remote.local.TrackerDao
import com.elian.calorietracker.features.tracker.data.remote.mapper.toTrackedFood
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetFoodsForDateUseCase(
	private val trackerDao: TrackerDao,
) {
	operator fun invoke(
		date: LocalDate
	) = trackerDao.getFoodsForDate(date)
		.map { trackedFoodEntities ->
			trackedFoodEntities.mapNotNull { entity ->
				entity.toTrackedFood()
			}
		}
}