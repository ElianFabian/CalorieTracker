package com.elian.calorietracker.features.tracker.presentation.util.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.elian.calorietracker.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun LocalDate.toFormattedString(
	formatter: DateTimeFormatter = defaultDateTimeFormatter,
): String {
	val today = LocalDate.now()

	return when (this) {
		today              -> stringResource(R.string.Today)
		today.minusDays(1) -> stringResource(R.string.Yesterday)
		today.plusDays(1)  -> stringResource(R.string.Tomorrow)
		else               -> defaultDateTimeFormatter.format(this)
	}
}

private val defaultDateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM")