package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.database.DatabaseHelper
import com.louisfn.somovie.data.database.datasource.RemoteKeyLocalDataSource
import com.louisfn.somovie.data.database.datasource.WatchlistLocalDataSource
import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.data.network.body.AddToWatchlistBody
import com.louisfn.somovie.data.network.datasource.WatchlistRemoteDataSource
import com.louisfn.somovie.data.repository.paging.WatchlistRemoteMediator
import com.louisfn.somovie.data.repository.paging.mapPaging
import com.louisfn.somovie.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface WatchlistRepository {

    @AnyThread
    suspend fun addToWatchlist(movieId: Long)

    @AnyThread
    suspend fun removeFromWatchlist(movieId: Long)

    @AnyThread
    fun watchlistPagingChanges(
        pagingConfig: PagingConfig,
        cacheTimeout: Long = DEFAULT_CACHE_TIMEOUT,
    ): Flow<PagingData<Movie>>

    companion object {
        private const val DEFAULT_CACHE_TIMEOUT = 0L
    }
}

internal class DefaultWatchlistRepository @Inject constructor(
    private val remoteDataSource: WatchlistRemoteDataSource,
    private val localDataSource: WatchlistLocalDataSource,
    private val sessionLocalDataSource: DataStoreLocalDataSource<SessionData>,
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val movieMapper: MovieMapper,
    private val databaseHelper: DatabaseHelper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : WatchlistRepository {

    override fun watchlistPagingChanges(
        pagingConfig: PagingConfig,
        cacheTimeout: Long,
    ): Flow<PagingData<Movie>> =
        flow {
            val pagerFlow =
                Pager(
                    config = pagingConfig,
                    remoteMediator = createWatchlistRemoteMediator(cacheTimeout),
                    pagingSourceFactory = { localDataSource.getPagingWatchlist() },
                ).flow
            emitAll(pagerFlow)
        }
            .mapPaging(movieMapper::mapToDomain)
            .flowOn(defaultDispatcher)

    @AnyThread
    private suspend fun createWatchlistRemoteMediator(
        cacheTimeout: Long,
    ) = WatchlistRemoteMediator(
        cacheTimeout = cacheTimeout,
        remoteKeyLocalDataSource = remoteKeyLocalDataSource,
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        movieMapper = movieMapper,
        databaseHelper = databaseHelper,
        accountId = getAccountId(),
    )

    override suspend fun addToWatchlist(movieId: Long) = defaultDispatcher {
        remoteDataSource.addToWatchlist(
            accountId = getAccountId(),
            addToWatchlistBody = AddToWatchlistBody(
                movieId = movieId,
                watchlist = true,
            ),
        )
        localDataSource.updateWatchlistState(movieId, true)
    }

    override suspend fun removeFromWatchlist(movieId: Long) = defaultDispatcher {
        remoteDataSource.addToWatchlist(
            accountId = getAccountId(),
            addToWatchlistBody = AddToWatchlistBody(
                movieId = movieId,
                watchlist = false,
            ),
        )
        databaseHelper.withTransaction {
            localDataSource.updateWatchlistState(movieId, false)
            localDataSource.deleteFromWatchlist(movieId)
        }
    }

    @AnyThread
    private suspend fun getAccountId(): Long =
        checkNotNull(sessionLocalDataSource.getData().account) { "Account should not be null" }
            .id
}
