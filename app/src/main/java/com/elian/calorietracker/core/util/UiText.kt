@file:Suppress("NOTHING_TO_INLINE")

package com.elian.calorietracker.core.util

import android.content.Context
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

sealed interface UiText

@JvmInline
private value class DynamicString(val value: String) : UiText

private data class StringResource(
	@StringRes
	val resId: Int,
	val args: List<UiTextArg?>,
) : UiText

private data class PluralsResource(
	@PluralsRes
	val resId: Int,
	val quantity: Int,
	val args: List<UiTextArg?>,
) : UiText


private val emptyUiText: UiText = DynamicString("")

fun emptyUiText(): UiText = emptyUiText

fun UiText(value: String): UiText = when {
	value.isEmpty() -> emptyUiText()
	else            -> DynamicString(value)
}

@JvmName("UiTextNullable")
inline fun UiText(value: String?): UiText? {
	if (value == null) {
		return null
	}

	return UiText(value)
}

fun UiText(
	@StringRes
	resId: Int,
	args: List<UiTextArg?> = emptyList(),
): UiText = StringResource(
	resId = resId,
	args = args,
)

fun UiText(
	@PluralsRes
	resId: Int,
	quantity: Int,
	args: List<UiTextArg?> = emptyList(),
): UiText = PluralsResource(
	resId = resId,
	quantity = quantity,
	args = args,
)

fun UiText.toCharSequence(context: Context): CharSequence {
	return when (this) {
		is DynamicString   -> value
		is StringResource  -> {
			val arguments = args.map { arg ->
				arg?.getValue(context)
			}.toTypedArray()

			SpanFormatter.format(
				format = context.getText(resId),
				args = arguments,
			)
		}
		is PluralsResource -> {
			val arguments = args.map { arg ->
				arg?.getValue(context)
			}.toTypedArray()

			SpanFormatter.format(
				format = context.resources.getQuantityText(resId, quantity),
				args = arguments,
			)
		}
	}
}

inline fun UiText.toString(context: Context): String {
	return toCharSequence(context).toString()
}

inline fun Collection<UiText>.joinToCharSequence(
	context: Context,
	separator: CharSequence = "\n",
	prefix: CharSequence = "",
	postfix: CharSequence = "",
	limit: Int = -1,
	truncated: CharSequence = "...",
): String? {
	if (isEmpty()) {
		return null
	}

	return joinToString(
		separator = separator,
		prefix = prefix,
		postfix = postfix,
		limit = limit,
		truncated = truncated,
	) { uiText ->
		uiText.toCharSequence(context)
	}
}

inline fun Collection<UiText>.joinToString(
	context: Context,
	separator: CharSequence = "\n",
	prefix: CharSequence = "",
	postfix: CharSequence = "",
	limit: Int = -1,
	truncated: CharSequence = "...",
): String? {
	if (isEmpty()) {
		return null
	}

	return joinToString(
		separator = separator,
		prefix = prefix,
		postfix = postfix,
		limit = limit,
		truncated = truncated,
	) { uiText ->
		uiText.toString(context)
	}
}


sealed interface UiTextArg

@JvmInline
private value class ValueArg(val value: Any) : UiTextArg

@JvmInline
private value class BooleanResourceArg(@BoolRes val resId: Int) : UiTextArg

@JvmInline
private value class IntegerResourceArg(@IntegerRes val resId: Int) : UiTextArg

private data class StringResourceArg(
	@StringRes
	val resId: Int,
	val args: List<UiTextArg?>,
) : UiTextArg

private data class PluralsResourceArg(
	@PluralsRes
	val resId: Int,
	val quantity: Int,
	val args: List<UiTextArg?>,
) : UiTextArg

private fun UiTextArg.getValue(context: Context): Any {
	val resources = context.resources

	return when (this) {
		is ValueArg           -> value
		is BooleanResourceArg -> resources.getBoolean(resId)
		is IntegerResourceArg -> resources.getInteger(resId)
		is StringResourceArg  -> {
			SpanFormatter.format(
				format = resources.getText(resId),
				args = args.map { arg -> arg?.getValue(context) }.toTypedArray(),
			)
		}
		is PluralsResourceArg -> {
			SpanFormatter.format(
				format = resources.getQuantityText(resId, quantity),
				args = args.map { arg -> arg?.getValue(context) }.toTypedArray(),
			)
		}
	}
}

