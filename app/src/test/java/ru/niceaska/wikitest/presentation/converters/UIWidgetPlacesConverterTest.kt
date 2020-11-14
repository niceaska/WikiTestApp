package ru.niceaska.wikitest.presentation.converters

import com.google.common.truth.Truth
import org.junit.Test
import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

/**
 * Тест на [UIWidgetPlacesConverter]
 */
class UIWidgetPlacesConverterTest {

    private val data: List<WikiGeoPageDomain> = listOf(
        WikiGeoPageDomain(0, TITLE1),
        WikiGeoPageDomain(1, TITLE2)
    )

    @Test
    fun convert() {
        Truth.assertThat(UIWidgetPlacesConverter().convert(data)).isEqualTo(
            listOf(
                WikiGeoPagePresentation(0, TITLE1), WikiGeoPagePresentation(1, TITLE2)
            )
        )
    }

    companion object {
        private const val TITLE1 = "title1"
        private const val TITLE2 = "title2"
    }
}