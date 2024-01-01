package com.elian.calorietracker.core.util.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.elian.calorietracker.core.util.UiText
import com.elian.calorietracker.core.util.toCharSequence
import com.elian.calorietracker.core.util.toString

@Composable
fun UiText.toCharSequence(): CharSequence {
	return toCharSequence(LocalContext.current)
}

@Composable
fun UiText.contentToString(): String {
	return toString(LocalContext.current)
}