package ru.niceaska.wikitest.data.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import io.mockk.*
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import ru.niceaska.wikitest.data.api.WikiApi
import ru.niceaska.wikitest.data.datasource.GeoDataSource
import ru.niceaska.wikitest.data.models.*
import ru.niceaska.wikitest.domain.repository.DataRepository
import ru.niceaska.wikitest.rules.RxSchedulerRule

class DataRepositoryImplTest {

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    private val dataSource: GeoDataSource = mockk(relaxed = true)
    private val retrofit: Retrofit = mockk(relaxed = true)
    private val mapper: ObjectMapper = mockk()
    private val wikiApi: WikiApi = mockk()
    private val testPublisher: PublishSubject<GeoPoint> = PublishSubject.create()
    private val geoPoint: GeoPoint = GeoPoint(56.6, 67.5)
    private val repository: DataRepository = DataRepositoryImpl(
        geoDataSource = dataSource,
        mapper = mapper
    )
    private val data = WikiFePageBodyWrapper(
        WikiGeoPageBody(
            listOf(
                WikiGeoPageData(123, TITLE1),
                WikiGeoPageData(124, TITLE2)
            )
        )
    )

    @Before
    fun setUp() {
        mockkConstructor(Retrofit.Builder::class)
        every { anyConstructed<Retrofit.Builder>().build() } returns retrofit
        every { retrofit.create(WikiApi::class.java) } returns wikiApi
    }

    @After
    fun after() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun getWikiPlacesList() {

        every { dataSource.locationData } returns testPublisher
        every { wikiApi.getPlacesList(any()) } returns Single.just(data)
        val testObserver = repository.getWikiPlacesList(false).test()
        testPublisher.onNext(geoPoint)
        verify { dataSource.requestLocation() }
        testObserver
            .assertValue(data)
            .assertComplete()
            .assertValueCount(1)
            .dispose()
    }

    @Test
    fun getWikiPlacesListForce() {

        every { dataSource.locationData } returns testPublisher
        every { wikiApi.getPlacesList(any()) } returns Single.just(data)
        val testObserver = repository.getWikiPlacesList(true).test()
        testPublisher.onNext(geoPoint)
        verify { dataSource.updateLocation() }
        testObserver
            .assertValue(data)
            .assertComplete()
            .assertValueCount(1)
            .dispose()
    }

    @Test
    fun getImagesNames() {
        val reader = mockk<ObjectReader>()
        val imageData = listOf(
            WikiImageData(TITLE1),
            WikiImageData(TITLE2)
        )
        every { wikiApi.getImagesTitles(any()) } returns Single.just(
            WikiImageBodyWrapper(
                mapOf<String, JsonNode>(
                    "query" to mockk<JsonNode>(relaxed = true)
                )
            )
        )
        every { mapper.readerFor(any<TypeReference<List<WikiImageData>?>>()) } returns reader

        every { reader.readValue<List<WikiImageData>?>(any<JsonNode>()) } returns imageData
        val testObserver = repository.getImagesNames(8).test()
        testObserver
            .assertValue(imageData)
            .assertComplete()
            .assertValueCount(1)
            .dispose()
    }

    companion object {
        private const val TITLE1 = "title1"
        private const val TITLE2 = "title2"
    }
}