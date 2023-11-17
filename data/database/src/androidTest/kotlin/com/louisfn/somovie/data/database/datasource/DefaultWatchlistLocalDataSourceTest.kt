package com.louisfn.somovie.data.database.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.entity.WatchlistEntity
import com.louisfn.somovie.test.fixtures.data.database.FakeMovieEntityFactory
import com.louisfn.somovie.test.shared.MainDispatcherRule
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultWatchlistLocalDataSourceTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var localDataSource: WatchlistLocalDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        localDataSource = DefaultWatchlistLocalDataSource(
            database = database,
            ioDispatcher = mainDispatcherRule.testDispatcher,
        )
    }

    @Test
    fun shouldInsertToWatchlist() = runTest {
        // Given
        val entities = FakeMovieEntityFactory.create(10)

        // When
        localDataSource.insertOrIgnoreToWatchlist(entities)

        // Then
        database.movieDao().getAll().shouldContainExactlyInAnyOrder(entities)
        database.watchListDao().getAll()
            .map { it.movieId }
            .shouldContainExactlyInAnyOrder(
                entities.map { it.id },
            )
    }

    @Test
    fun shouldNotInsertToWatchlistIfAlreadyInserted() = runTest {
        // Given
        val entities = FakeMovieEntityFactory.create(2)
        val watchlist = entities.map { WatchlistEntity(movieId = it.id) }
        database.movieDao().insertOrAbort(entities)
        database.watchListDao().insertOrAbort(watchlist)

        // When
        localDataSource.insertOrIgnoreToWatchlist(entities)

        // Then
        database.movieDao().getAll().shouldContainExactlyInAnyOrder(entities)
        database.watchListDao().getAll()
            .map { it.movieId }
            .shouldContainExactlyInAnyOrder(
                entities.map { it.id },
            )
    }

    @Test
    fun shouldUpdateWatchlistState() = runTest {
        // Given
        val entity = FakeMovieEntityFactory.create(1).first().copy(watchlist = false)
        database.movieDao().insertOrAbort(entity)

        // When
        localDataSource.updateWatchlistState(entity.id, true)

        // Then
        database.movieDao().getAll().first().watchlist!!.shouldBeTrue()
    }

    @Test
    fun shouldDeleteMovieFromWatchlist() = runTest {
        // Given
        val entity = FakeMovieEntityFactory.create(1).first()
        val watchlist = WatchlistEntity(movieId = entity.id)
        database.movieDao().insertOrAbort(entity)
        database.watchListDao().insertOrAbort(watchlist)

        // When
        localDataSource.deleteFromWatchlist(entity.id)

        // Then
        database.watchListDao().getAll().shouldBeEmpty()
    }

    @Test
    fun shouldDeleteWatchlist() = runTest {
        // Given
        val entities = FakeMovieEntityFactory.create(2)
        val watchlist = entities.map { WatchlistEntity(movieId = it.id) }
        database.movieDao().insertOrAbort(entities)
        database.watchListDao().insertOrAbort(watchlist)

        // When
        localDataSource.deleteWatchlist()

        // Then
        database.watchListDao().getAll().shouldBeEmpty()
    }

    @After
    fun tearDown() {
        database.close()
    }
}
