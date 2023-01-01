package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.database.datasource.MovieCreditsLocalDataSource
import com.louisfn.somovie.data.mapper.MovieCreditsMapper
import com.louisfn.somovie.data.network.datasource.MovieCreditsRemoteDataSource
import com.louisfn.somovie.domain.model.MovieCredits
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface MovieCreditsRepository {

    @AnyThread
    fun movieCreditsChanges(movieId: Long): Flow<MovieCredits>

    @AnyThread
    suspend fun refreshMovieCredits(movieId: Long)
}

internal class DefaultMovieCreditsRepository @Inject constructor(
    private val remoteDataSource: MovieCreditsRemoteDataSource,
    private val localDataSource: MovieCreditsLocalDataSource,
    private val mapper: MovieCreditsMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : MovieCreditsRepository {

    override fun movieCreditsChanges(movieId: Long): Flow<MovieCredits> =
        movieCreditsChangesFromLocal(movieId)

    override suspend fun refreshMovieCredits(movieId: Long) {
        defaultDispatcher {
            remoteDataSource.getMovieCredits(movieId)
                .also {
                    with(localDataSource) {
                        replaceActors(movieId, mapper.mapCastToEntity(it))
                        replaceCrewMembers(movieId, mapper.mapCrewToEntity(it))
                    }
                }
        }
    }

    @AnyThread
    private fun movieCreditsChangesFromLocal(movieId: Long): Flow<MovieCredits> =
        combine(
            localDataSource.actorsChanges(movieId),
            localDataSource.crewMembersChanges(movieId)
        ) { actors, crewMembers -> mapper.mapToDomain(actors, crewMembers) }
            .flowOn(defaultDispatcher)
}
