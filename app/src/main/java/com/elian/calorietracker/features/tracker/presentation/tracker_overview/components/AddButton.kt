package com.elian.calorietracker.features.tracker.presentation.tracker_overview.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun AddButton(
	modifier: Modifier = Modifier,
	text: String,
	onClick: () -> Unit,
	color: Color = MaterialTheme.colorScheme.primary,
	textStyle: TextStyle = TextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 20.sp,
	),
) {
	val spacing = LocalSpacing.current

	Row(
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.clip(RoundedCornerShape(100F))
			.clickable { onClick() }
			.border(
				width = 1.dp,
				color = color,
				shape = RoundedCornerShape(100F)
			)
			.padding(spacing.medium)
	) {
		Icon(
			imageVector = Icons.Default.Add,
			contentDescription = stringResource(R.string.Add),
			tint = color,
		)
		Spacer(Modifier.width(spacing.medium))
		Text(
			text = text,
			style = textStyle,
			color = color,
		)
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	AddButton(
		text = "Add Food",
		onClick = {},
	)
}