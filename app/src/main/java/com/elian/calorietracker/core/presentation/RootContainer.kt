package com.elian.calorietracker.core.presentation

import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.elian.calorietracker.core.presentation.simplestack.BasePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContainer() {
	Scaffold(
		modifier = Modifier
			.fillMaxSize()
	) { innerPadding ->
		AndroidView(
			factory = { context ->
				FrameLayout(context)
			},
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
		)
	}
}


@Preview(showBackground = true)
@Composable
private fun Preview() = BasePreview {
	RootContainer()
}