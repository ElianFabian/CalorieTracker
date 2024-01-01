package com.elian.calorietracker.features.onboarding.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun ActionButton(
	text: String,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	isEnabled: Boolean = true,
	textStyle: TextStyle = TextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 20.sp,
	),
) {
	val spacing = LocalSpacing.current

	Button(
		onClick = onClick,
		modifier = modifier,
		enabled = isEnabled,
		shape = RoundedCornerShape(100.dp)
	) {
		Text(
			text = text,
			style = textStyle,
			color = MaterialTheme.colorScheme.onPrimary,
			modifier = Modifier.padding(spacing.small),
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	ActionButton(
		text = "Next",
		onClick = {},
	)
}