package com.elian.calorietracker.core.presentation.simplestack

import androidx.compose.runtime.Composable
import com.elian.calorietracker.core.util.ext.simplestack.BackstackProvider
import com.elian.calorietracker.ui.theme.CalorieTrackerTheme
import com.zhuinden.simplestack.Backstack

@Composable
fun BasePreview(
	content: @Composable () -> Unit,
) {
	BackstackProvider(backstack = Backstack()) {
		CalorieTrackerTheme {
			content()
		}
	}
}