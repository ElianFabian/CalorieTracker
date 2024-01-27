package com.elian.calorietracker

import android.os.Parcelable
import androidx.test.platform.app.InstrumentationRegistry
import com.elian.calorietracker.core.util.UiText
import com.elian.calorietracker.core.util.booleanResArg
import com.elian.calorietracker.core.util.emptyUiText
import com.elian.calorietracker.core.util.integerResArg
import com.elian.calorietracker.core.util.stringResArg
import com.elian.calorietracker.core.util.toString
import com.elian.calorietracker.core.util.uiArgsOf
import com.elian.calorietracker.test.R
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class UiTextTest {

	private val context = InstrumentationRegistry.getInstrumentation().context


	@Test
	fun `Test-Dynamic-string`() {
		val source = "Hello, World!"
		val uiText = UiText(source)

		assertEquals(source, uiText.toString(context))
	}

	@Test
	fun `Test-Dynamic-string-with-null-value`() {
		val uiText = UiText(null)

		assertEquals(null, uiText)
	}

	@Test
	fun `Test-Dynamic-string-with-empty-value`() {
		val uiText = UiText("")

		val expected = emptyUiText()
		val actual = uiText

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource`() {
		val source = R.string.SimpleString
		val uiText = UiText(source)

		val expected = context.getString(source)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-string-arg`() {
		val arg = "Hello, World!"
		val source = R.string.StringWithStringParameter_param1
		val uiText = UiText(source, uiArgsOf(arg))

		val expected = context.getString(source, arg)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-int-arg`() {
		val arg = 42
		val source = R.string.StringWithIntParameter_param1
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(arg),
		)

		val expected = context.getString(source, arg)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-boolean-arg`() {
		val arg = true
		val source = R.string.StringWithBooleanParameter_param1
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(arg),
		)

		val expected = context.getString(source, arg)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-string-resource-arg`() {
		val stringRes = R.string.SimpleString
		val source = R.string.StringWithStringParameter_param1
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(
				stringResArg(stringRes),
			),
		)

		val expected = context.getString(source, context.getString(stringRes))
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-multiple-args`() {
		val arg1 = "Hello, World!"
		val arg2 = 42
		val arg3 = true
		val source = R.string.StringWithMultipleParameters_param1_param2_param3
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(
				arg1,
				arg2,
				arg3,
			),
		)

		val expected = context.getString(source, arg1, arg2, arg3)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-multiple-resource-args`() {
		val arg1 = R.string.SimpleString
		val arg2 = R.integer.SimpleInteger
		val arg3 = R.bool.SimpleBoolean
		val source = R.string.StringWithMultipleParameters_param1_param2_param3
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(
				stringResArg(arg1),
				integerResArg(arg2),
				booleanResArg(arg3),
			),
		)

		val expected = context.getString(
			source,
			context.getString(arg1),
			context.resources.getInteger(arg2),
			context.resources.getBoolean(arg3),
		)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-string-resource-arg-with-string-arg`() {
		val arg = R.string.ArgWithStringParameter_param1
		val argOfArg = "Hello, World!"
		val source = R.string.StringWithStringParameter_param1
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(
				stringResArg(
					resId = arg,
					args = uiArgsOf(argOfArg),
				),
			),
		)

		val expected = context.getString(
			source,
			context.getString(
				arg,
				argOfArg,
			),
		)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-string-resource-arg-with-int-arg`() {
		val arg = R.string.ArgWithIntParameter_param1
		val argOfArg = 42
		val source = R.string.StringWithStringParameter_param1
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(
				stringResArg(
					resId = arg,
					args = uiArgsOf(argOfArg),
				),
			),
		)

		val expected = context.getString(
			source,
			context.getString(
				arg,
				argOfArg,
			),
		)
		val actual = uiText.toString(context)
		
		println("$$$ test expected: $expected, actual: $actual")

		assertEquals(expected, actual)
	}
	@kotlinx.parcelize.Parcelize
	data class Person(val name: String, val age: Int) : Parcelable
	private fun uwu() {

		
		val source = R.string.StringWithStringParameter_param1
		val arg = Person("Elian", 18)
		
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(arg),
		)
		
		val expected = context.getString(source, arg)
		
		val actual = uiText.toString(context)
		
		println("$$$ uwu expected: $expected, actual: $actual")
		
		assertEquals(expected, actual)
	}

	@Test
	fun `Test-String-resource-with-string-resource-arg-with-boolean-arg`() {
		uwu()
		val arg = R.string.ArgWithBooleanParameter_param1
		val argOfArg = true
		val source = R.string.StringWithStringParameter_param1
		val uiText = UiText(
			resId = source,
			args = uiArgsOf(
				stringResArg(
					resId = arg,
					args = uiArgsOf(argOfArg),
				),
			),
		)

		val expected = context.getString(
			source,
			context.getString(
				arg,
				argOfArg,
			),
		)
		val actual = uiText.toString(context)

		assertEquals(expected, actual)
	}

	@Test
	fun `Test-Plural-string-resource`() {
		val quantity1 = 1
		val quantity2 = 2

		val source = R.plurals.SimplePlural
		val uiText1 = UiText(
			resId = source,
			quantity = quantity1,
		)
		val uiText2 = UiText(
			resId = source,
			quantity = quantity2,
		)

		val expected1 = context.resources.getQuantityString(source, quantity1)
		val actual1 = uiText1.toString(context)

		assertEquals(expected1, actual1)

		val expected2 = context.resources.getQuantityString(source, quantity2)
		val actual2 = uiText2.toString(context)

		assertEquals(expected2, actual2)
	}
}
