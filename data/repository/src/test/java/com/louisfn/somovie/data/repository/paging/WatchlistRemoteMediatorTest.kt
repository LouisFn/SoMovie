package com.louisfn.somovie.data.repository.paging

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity.Type.WATCH_LIST
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.test.fixtures.data.database.FakeMovieEntityFactory
import com.louisfn.somovie.test.fixtures.data.datastore.FakeSessionDataFactory
import com.louisfn.somovie.test.fixtures.data.mapper.MapperFactory
import com.louisfn.somovie.test.fixtures.data.response.FakeMovieResponseFactory
import com.louisfn.somovie.test.fixtures.datasource.database.FakeRemoteKeyLocalDataSource
import com.louisfn.somovie.test.fixtures.datasource.database.FakeWatchlistLocalDataSource
import com.louisfn.somovie.test.fixtures.datasource.network.FakeWatchlistRemoteDataSource
import com.louisfn.somovie.test.fixtures.utils.FakeDateTimeProvider
import com.louisfn.somovie.test.fixtures.utils.database.FakeDatabaseHelper
import com.louisfn.somovie.test.shared.FakeWebServer
import com.louisfn.somovie.test.shared.MainDispatcherRule
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WatchlistRemoteMediatorTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var remoteKeyLocalDataSource: FakeRemoteKeyLocalDataSource
    private lateinit var watchlistRemoteDataSource: FakeWatchlistRemoteDataSource
    private lateinit var watchlistLocalDataSource: FakeWatchlistLocalDataSource
    private lateinit var watchlistRemoteMediator: WatchlistRemoteMediator
    private val databaseHelper = FakeDatabaseHelper()
    private val fakeDateTimeProvider = FakeDateTimeProvider()
    private val movieMapper: MovieMapper = MapperFactory.createMovieMapper(fakeDateTimeProvider)
    private val fakeWebServer = FakeWebServer()
    private val testScope = TestScope(mainDispatcherRule.testDispatcher)
    private val nbrOfResultsByPage = 2
    private val defaultMovies = FakeMovieResponseFactory.create(5)
    private val defaultSession = FakeSessionDataFactory.default

    @Before
    fun setUp() {
        val accountId = requireNotNull(defaultSession.account).id

        remoteKeyLocalDataSource = FakeRemoteKeyLocalDataSource(fakeDateTimeProvider)
        watchlistLocalDataSource = FakeWatchlistLocalDataSource(testScope)
        watchlistRemoteDataSource = FakeWatchlistRemoteDataSource(
            movies = defaultMovies,
            accountId = accountId,
            fakeWebServer = fakeWebServer,
            nbrOfResultsByPage = nbrOfResultsByPage
        )

        watchlistRemoteMediator = WatchlistRemoteMediator(
            cacheTimeout = 0,
            remoteDataSource = watchlistRemoteDataSource,
            localDataSource = watchlistLocalDataSource,
            remoteKeyLocalDataSource = remoteKeyLocalDataSource,
            movieMapper = movieMapper,
            databaseHelper = databaseHelper,
            accountId = accountId
        )
    }

    @Test
    fun shouldRefreshReturnsSuccessAndInsertData_whenMoreDataIsPresent() = runTest {
        // Given
        val pagingState = PagingState<Int, MovieEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 0
        )
        watchlistRemoteDataSource.addToWatchlist(defaultMovies)
        watchlistLocalDataSource.insertOrIgnoreToWatchlist(FakeMovieEntityFactory.create(1))

        // When
        val result = watchlistRemoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        result.shouldBeInstanceOf<Success>()
        result.endOfPaginationReached.shouldBeFalse()
        watchlistLocalDataSource.watchlist.value.size.shouldBe(nbrOfResultsByPage)
        remoteKeyLocalDataSource.getRemoteKey(WATCH_LIST)
            .shouldBe(RemoteKeyEntity(WATCH_LIST, "2", fakeDateTimeProvider.now()))
    }

    @Test
    fun shouldRefreshReturnsSuccessWithEndOfPaginationAndInsertData_whenNoMoreData() =
        runTest {
            // Given
            val pagingState = PagingState<Int, MovieEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 0
            )
            val watchlist = defaultMovies.take(nbrOfResultsByPage)
            watchlistRemoteDataSource.addToWatchlist(watchlist)
            watchlistLocalDataSource.insertOrIgnoreToWatchlist(FakeMovieEntityFactory.create(1))

            // When
            val result = watchlistRemoteMediator.load(LoadType.REFRESH, pagingState)

            // Then
            result.shouldBeInstanceOf<Success>()
            result.endOfPaginationReached.shouldBeTrue()

            watchlistLocalDataSource.watchlist.value.size
                .shouldBe(nbrOfResultsByPage)
            remoteKeyLocalDataSource.getRemoteKey(WATCH_LIST)
                .shouldBe(RemoteKeyEntity(WATCH_LIST, "2", fakeDateTimeProvider.now()))
        }

    @Test
    fun shouldRefreshReturnsErrorAndInsertNoneData_whenRequestedFailed() = runTest {
        // Given
        val pagingState = PagingState<Int, MovieEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 0
        )
        val currentWatchlist = FakeMovieEntityFactory.create(1)
        fakeWebServer.isNetworkAvailable = false
        watchlistRemoteDataSource.addToWatchlist(defaultMovies)
        watchlistLocalDataSource.insertOrIgnoreToWatchlist(currentWatchlist)

        // When
        val result = watchlistRemoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        result.shouldBeInstanceOf<Error>()
        watchlistLocalDataSource.watchlist.value.shouldBe(currentWatchlist)
        remoteKeyLocalDataSource.getRemoteKey(WATCH_LIST).shouldBeNull()
    }

    @Test
    fun shouldAppendReturnsSuccessAndInsertDataInLocal_whenMoreDataIsPresent() =
        runTest {
            // Given
            val pagingState = PagingState<Int, MovieEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 0
            )
            val watchlist = defaultMovies
            watchlistRemoteDataSource.addToWatchlist(watchlist)
            watchlistLocalDataSource.insertOrIgnoreToWatchlist(FakeMovieEntityFactory.create(1))

            // When
            watchlistRemoteMediator.load(LoadType.REFRESH, pagingState)
            val result = watchlistRemoteMediator.load(LoadType.APPEND, pagingState)

            // Then
            result.shouldBeInstanceOf<Success>()
            result.endOfPaginationReached.shouldBeFalse()

            watchlistLocalDataSource.watchlist.value.size.shouldBe(nbrOfResultsByPage * 2)
            remoteKeyLocalDataSource.getRemoteKey(WATCH_LIST)
                .shouldBe(RemoteKeyEntity(WATCH_LIST, "3", fakeDateTimeProvider.now()))
        }

    @Test
    fun shouldPrependReturnsSuccessAndNotFetchData() = runTest {
        // Given
        val pagingState = PagingState<Int, MovieEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 0
        )
        watchlistRemoteDataSource.addToWatchlist(defaultMovies)

        // When
        watchlistRemoteMediator.load(LoadType.REFRESH, pagingState)
        val result = watchlistRemoteMediator.load(LoadType.PREPEND, pagingState)

        // Then
        result.shouldBeInstanceOf<Success>()
        result.endOfPaginationReached.shouldBeTrue()

        watchlistLocalDataSource.watchlist.value.size.shouldBe(nbrOfResultsByPage)
        remoteKeyLocalDataSource.getRemoteKey(WATCH_LIST)
            .shouldBe(RemoteKeyEntity(WATCH_LIST, "2", fakeDateTimeProvider.now()))
    }
}
