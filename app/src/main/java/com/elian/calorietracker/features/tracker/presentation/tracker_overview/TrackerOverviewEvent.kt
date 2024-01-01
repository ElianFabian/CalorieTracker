package com.elian.calorietracker.features.tracker.presentation.tracker_overview

sealed interface TrackerOverviewEvent {
	data object ScrollToTop : TrackerOverviewEvent
}