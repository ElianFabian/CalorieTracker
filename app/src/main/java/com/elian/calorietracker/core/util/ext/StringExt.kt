package com.elian.calorietracker.core.util.ext

fun String.filterDigits(): String {
	return filter { it.isDigit() }
}