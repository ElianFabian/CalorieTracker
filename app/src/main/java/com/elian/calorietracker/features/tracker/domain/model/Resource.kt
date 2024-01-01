package com.elian.calorietracker.features.tracker.domain.model

import com.elian.calorietracker.R
import com.elian.calorietracker.core.util.UiText
import kotlinx.coroutines.CancellationException
import okio.IOException
import retrofit2.HttpException

typealias SimpleResource = Resource<Unit>

sealed interface Resource<T> {
	data class Success<T>(
		val data: T?,
	) : Resource<T>

	data class Error<T>(
		val errorMessage: UiText,
	) : Resource<T>
}

suspend inline fun <T> safeApiCall(
	crossinline apiCall: suspend () -> T?,
): Resource<T> {
	return try {
		val data = apiCall()

		Resource.Success(data)
	}
	catch (e: CancellationException) {
		throw e
	}
	catch (e: IOException) {
		Resource.Error(UiText(R.string.Error__CouldntReachServer))
	}
	catch (e: HttpException) {
		Resource.Error(UiText(R.string.Error__SomethingWentWrong))
	}
	catch (e: Exception) {
		val exceptionMessage = e.message

		val message = if (exceptionMessage.isNullOrBlank()) {
			UiText(R.string.Error__Unknown)
		}
		else UiText(exceptionMessage)

		Resource.Error(message)
	}
}