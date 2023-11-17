package com.louisfn.somovie.data.database.datasource

import androidx.annotation.AnyThread
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.louisfn.somovie.core.common.annotation.IoDispatcher
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.entity.WatchlistEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface WatchlistLocalDataSource {

    @AnyThread
    fun getPagingWatchlist(): PagingSource<Int, MovieEntity>

    @AnyThread
    suspend fun insertOrIgnoreToWatchlist(movieEntities: List<MovieEntity>)

    @AnyThread
    suspend fun updateWatchlistState(movieId: Long, watchlist: Boolean)

    @AnyThread
    suspend fun deleteFromWatchlist(movieId: Long)

    @AnyThread
    suspend fun deleteWatchlist()
}

internal class DefaultWatchlistLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : WatchlistLocalDataSource {

    override fun getPagingWatchlist(): PagingSource<Int, MovieEntity> =
        database.watchListDao().getPaging()

    override suspend fun updateWatchlistState(movieId: Long, watchlist: Boolean) {
        database.movieDao().updateWatchlist(movieId, watchlist)
    }

    override suspend fun insertOrIgnoreToWatchlist(movieEntities: List<MovieEntity>) {
        ioDispatcher {
            with(database) {
                val watchList = movieEntities.map { WatchlistEntity(movieId = it.id) }
                withTransaction {
                    movieDao().insertOrIgnore(movieEntities)
                    watchListDao().insertOrIgnore(watchList)
                }
            }
        }
    }

    override suspend fun deleteFromWatchlist(movieId: Long) {
        ioDispatcher {
            database.watchListDao().delete(movieId)
        }
    }

    override suspend fun deleteWatchlist() {
        database.watchListDao().deleteAll()
    }
}
