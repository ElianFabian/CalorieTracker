package com.elian.calorietracker.core.presentation.simplestack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.elian.calorietracker.core.util.ext.simplestack.BackstackProvider
import com.elian.calorietracker.ui.theme.CalorieTrackerTheme
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.navigatorktx.backstack

abstract class ComposeKeyedFragment : KeyedFragment() {

	@Composable
	abstract fun Content()

	final override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		return ComposeView(requireContext()).apply {
			setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
			setContent {
				BackstackProvider(
					backstack = backstack,
				) {
					CalorieTrackerTheme {
						this@ComposeKeyedFragment.Content()
					}
				}
			}
		}
	}
}