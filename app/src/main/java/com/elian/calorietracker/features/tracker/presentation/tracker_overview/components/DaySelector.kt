package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.features.tracker.presentation.util.ext.toFormattedString
import java.time.LocalDate

@Composable
fun DaySelector(
	date: LocalDate,
	onPreviousDayClick: () -> Unit,
	onNextDayClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Row(
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.fillMaxWidth()
	) {
		IconButton(
			onClick = onPreviousDayClick,
		) {
			Icon(
				imageVector = Icons.Default.ArrowBack,
				contentDescription = stringResource(R.string.PreviousDay),
			)
		}
		Text(
			text = date.toFormattedString(),
			style = MaterialTheme.typography.titleMedium,
		)
		IconButton(
			onClick = onNextDayClick,
		) {
			Icon(
				imageVector = Icons.Default.ArrowForward,
				contentDescription = stringResource(R.string.NextDay),
			)
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	DaySelector(
		date = LocalDate.now(),
		onPreviousDayClick = {},
		onNextDayClick = {},
	)
}