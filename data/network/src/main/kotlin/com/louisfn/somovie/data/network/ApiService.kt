package com.louisfn.somovie.data.network

import com.louisfn.somovie.data.network.body.AddToWatchlistBody
import com.louisfn.somovie.data.network.body.RequestTokenBody
import com.louisfn.somovie.data.network.parameter.MovieDiscoverSortParameter
import com.louisfn.somovie.data.network.response.AccountResponse
import com.louisfn.somovie.data.network.response.ConfigurationResponse
import com.louisfn.somovie.data.network.response.MovieAccountStateResponse
import com.louisfn.somovie.data.network.response.MovieCreditsResponse
import com.louisfn.somovie.data.network.response.MovieDetailsResponse
import com.louisfn.somovie.data.network.response.MovieImagesResponse
import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.data.network.response.MovieVideoResponse
import com.louisfn.somovie.data.network.response.NewSessionResponse
import com.louisfn.somovie.data.network.response.PaginatedResultsResponse
import com.louisfn.somovie.data.network.response.RequestTokenResponse
import com.louisfn.somovie.data.network.response.ResultsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    //region Authentication

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createNewSession(@Body requestTokenBody: RequestTokenBody): NewSessionResponse

    //endregion

    //region Account

    @GET("account")
    suspend fun getAccount(): AccountResponse

    //endregion

    //region Configuration

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    //endregion

    //region Movie

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PaginatedResultsResponse<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): PaginatedResultsResponse<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): PaginatedResultsResponse<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): PaginatedResultsResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Long): MovieDetailsResponse

    @GET("movie/{movie_id}/images?include_image_language=en,null")
    suspend fun getMovieImages(@Path("movie_id") movieId: Long): MovieImagesResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Long): MovieCreditsResponse

    @GET("movie/{movie_id}/videos?include_video_language=en,null")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Long): ResultsResponse<MovieVideoResponse>

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(@Path("movie_id") movieId: Long): MovieAccountStateResponse

    //endregion

    //region Discover

    @GET("discover/movie")
    suspend fun getMovieDiscover(
        @Query("sort_by") sortBy: MovieDiscoverSortParameter,
        @Query("vote_count.gte") minVoteCount: Int,
        @Query("vote_average.gte") minVoteAverage: Float,
        @Query("page") page: Int,
    ): PaginatedResultsResponse<MovieResponse>

    //endregion

    //region Watchlist

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: Long,
        @Body addToWatchlistBody: AddToWatchlistBody,
    )

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getMovieWatchlist(
        @Path("account_id") accountId: Long,
        @Query("page") page: Int,
    ): PaginatedResultsResponse<MovieResponse>

    //endregion
}
