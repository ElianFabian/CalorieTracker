package com.elian.calorietracker.features.tracker.data.remote.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class TrackedFoodEntity(
	val name: String,
	val imageUrl: String?,
	val date: LocalDate,
	val carbs: Int,
	val protein: Int,
	val fat: Int,
	val mealType: String,
	val amount: Int,
	val calories: Int,
	@PrimaryKey(autoGenerate = true)
	val id: Long? = null,
)