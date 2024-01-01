package com.elian.calorietracker.core.presentation.ext

import androidx.compose.ui.geometry.Offset

private val offsetOne = Offset(1F, 1F)
private val offsetUp = Offset(0F, -1F)
private val offsetDown = Offset(0F, 1F)
private val offsetRight = Offset(1F, 0F)
private val offsetLeft = Offset(-1F, 0F)

val Offset.Companion.One: Offset get() = offsetOne

val Offset.Companion.Up: Offset get() = offsetUp

val Offset.Companion.Down: Offset get() = offsetDown

val Offset.Companion.Right: Offset get() = offsetRight

val Offset.Companion.Left: Offset get() = offsetLeft