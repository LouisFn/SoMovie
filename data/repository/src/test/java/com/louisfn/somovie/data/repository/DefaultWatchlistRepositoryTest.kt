package com.louisfn.somovie.data.repository

import androidx.paging.PagingConfig
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.test.fixtures.data.datastore.FakeSessionDataFactory
import com.louisfn.somovie.test.fixtures.data.mapper.MapperFactory
import com.louisfn.somovie.test.fixtures.data.response.FakeMovieResponseFactory
import com.louisfn.somovie.test.fixtures.datasource.database.FakeRemoteKeyLocalDataSource
import com.louisfn.somovie.test.fixtures.datasource.database.FakeWatchlistLocalDataSource
import com.louisfn.somovie.test.fixtures.datasource.datastore.FakeSessionLocalDataSource
import com.louisfn.somovie.test.fixtures.datasource.network.FakeWatchlistRemoteDataSource
import com.louisfn.somovie.test.fixtures.utils.database.FakeDatabaseHelper
import com.louisfn.somovie.test.shared.FakeWebServer
import com.louisfn.somovie.test.shared.MainDispatcherRule
import com.louisfn.somovie.test.shared.paging.PagingItemsCollector
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultWatchlistRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var remoteKeyLocalDataSource: FakeRemoteKeyLocalDataSource
    private lateinit var watchlistLocalDataSource: FakeWatchlistLocalDataSource
    private lateinit var watchlistRemoteDataSource: FakeWatchlistRemoteDataSource
    private lateinit var sessionLocalDataSource: FakeSessionLocalDataSource
    private lateinit var watchlistRepository: WatchlistRepository
    private val movieMapper = MapperFactory.createMovieMapper()

    private val testScope = TestScope(mainDispatcherRule.testDispatcher)
    private val defaultSession = FakeSessionDataFactory.default
    private val defaultMovies = FakeMovieResponseFactory.create(4)
    private var fakeWebServer = FakeWebServer()

    @Before
    fun setUp() {
        watchlistRemoteDataSource =
            FakeWatchlistRemoteDataSource(
                movies = defaultMovies,
                accountId = requireNotNull(defaultSession.account).id,
                fakeWebServer = fakeWebServer
            )
        remoteKeyLocalDataSource = FakeRemoteKeyLocalDataSource()
        watchlistLocalDataSource = FakeWatchlistLocalDataSource(testScope)
        sessionLocalDataSource = FakeSessionLocalDataSource()
        sessionLocalDataSource.setSession(defaultSession)

        watchlistRepository = DefaultWatchlistRepository(
            remoteDataSource = watchlistRemoteDataSource,
            localDataSource = watchlistLocalDataSource,
            sessionLocalDataSource = sessionLocalDataSource,
            remoteKeyLocalDataSource = remoteKeyLocalDataSource,
            movieMapper = movieMapper,
            databaseHelper = FakeDatabaseHelper(),
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun shouldGetWatchlist_whenRefreshDataSucceed() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<Movie>()
        val watchlist = defaultMovies.take(2)
        watchlistRemoteDataSource.addToWatchlist(watchlist)

        // When
        val job = launch(UnconfinedTestDispatcher()) {
            watchlistRepository
                .watchlistPagingChanges(PagingConfig(10))
                .collectLatest(pagingItemsCollector::collectFrom)
        }

        // Then
        val expectedEntities = movieMapper.mapToEntity(responses = watchlist, fromWatchlist = true)
        val expectedData = movieMapper.mapToDomain(expectedEntities)
        watchlistLocalDataSource.watchlist.value.shouldContainExactly(expectedEntities)
        pagingItemsCollector.items.value.shouldContainExactly(expectedData)

        job.cancel()
    }

    @Test
    fun shouldAddToWatchlist() = runTest {
        // Given
        val movie = defaultMovies.first()

        // When
        watchlistRepository.addToWatchlist(movie.id)

        // Then
        watchlistRemoteDataSource.getFullWatchlist().shouldContainExactly(movie)
        watchlistLocalDataSource.moviesWatchListState.value
            .shouldContainExactly(FakeWatchlistLocalDataSource.MovieWatchlistState(movie.id, true))
    }

    @Test
    fun shouldNotAddToWatchlist_whenNoNetwork() = runTest {
        // Given
        val movie = defaultMovies.first()
        fakeWebServer.isNetworkAvailable = false

        // When
        val result = runCatching { watchlistRepository.addToWatchlist(movie.id) }

        // Then
        result.isFailure.shouldBeTrue()
        watchlistRemoteDataSource.getFullWatchlist().shouldBeEmpty()
        watchlistLocalDataSource.moviesWatchListState.value.shouldBeEmpty()
    }

    @Test
    fun shouldRemoveToWatchlist() = runTest {
        // Given
        val movie = defaultMovies.first()
        watchlistRemoteDataSource.addToWatchlist(movie)

        // When
        watchlistRepository.removeFromWatchlist(movie.id)

        // Then
        watchlistRemoteDataSource.getFullWatchlist().shouldBeEmpty()
        watchlistLocalDataSource.moviesWatchListState.value
            .shouldContainExactly(FakeWatchlistLocalDataSource.MovieWatchlistState(movie.id, false))
    }

    @Test
    fun shouldNotRemoveToWatchlist_whenNoNetwork() = runTest {
        // Given
        val movie = defaultMovies.first()
        watchlistRemoteDataSource.addToWatchlist(movie)
        fakeWebServer.isNetworkAvailable = false

        // When
        val result = runCatching { watchlistRepository.removeFromWatchlist(movie.id) }

        // Then
        result.isFailure.shouldBe(true)
        watchlistRemoteDataSource.getFullWatchlist().shouldContainExactly(movie)
        watchlistLocalDataSource.moviesWatchListState.value.shouldBeEmpty()
    }
}
