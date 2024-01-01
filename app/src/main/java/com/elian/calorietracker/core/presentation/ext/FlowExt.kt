package com.elian.calorietracker.core.presentation.ext

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@SuppressLint("ComposableNaming")
@Composable
inline fun <T> Flow<T>.collectAsEffect(
	crossinline action: suspend (T) -> Unit,
) {
	LaunchedEffect(Unit) {
		withContext(Dispatchers.Main.immediate) {
			collect { value ->
				action(value)
			}
		}
	}
}

@SuppressLint("ComposableNaming")
@Composable
inline fun <T> Flow<T>.collectAsEffectWithLifecycle(
	minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
	crossinline action: suspend (T) -> Unit,
) {
	val lifecycle = LocalLifecycleOwner.current.lifecycle

	LaunchedEffect(Unit) {
		withContext(Dispatchers.Main.immediate) {
			flowWithLifecycle(lifecycle, minActiveState)
			collect { value ->
				action(value)
			}
		}
	}
}