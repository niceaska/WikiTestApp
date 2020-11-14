package ru.niceaska.wikitest.presentation.activities

/**
 * Слушатель для отображения списка заголовков картинок
 */
interface ShowImageTitlesListener {

    /**
     * Показать заголовки по [id]
     */
    fun showTitles(id: Long)
}