package com.elian.calorietracker.features.tracker.domain.use_case

import com.elian.calorietracker.features.tracker.data.remote.local.TrackerDao
import com.elian.calorietracker.features.tracker.data.remote.mapper.toTrackedFoodEntity
import com.elian.calorietracker.features.tracker.domain.model.TrackedFood

class DeleteTrackedFoodUseCase(
	private val trackerDao: TrackerDao,
) {
	suspend operator fun invoke(trackedFood: TrackedFood) {
		trackerDao.deleteTrackedFood(trackedFood.toTrackedFoodEntity())
	}
}