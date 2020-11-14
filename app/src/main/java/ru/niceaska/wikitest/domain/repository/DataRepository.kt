package ru.niceaska.wikitest.domain.repository

import io.reactivex.Single
import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.data.models.WikiImageData

/**
 * интерфейс репозитория для предоставления данных
 */
interface DataRepository {

    /**
     * Запросить геолокацию и получить список статей
     *
     * @param force    определяет будет ли геолокация запрошена принудительно,
     *                 при false берет последнее известное значение
     */
    fun getWikiPlacesList(force: Boolean): Single<WikiFePageBodyWrapper>

    /**
     * Запросить список картинок по айди сттаьи
     *
     * @param id айди статьи
     */
    fun getImagesNames(id: Long): Single<List<WikiImageData>>
}