package com.elian.calorietracker.features.tracker.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elian.calorietracker.R
import com.elian.calorietracker.core.presentation.simplestack.BasePreview
import com.elian.calorietracker.ui.theme.LocalSpacing

@Composable
fun SearchTextField(
	modifier: Modifier = Modifier,
	value: String,
	onValueChange: (value: String) -> Unit,
	onSearchClick: () -> Unit,
	hint: String = stringResource(R.string.Search),
) {
	val spacing = LocalSpacing.current
	var isFocused by remember {
		mutableStateOf(false)
	}

	Box(
		modifier = modifier
	) {
		BasicTextField(
			value = value,
			onValueChange = onValueChange,
			singleLine = true,
			keyboardActions = KeyboardActions(
				onSearch = {
					onSearchClick()
					defaultKeyboardAction(ImeAction.Search)
				}
			),
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Search,
			),
			modifier = Modifier
				.clip(RoundedCornerShape(5.dp))
				.padding(2.dp)
				.shadow(
					elevation = 2.dp,
					shape = RoundedCornerShape(5.dp),
				)
				.background(MaterialTheme.colorScheme.surface)
				.fillMaxWidth()
				.padding(spacing.medium)
				.padding(end = spacing.medium)
				.onFocusChanged { state ->
					isFocused = state.isFocused
				}
		)

		val showHint = !isFocused && value.isBlank()
		if (showHint) {
			Text(
				text = hint,
				style = MaterialTheme.typography.bodyMedium,
				fontWeight = FontWeight.Light,
				color = Color.LightGray,
				modifier = Modifier
					.align(Alignment.CenterStart)
					.padding(start = spacing.medium)
			)
		}
		IconButton(
			onClick = onSearchClick,
			modifier = Modifier
				.align(Alignment.CenterEnd)
		) {
			Icon(
				imageVector = Icons.Default.Search,
				contentDescription = stringResource(R.string.Search),
			)
		}
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	SearchTextField(
		value = "",
		onValueChange = {},
		onSearchClick = {},
	)
}