fun stringResArg(
	@StringRes
	resId: Int,
	args: List<UiTextArg?> = emptyList(),
): UiTextArg = StringResourceArg(resId, args)

fun pluralsResArg(
	@PluralsRes
	resId: Int,
	quantity: Int,
	args: List<UiTextArg?> = emptyList(),
): UiTextArg = PluralsResourceArg(resId, quantity, args)

fun integerResArg(@IntegerRes resId: Int): UiTextArg = IntegerResourceArg(resId)

fun booleanResArg(@BoolRes resId: Int): UiTextArg = BooleanResourceArg(resId)


private inline fun valueAsArg(value: Any?): UiTextArg? = when (value) {
	null         -> null
	is UiTextArg -> value
	is String,
	is Boolean,
	is Char,
	is Byte,
	is Short,
	is Int,
	is Long,
	is Float,
	is Double    -> ValueArg(value)
	else         -> ValueArg(value.toString())
}

fun uiArgsOf(vararg args: Any?): List<UiTextArg?> = args.map { arg -> valueAsArg(arg) }


/*
* Copyright © 2014 George T. Steel
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
//https://github.com/george-steel/android-utils/blob/master/src/org/oshkimaadziig/george/androidutils/SpanFormatter.java
/**
 * Provides [String.format] style functions that work with [android.text.Spanned] strings and preserve formatting.
 *
 * @author George T. Steel
 */
private object SpanFormatter {
	private val FORMAT_SEQUENCE = "%([0-9]+\\$|<?)([^a-zA-z%]*)([[a-zA-Z%]&&[^tT]]|[tT][a-zA-Z])".toPattern()

	/**
	 * Version of [String.format] that works on [android.text.Spanned] strings to preserve rich text formatting.
	 * Both the `format` as well as any `%s args` can be Spanned and will have their formatting preserved.
	 * Due to the way [android.text.Spannable]s work, any argument's spans will can only be included **once** in the result.
	 * Any duplicates will appear as text only.
	 *
	 * @param format the format string (see [java.util.Formatter.format])
	 * @param args
	 * the list of arguments passed to the formatter. If there are
	 * more arguments than required by `format`,
	 * additional arguments are ignored.
	 * @return the formatted string (with spans).
	 */
	inline fun format(
		format: CharSequence?,
		vararg args: Any?,
	): android.text.SpannedString {
		return format(java.util.Locale.getDefault(), format, *args)
	}

	/**
	 * Version of [String.format] that works on [android.text.Spanned] strings to preserve rich text formatting.
	 * Both the `format` as well as any `%s args` can be Spanned and will have their formatting preserved.
	 * Due to the way [android.text.Spannable]s work, any argument's spans will can only be included **once** in the result.
	 * Any duplicates will appear as text only.
	 *
	 * @param locale
	 * the locale to apply; `null` value means no localization.
	 * @param format the format string (see [java.util.Formatter.format])
	 * @param args
	 * the list of arguments passed to the formatter.
	 * @return the formatted string (with spans).
	 * @see String.format
	 */
	fun format(
		locale: java.util.Locale,
		format: CharSequence?,
		vararg args: Any?,
	): android.text.SpannedString {
		val out = android.text.SpannableStringBuilder(format)
		var i = 0
		var argAt = -1
		while (i < out.length) {
			val m = FORMAT_SEQUENCE.matcher(out)
			if (!m.find(i))
				break
			i = m.start()
			val exprEnd = m.end()
			val argTerm = m.group(1)
			val modTerm = m.group(2)
			val typeTerm = m.group(3)
			var cookedArg: CharSequence
			when (typeTerm) {
				"%"  -> cookedArg = "%"
				"n"  -> cookedArg = "\n"
				else -> {
					val argIdx: Int = when (argTerm) {
						""   -> ++argAt
						"<"  -> argAt
						else -> argTerm!!.substring(0, argTerm.length - 1).toInt() - 1
					}
					val argItem = args[argIdx]
					cookedArg = if ((typeTerm == "s") && argItem is android.text.Spanned) {
						argItem
					}
					else String.format(locale, "%$modTerm$typeTerm", argItem)
				}
			}
			out.replace(i, exprEnd, cookedArg)
			i += cookedArg.length
		}
		return android.text.SpannedString(out)
	}
}