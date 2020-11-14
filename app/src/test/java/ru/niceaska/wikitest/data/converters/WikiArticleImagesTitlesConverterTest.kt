package ru.niceaska.wikitest.data.converters

import com.google.common.truth.Truth
import org.junit.Test
import ru.niceaska.wikitest.data.models.WikiImageData

/**
 * Тест на [WikiArticleImagesTitlesConverter]
 */
class WikiArticleImagesTitlesConverterTest {

    private val wikiImageTitlesData: List<WikiImageData> = listOf(
        WikiImageData(TITLE1),
        WikiImageData(TITLE2),
    )

    @Test
    fun testConvert() = Truth.assertThat(
        WikiArticleImagesTitlesConverter()
            .convert(wikiImageTitlesData)
    ).isEqualTo(listOf(TITLE1, TITLE2))

    companion object {
        private const val TITLE1 = "title1"
        private const val TITLE2 = "title2"
    }
}
