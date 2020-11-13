package ru.niceaska.wikitest.domain.interactors

import io.reactivex.Single
import ru.niceaska.wikitest.data.converters.WikiArticleImagesTitlesConverter
import ru.niceaska.wikitest.domain.repository.DataRepository

/**
 * Интерактор для получения заголовков картинок по айди статьи
 *
 * @constructor
 * @property repository репозиторий [DataRepository] для получения данных
 * @property converter конвертер из дата в домейн слой
 */
class WikiGetImagesTitlesInteractor(
    private val repository: DataRepository,
    private val converter: WikiArticleImagesTitlesConverter
) {

    fun getArticleTitles(id: Long): Single<List<String>> = repository.getImagesNames(id).map {
        converter.convert(it)
    }
}