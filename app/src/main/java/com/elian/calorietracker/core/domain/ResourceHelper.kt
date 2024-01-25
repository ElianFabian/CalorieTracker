package com.elian.calorietracker.core.domain

import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import kotlinx.coroutines.flow.StateFlow

/**
 * This interface is used to provide resources to the application.
 *
 * It can safely be used in classes like ViewModels.
 */
interface ResourceHelper {
	fun getInt(@IntegerRes id: Int): Int
	fun getBoolean(@BoolRes id: Int): Boolean
	fun getColor(@ColorRes id: Int): Int
	fun getDimension(@DimenRes id: Int): Float
	fun getDimensionPixelOffset(@DimenRes id: Int): Int
	fun getDimensionPixelSize(@DimenRes id: Int): Int
	fun getFloatOrNull(@DimenRes id: Int): Float?
	fun getString(@StringRes id: Int): String
	fun getQuantityString(@PluralsRes id: Int, quantity: Int): String
	fun getText(@StringRes id: Int): CharSequence
	fun getQuantityText(@PluralsRes id: Int, quantity: Int): CharSequence
	fun getIntArray(@ArrayRes id: Int): IntArray
	fun getStringArray(@ArrayRes id: Int): Array<String>
	fun getTextArray(@ArrayRes id: Int): Array<CharSequence>
	fun getDrawable(@DrawableRes id: Int): Drawable?

	fun getIntStateFlow(@IntegerRes id: Int): StateFlow<Int>
	fun getBooleanStateFlow(@BoolRes id: Int): StateFlow<Boolean>
	fun getColorStateFlow(@ColorRes id: Int): StateFlow<Int>
	fun getDimensionStateFlow(@DimenRes id: Int): StateFlow<Float>
	fun getDimensionPixelOffsetStateFlow(@DimenRes id: Int): StateFlow<Int>
	fun getDimensionPixelSizeStateFlow(@DimenRes id: Int): StateFlow<Int>
	fun getFloatStateFlow(@DimenRes id: Int): StateFlow<Float?>
	fun getStringStateFlow(@StringRes id: Int): StateFlow<String>
	fun getQuantityStringStateFlow(@PluralsRes id: Int, quantity: Int): StateFlow<String>
	fun getTextStateFlow(@StringRes id: Int): StateFlow<CharSequence>
	fun getQuantityTextStateFlow(@PluralsRes id: Int, quantity: Int): StateFlow<CharSequence>
	fun getIntArrayStateFlow(@ArrayRes id: Int): StateFlow<IntArray>
	fun getStringArrayStateFlow(@ArrayRes id: Int): StateFlow<Array<String>>
	fun getTextArrayStateFlow(@ArrayRes id: Int): StateFlow<Array<CharSequence>>
	fun getDrawableStateFlow(@DrawableRes id: Int): StateFlow<Drawable?>
}