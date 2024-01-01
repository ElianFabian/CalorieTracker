package com.elian.calorietracker.core.util.simplestack

import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.StateChange
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Backstack.stateChangeFlow(): Flow<StateChange> = callbackFlow {
	val listener = Backstack.CompletionListener { stateChange: StateChange ->
		trySend(stateChange)
	}

	addStateChangeCompletionListener(listener)

	awaitClose {
		removeStateChangeCompletionListener(listener)
	}
}