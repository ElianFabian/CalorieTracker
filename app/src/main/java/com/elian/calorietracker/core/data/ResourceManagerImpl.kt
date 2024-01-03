package com.elian.calorietracker.core.data

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.elian.calorietracker.core.domain.ResourceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResourceManagerImpl(
	private val context: Context,
) : ResourceManager {

	private val _stateFlows = mutableMapOf<TypedResource, MutableStateFlow<in Any?>>()


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

	override fun getDrawable(@DrawableRes id: Int): Drawable? {
		return ContextCompat.getDrawable(context, id)
	}

	override fun getIntStateFlow(@IntegerRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Integer(id),
		getResource = ::getInt,
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
		typedResource = TypedResource.Dimension(id),
		getResource = ::getDimensionPixelOffset,
	)

	override fun getDimensionPixelSizeStateFlow(@DimenRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Dimension(id),
		getResource = ::getDimensionPixelSize,
	)

	override fun getBooleanStateFlow(@BoolRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Boolean(id),
		getResource = ::getBoolean,
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
		typedResource = TypedResource.Plurals(id, quantity),
		getResource = { getQuantityString(id, quantity) },
	)

	override fun getTextStateFlow(@StringRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.String(id),
		getResource = ::getText,
	)

	override fun getQuantityTextStateFlow(
		@PluralsRes
		id: Int,
		quantity: Int,
	) = getOrCreateStateFlow(
		typedResource = TypedResource.Plurals(id, quantity),
		getResource = { getQuantityText(id, quantity) },
	)

	override fun getIntArrayStateFlow(@ArrayRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Array(id),
		getResource = ::getIntArray,
	)

	override fun getStringArrayStateFlow(@ArrayRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Array(id),
		getResource = ::getStringArray,
	)

	override fun getDrawableStateFlow(@DrawableRes id: Int) = getOrCreateStateFlow(
		typedResource = TypedResource.Drawable(id),
		getResource = ::getDrawable,
	)


	private fun update() {
		_stateFlows.forEach { (typedResource, stateFlow) ->
			stateFlow.value = when (typedResource) {
				is TypedResource.Integer   -> getInt(typedResource.id)
				is TypedResource.Color     -> getColor(typedResource.id)
				is TypedResource.Dimension -> getDimension(typedResource.id)
				is TypedResource.Boolean   -> getBoolean(typedResource.id)
				is TypedResource.String    -> getString(typedResource.id)
				is TypedResource.Plurals   -> getQuantityString(typedResource.id, typedResource.quantity)
				is TypedResource.Array     -> getStringArray(typedResource.id)
				is TypedResource.Drawable  -> getDrawable(typedResource.id)
			}
		}
	}

	private inline fun <T> getOrCreateStateFlow(
		typedResource: TypedResource,
		getResource: (id: Int) -> T,
	): StateFlow<T> {
		if (typedResource in _stateFlows) {
			return getFlow(typedResource)
		}

		val value = getResource(typedResource.id)

		val stateFlow = MutableStateFlow(value)

		addStateFlow(typedResource, stateFlow)

		return stateFlow
	}

	@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
	private inline fun addStateFlow(typedResource: TypedResource, stateFlow: MutableStateFlow<*>) {
		_stateFlows[typedResource] = stateFlow as MutableStateFlow<in Any?>
	}

	@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
	private inline fun <T> getFlow(typedResource: TypedResource): StateFlow<T> {
		return _stateFlows[typedResource] as StateFlow<T>
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
	value class String(@StringRes override val id: Int) : TypedResource

	@JvmInline
	value class Plurals private constructor(
		private val packedValue: Long
	) : TypedResource {

		@get:PluralsRes
		override val id: Int get() = packedValue.toInt()

		inline val quantity: Int get() = (packedValue shr 32).toInt()

		constructor(
			@PluralsRes
			id: Int,
			quantity: Int,
		) : this(
			packedValue = id.toLong() shl 32 or quantity.toLong(),
		)
	}

	@JvmInline
	value class Array(@ArrayRes override val id: Int) : TypedResource

	@JvmInline
	value class Drawable(@DrawableRes override val id: Int) : TypedResource
}