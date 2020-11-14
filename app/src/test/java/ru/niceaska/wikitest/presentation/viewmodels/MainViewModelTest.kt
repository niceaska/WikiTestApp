package ru.niceaska.wikitest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.niceaska.wikitest.domain.interactors.WikiGetImagesTitlesInteractor
import ru.niceaska.wikitest.domain.interactors.WikiGetPagesInteractor
import ru.niceaska.wikitest.domain.models.WikiGeoPageDomain
import ru.niceaska.wikitest.presentation.converters.UIWidgetPlacesConverter
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation
import ru.niceaska.wikitest.presentation.models.WikiResult
import ru.niceaska.wikitest.rules.RxSchedulerRule

/**
 * Тест на [MainViewModel]
 */
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    private val observerPagesResult: Observer<WikiResult<List<WikiGeoPagePresentation>, Throwable>> =
        mockk(relaxUnitFun = true)
    private val observerImageTitlesResult: Observer<WikiResult<List<String>, Throwable>> =
        mockk(relaxUnitFun = true)
    private val getImagesTitlesInteractor: WikiGetImagesTitlesInteractor = mockk()
    private val getPagesInteractor: WikiGetPagesInteractor = mockk()
    private val viewModel: MainViewModel = MainViewModel(
        getPagesInteractor = getPagesInteractor,
        getImagesTitlesInteractor = getImagesTitlesInteractor,
        converter = UIWidgetPlacesConverter()
    )

    @Before
    fun setUp() {
        viewModel.resultImagesTitlesLiveData.observeForever(observerImageTitlesResult)
        viewModel.resultLiveData.observeForever(observerPagesResult)
    }

    @Test
    fun fetchData() {
        val domain: List<WikiGeoPageDomain> = listOf(
            WikiGeoPageDomain(0, TITLE1),
            WikiGeoPageDomain(1, TITLE2)
        )
        val presentation = listOf(
            WikiGeoPagePresentation(0, TITLE1),
            WikiGeoPagePresentation(1, TITLE2)
        )
        every { getPagesInteractor.getPagesList(allAny()) } returns Single.just(domain)
        viewModel.fetchData(true)
        verifySequence {
            observerPagesResult.onChanged(WikiResult.Loading(true))
            observerPagesResult.onChanged(WikiResult.Success(presentation))
            observerPagesResult.onChanged(WikiResult.Loading(false))
        }
    }

    @Test
    fun fetchDataError() {
        val error = IllegalArgumentException()
        every { getPagesInteractor.getPagesList(allAny()) } returns Single.error(error)
        viewModel.fetchData(true)
        verifySequence {
            observerPagesResult.onChanged(WikiResult.Loading(true))
            observerPagesResult.onChanged(WikiResult.Error(error))
            observerPagesResult.onChanged(WikiResult.Loading(false))
        }
    }

    @Test
    fun getArticleImagesTitles() {
        every {
            getImagesTitlesInteractor.getArticleImageTitles(allAny())
        } returns Single.just(listOf(TITLE1, TITLE2))
        viewModel.getArticleImagesTitles(8)
        verifySequence {
            observerImageTitlesResult.onChanged(WikiResult.Loading(true))
            observerImageTitlesResult.onChanged(WikiResult.Success(listOf(TITLE1, TITLE2)))
            observerImageTitlesResult.onChanged(WikiResult.Loading(false))
        }
    }

    @Test
    fun getArticleImagesTitlesError() {
        val error = IllegalArgumentException()
        every {
            getImagesTitlesInteractor.getArticleImageTitles(allAny())
        } returns Single.error(error)
        viewModel.getArticleImagesTitles(8)
        verifySequence {
            observerImageTitlesResult.onChanged(WikiResult.Loading(true))
            observerImageTitlesResult.onChanged(WikiResult.Error(error))
            observerImageTitlesResult.onChanged(WikiResult.Loading(false))
        }
    }

    companion object {
        private const val TITLE1 = "title1"
        private const val TITLE2 = "title2"
    }
}