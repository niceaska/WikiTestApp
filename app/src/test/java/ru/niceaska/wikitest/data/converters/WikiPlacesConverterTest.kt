package ru.niceaska.wikitest.data.converters

import com.google.common.truth.Truth
import org.junit.Test
import ru.niceaska.wikitest.data.models.WikiFePageBodyWrapper
import ru.niceaska.wikitest.data.models.WikiGeoPageBody
import ru.niceaska.wikitest.data.models.WikiGeoPageData
import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain

/**
 * Тест на [WikiPlacesConverter]
 */
class WikiPlacesConverterTest {

    private val expected: List<WikiGeoPageDomain> = listOf(
        WikiGeoPageDomain(123, TITLE1),
        WikiGeoPageDomain(124, TITLE2)
    )

    @Test
    fun convert() {
        val data = WikiFePageBodyWrapper(
            WikiGeoPageBody(
                listOf(
                    WikiGeoPageData(123, TITLE1),
                    WikiGeoPageData(124, TITLE2)
                )
            )
        )
        Truth.assertThat(WikiPlacesConverter().convert(data)).isEqualTo(expected)
    }

    companion object {
        private const val TITLE1 = "title1"
        private const val TITLE2 = "title2"
    }
}