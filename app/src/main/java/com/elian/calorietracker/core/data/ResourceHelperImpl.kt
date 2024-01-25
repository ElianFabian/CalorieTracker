package com.elian.calorietracker.core.data

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.elian.calorietracker.core.domain.ResourceHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResourceHelperImpl(
	private val context: Context,
) : ResourceHelper {

	private val _stateFlowByResource = mutableMapOf<TypedResource, MutableStateFlow<in Any?>>()


	init {
		context.registerComponentCallbacks(object : ComponentCallbacks {
			override fun onConfigurationChanged(newConfig: Configuration) {
				update()
			}

			override fun onLowMemory() {}
		})
	}


	override fun getInt(@IntegerRes id: Int): Int {
		return context.resources.getInteger(id)
	}

	override fun getBoolean(@BoolRes id: Int): Boolean {
		return context.resources.getBoolean(id)
	}

	override fun getColor(@ColorRes id: Int): Int {
		return ContextCompat.getColor(context, id)
	}

	override fun getDimension(@DimenRes id: Int): Float {
		return context.resources.getDimension(id)
	}

	override fun getDimensionPixelOffset(@DimenRes id: Int): Int {
		return context.resources.getDimensionPixelOffset(id)
	}

	override fun getDimensionPixelSize(@DimenRes id: Int): Int {
		return context.resources.getDimensionPixelSize(id)
	}

	override fun getFloatOrNull(@DimenRes id: Int): Float? {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
			return null
		}
		return context.resources.getFloat(id)
	}

	override fun getString(@StringRes id: Int): String {
		return context.resources.getString(id)
	}

	override fun getQuantityString(
		@PluralsRes
		id: Int,
		quantity: Int,
	): String {
		return context.resources.getQuantityString(id, quantity)
	}

	override fun getText(@StringRes id: Int): CharSequence {
		return context.resources.getText(id)
	}

	override fun getQuantityText(
		@PluralsRes
		id: Int,
		quantity: Int,
	): CharSequence {
		return context.resources.getQuantityText(id, quantity)
	}

	override fun getIntArray(@ArrayRes id: Int): IntArray {
		return context.resources.getIntArray(id)
	}

	override fun getStringArray(@ArrayRes id: Int): Array<String> {
		return context.resources.getStringArray(id)
	}

	override fun getTextArray(@ArrayRes id: Int): Array<CharSequence> {
		return context.resources.getTextArray(id)
	}

	override fun getDrawable(@DrawableRes id: Int): Drawable? {
		return ContextCompat.getDrawable(context, id)
	}

	override fun getIntStateFlow(@IntegerRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Integer(id),
		getResource = ::getInt,
	)

	override fun getBooleanStateFlow(@BoolRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Boolean(id),
		getResource = ::getBoolean,
	)

	override fun getColorStateFlow(@ColorRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Color(id),
		getResource = ::getColor,
	)

	override fun getDimensionStateFlow(@DimenRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Dimension(id),
		getResource = ::getDimension,
	)

	override fun getDimensionPixelOffsetStateFlow(@DimenRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.DimensionPixelOffset(id),
		getResource = ::getDimensionPixelOffset,
	)

	override fun getDimensionPixelSizeStateFlow(@DimenRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.DimensionPixelSize(id),
		getResource = ::getDimensionPixelSize,
	)

	override fun getFloatStateFlow(@DimenRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Float(id),
		getResource = ::getFloatOrNull,
	)

	override fun getStringStateFlow(@StringRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.String(id),
		getResource = ::getString,
	)

	override fun getQuantityStringStateFlow(
		@PluralsRes
		id: Int,
		quantity: Int,
	) = getOrCreateStateFlow(
		typedResource = TypedResource.QuantityString(id, quantity),
		getResource = { getQuantityString(id, quantity) },
	)

	override fun getTextStateFlow(@StringRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Text(id),
		getResource = ::getText,
	)

	override fun getQuantityTextStateFlow(
		@PluralsRes
		id: Int,
		quantity: Int,
	) = getOrCreateStateFlow(
		typedResource = TypedResource.QuantityText(id, quantity),
		getResource = { getQuantityText(id, quantity) },
	)

	override fun getIntArrayStateFlow(@ArrayRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.IntArray(id),
		getResource = ::getIntArray,
	)

	override fun getStringArrayStateFlow(@ArrayRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.StringArray(id),
		getResource = ::getStringArray,
	)

	override fun getTextArrayStateFlow(@ArrayRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.TextArray(id),
		getResource = ::getTextArray,
	)

	override fun getDrawableStateFlow(@DrawableRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Drawable(id),
		getResource = ::getDrawable,
	)


	private fun update() {
		_stateFlowByResource.forEach { (typedResource, stateFlow) ->
			val id = typedResource.id

			stateFlow.value = when (typedResource) {
				is TypedResource.Integer              -> getInt(id)
				is TypedResource.Boolean              -> getBoolean(id)
				is TypedResource.Color                -> getColor(id)
				is TypedResource.Dimension            -> getDimension(id)
				is TypedResource.DimensionPixelOffset -> getDimensionPixelOffset(id)
				is TypedResource.DimensionPixelSize   -> getDimensionPixelSize(id)
				is TypedResource.Float                -> getFloatOrNull(id)
				is TypedResource.String               -> getString(id)
				is TypedResource.QuantityString       -> getQuantityString(id, typedResource.quantity)
				is TypedResource.Text                 -> getText(id)
				is TypedResource.QuantityText         -> getQuantityText(id, typedResource.quantity)
				is TypedResource.IntArray             -> getIntArray(id)
				is TypedResource.StringArray          -> getStringArray(id)
				is TypedResource.TextArray            -> getTextArray(id)
				is TypedResource.Drawable             -> getDrawable(id)
			}
		}
	}

	private inline fun <T> getOrCreateStateFlow(
		typedResource: TypedResource,
		getResource: (id: Int) -> T,
	): StateFlow<T> {
		if (typedResource in _stateFlowByResource) {
			return getFlow(typedResource)
		}

		val value = getResource(typedResource.id)

		val stateFlow = MutableStateFlow(value)

		addStateFlow(typedResource, stateFlow)

		return stateFlow
	}

	@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
	private inline fun addStateFlow(typedResource: TypedResource, stateFlow: MutableStateFlow<*>) {
		_stateFlowByResource[typedResource] = stateFlow as MutableStateFlow<in Any?>
	}

	@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
	private inline fun <T> getFlow(typedResource: TypedResource): StateFlow<T> {
		return _stateFlowByResource[typedResource] as StateFlow<T>
	}
}


