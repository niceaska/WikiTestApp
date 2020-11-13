package ru.niceaska.wikitest.presentation.models

import androidx.lifecycle.Observer

sealed class WikiResult<out Success, out Error> {

    /**
     * Состояния загрузки
     */
    data class Loading(val enable: Boolean) : WikiResult<Nothing, Nothing>()

    /**
     * Состояние ошибки загрузки
     *
     * @property error текст ошибки
     */
    data class Error<T>(val error: T) : WikiResult<Nothing, T>()

    /**
     * Состояние успешной загрузки
     *
     * @param data загруженный ресурс
     */
    data class Success<T>(val data: T) : WikiResult<T, Nothing>()
}

/**
 * Создать [Observer], который будет обрабатывать состояние ресурса
 */
inline fun <Success, Error> createResultObserver(
    crossinline onSuccess: (Success?) -> Unit,
    crossinline onLoading: (Boolean) -> Unit,
    crossinline onError: (Error) -> Unit
): Observer<WikiResult<Success, Error>> = Observer { result: WikiResult<Success, Error>? ->
    result?.let {
        when (it) {
            is WikiResult.Success -> onSuccess.invoke(it.data)
            is WikiResult.Loading -> onLoading.invoke(it.enable)
            is WikiResult.Error -> onError.invoke(it.error)
        }
    }
}