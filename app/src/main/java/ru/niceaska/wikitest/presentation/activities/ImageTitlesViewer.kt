package ru.niceaska.wikitest.presentation.activities

/**
 * Интерфейс для реализации показа списка статей по айди
 */
interface ImageTitlesViewer {

    /**
     * Показать список статей по [id]
     */
    fun showImageTitles(id: Long)
}