private sealed interface TypedResource {

	val id: Int

	@JvmInline
	value class Integer(@IntegerRes override val id: Int) : TypedResource

	@JvmInline
	value class Boolean(@BoolRes override val id: Int) : TypedResource

	@JvmInline
	value class Color(@ColorRes override val id: Int) : TypedResource

	@JvmInline
	value class Dimension(@DimenRes override val id: Int) : TypedResource

	@JvmInline
	value class DimensionPixelOffset(@DimenRes override val id: Int) : TypedResource

	@JvmInline
	value class DimensionPixelSize(@DimenRes override val id: Int) : TypedResource

	@JvmInline
	value class Float(@DimenRes override val id: Int) : TypedResource

	@JvmInline
	value class String(@StringRes override val id: Int) : TypedResource

	@JvmInline
	value class QuantityString private constructor(
		private val packedValue: Long
	) : TypedResource {

		@get:PluralsRes
		override val id: Int get() = packedValue.toInt()

		val quantity: Int get() = (packedValue shr 32).toInt()

		constructor(
			@PluralsRes
			id: Int,
			quantity: Int,
		) : this(
			packedValue = id.toLong() shl 32 or quantity.toLong(),
		)
	}

	@JvmInline
	value class Text(@StringRes override val id: Int) : TypedResource

	@JvmInline
	value class QuantityText private constructor(
		private val packedValue: Long
	) : TypedResource {

		@get:PluralsRes
		override val id: Int get() = packedValue.toInt()

		val quantity: Int get() = (packedValue shr 32).toInt()

		constructor(
			@PluralsRes
			id: Int,
			quantity: Int,
		) : this(
			packedValue = id.toLong() shl 32 or quantity.toLong(),
		)
	}

	@JvmInline
	value class IntArray(@ArrayRes override val id: Int) : TypedResource

	@JvmInline
	value class StringArray(@ArrayRes override val id: Int) : TypedResource

	@JvmInline
	value class TextArray(@ArrayRes override val id: Int) : TypedResource

	@JvmInline
	value class Drawable(@DrawableRes override val id: Int) : TypedResource
}