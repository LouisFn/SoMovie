package com.louisfn.somovie.feature.home.watchlist

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.louisfn.somovie.data.repository.AccountRepository
import com.louisfn.somovie.domain.usecase.authentication.AuthenticationInteractor
import com.louisfn.somovie.domain.usecase.watchlist.WatchlistInteractor
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.ContentState
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.LoadNextPageState
import com.louisfn.somovie.test.fixtures.data.repository.FakeAccountRepository
import com.louisfn.somovie.test.fixtures.data.repository.FakeAuthenticationRepository
import com.louisfn.somovie.test.fixtures.data.repository.FakeSessionRepository
import com.louisfn.somovie.test.fixtures.data.repository.FakeWatchlistRepository
import com.louisfn.somovie.test.fixtures.domain.FakeMovieFactory
import com.louisfn.somovie.test.fixtures.domain.FakeSessionFactory
import com.louisfn.somovie.test.fixtures.ui.FakeErrorsDispatcher
import com.louisfn.somovie.test.shared.FakeWebServer
import com.louisfn.somovie.test.shared.MainDispatcherRule
import com.louisfn.somovie.test.shared.paging.PagingItemsCollector
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WatchlistViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testScope = TestScope(mainDispatcherRule.testDispatcher)

    private lateinit var errorsDispatcher: FakeErrorsDispatcher
    private lateinit var watchlistRepository: FakeWatchlistRepository
    private lateinit var sessionRepository: FakeSessionRepository
    private lateinit var authenticationRepository: FakeAuthenticationRepository
    private lateinit var accountRepository: AccountRepository
    private lateinit var watchlistInteractor: WatchlistInteractor
    private lateinit var authenticationInteractor: AuthenticationInteractor
    private lateinit var viewModel: WatchlistViewModel

    private val defaultMovies = FakeMovieFactory.create(3)
    private var fakeWebServer = FakeWebServer()

    @Before
    fun setUp() {
        errorsDispatcher = FakeErrorsDispatcher()

        authenticationRepository = FakeAuthenticationRepository()
        watchlistRepository = FakeWatchlistRepository(defaultMovies, fakeWebServer)
        accountRepository = FakeAccountRepository()
        sessionRepository = FakeSessionRepository()
        sessionRepository.setSession(FakeSessionFactory.default)

        watchlistInteractor = WatchlistInteractor(watchlistRepository)
        authenticationInteractor = AuthenticationInteractor(
            authenticationRepository = authenticationRepository,
            sessionRepository = sessionRepository,
            accountRepository = accountRepository,
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )

        viewModel = WatchlistViewModel(
            watchlistInteractor = watchlistInteractor,
            authenticationInteractor = authenticationInteractor,
            errorsDispatcher = errorsDispatcher,
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            applicationScope = testScope
        )
    }

    @Test
    fun uiStateShouldBeAccountLoggedIn_whenSessionHasAccount() = runTest {
        // Given

        // When

        // Then
        viewModel.uiState.first().shouldBeInstanceOf<WatchlistUiState.AccountLoggedIn>()
    }

    @Test
    fun uiStateShouldBeAccountDisconnected_whenSessionIsEmpty() = runTest {
        // Given
        sessionRepository.setSession(FakeSessionFactory.empty)

        // When

        // Then
        viewModel.uiState.first().shouldBeInstanceOf<WatchlistUiState.AccountDisconnected>()
    }

    @Test
    fun pagingItemsShouldContainsMovieItems() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<MovieItem>()
        val movieAddedToWatchlist = defaultMovies.first()
        watchlistRepository.addToWatchlist(movieAddedToWatchlist.id)

        // When
        pagingItemsCollector.collectFrom(viewModel.pagedMovieItems.first())

        // Then
        pagingItemsCollector.items.value.shouldBe(
            listOf(
                MovieItem(
                    movie = movieAddedToWatchlist,
                    isHidden = false
                )
            )
        )
    }

    @Test
    fun shouldHideMovieItem_onSwipeToDismiss() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<MovieItem>()
        val movieAddedToWatchlist = defaultMovies.first()
        watchlistRepository.addToWatchlist(movieAddedToWatchlist.id)

        // When
        viewModel.onSwipeToDismiss(movieAddedToWatchlist)
        pagingItemsCollector.collectFrom(viewModel.pagedMovieItems.first())

        // Then
        pagingItemsCollector.items.value.shouldBe(
            listOf(
                MovieItem(
                    movie = movieAddedToWatchlist,
                    isHidden = true
                )
            )
        )
    }

    @Test
    fun shouldDisplayItem_whenSwipeToDismissSnackbarActionPerformed() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<MovieItem>()
        val movieAddedToWatchlist = defaultMovies.first()
        watchlistRepository.addToWatchlist(movieAddedToWatchlist.id)

        // When
        viewModel.onSwipeToDismiss(movieAddedToWatchlist)
        viewModel.onUndoSwipeToDismissSnackbarActionPerformed(movieAddedToWatchlist.id)
        pagingItemsCollector.collectFrom(viewModel.pagedMovieItems.first())

        // Then
        pagingItemsCollector.items.value.shouldBe(
            listOf(
                MovieItem(
                    movie = movieAddedToWatchlist,
                    isHidden = false
                )
            )
        )
    }

    @Test
    fun shouldRemoveItem_whenSwipeToDismissSnackbarActionDismissed() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<MovieItem>()
        val movieAddedToWatchlist = defaultMovies.first()
        watchlistRepository.addToWatchlist(movieAddedToWatchlist.id)

        // When
        viewModel.onSwipeToDismiss(movieAddedToWatchlist)
        viewModel.onUndoSwipeToDismissSnackbarDismissed(movieAddedToWatchlist.id)
        pagingItemsCollector.collectFrom(viewModel.pagedMovieItems.first())

        // Then
        pagingItemsCollector.items.value.shouldBeEmpty()
    }

    @Test
    fun shouldUnhideItem_whenRemoveFromWatchlistFailed() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<MovieItem>()
        val movieAddedToWatchlist = defaultMovies.first()
        watchlistRepository.addToWatchlist(movieAddedToWatchlist.id)
        fakeWebServer.isNetworkAvailable = false

        // When
        viewModel.onSwipeToDismiss(movieAddedToWatchlist)
        viewModel.onUndoSwipeToDismissSnackbarDismissed(movieAddedToWatchlist.id)
        pagingItemsCollector.collectFrom(viewModel.pagedMovieItems.first())

        // Then
        pagingItemsCollector.items.value.shouldBe(
            listOf(
                MovieItem(
                    movie = movieAddedToWatchlist,
                    isHidden = false
                )
            )
        )
    }

    @Test
    fun shouldRemoveAllPendingSwipedMovies_whenViewHidden() = runTest {
        // Given
        val pagingItemsCollector = PagingItemsCollector<MovieItem>()
        val movieAddedToWatchlist = defaultMovies.first()
        watchlistRepository.addToWatchlist(movieAddedToWatchlist.id)

        // When
        viewModel.onSwipeToDismiss(movieAddedToWatchlist)
        viewModel.onViewHidden()
        pagingItemsCollector.collectFrom(viewModel.pagedMovieItems.first())

        // Then
        pagingItemsCollector.items.value.shouldBeEmpty()
    }

    @Test
    fun contentStateShouldBeRetry_whenWatchlistIsEmptyAndRefreshFailed() = runTest {
        // Given

        // When
        val combinedLoadStates =
            createCombinedLoadStates(refresh = LoadState.Error(RuntimeException()))
        viewModel.onLoadStateChanged(combinedLoadStates, 0)

        // Then
        val uiState = viewModel.uiState.first()
        uiState.shouldBeInstanceOf<WatchlistUiState.AccountLoggedIn>()
        uiState.contentState.shouldBe(ContentState.RETRY)
        uiState.loadNextPageState.shouldBe(LoadNextPageState.IDLE)
        errorsDispatcher.hasError(RuntimeException()).shouldBeTrue()
    }

    @Test
    fun contentStateShouldBeLoading_whenWatchlistIsEmptyAndRefreshIsOngoing() =
        runTest {
            // Given
            // When
            val combinedLoadStates = createCombinedLoadStates(refresh = LoadState.Loading)
            viewModel.onLoadStateChanged(combinedLoadStates, 0)

            // Then
            val uiState = viewModel.uiState.first()
            uiState.shouldBeInstanceOf<WatchlistUiState.AccountLoggedIn>()
            uiState.contentState.shouldBe(ContentState.LOADING)
            uiState.loadNextPageState.shouldBe(LoadNextPageState.IDLE)
            errorsDispatcher.hasError(RuntimeException()).shouldBeFalse()
        }

    @Test
    fun nextPageStateShouldBeRetry_whenWatchlistIsNotEmptyAndRefreshFailed() =
        runTest {
            // Given

            // When
            val combinedLoadStates =
                createCombinedLoadStates(append = LoadState.Error(RuntimeException()))
            viewModel.onLoadStateChanged(combinedLoadStates, 1)

            // Then
            val uiState = viewModel.uiState.first()
            uiState.shouldBeInstanceOf<WatchlistUiState.AccountLoggedIn>()
            uiState.loadNextPageState.shouldBe(LoadNextPageState.RETRY)
            uiState.contentState.shouldBe(ContentState.SUCCESS)
            errorsDispatcher.hasError(RuntimeException()).shouldBeTrue()
        }

    @Test
    fun nextPageStateShouldBeLoading_whenWatchlistIsNotEmptyAndIsRefreshing() =
        runTest {
            // Given

            // When
            val combinedLoadStates = createCombinedLoadStates(append = LoadState.Loading)
            viewModel.onLoadStateChanged(combinedLoadStates, 1)

            // Then
            val uiState = viewModel.uiState.first()
            uiState.shouldBeInstanceOf<WatchlistUiState.AccountLoggedIn>()
            uiState.loadNextPageState.shouldBe(LoadNextPageState.LOADING)
            uiState.contentState.shouldBe(ContentState.SUCCESS)
            errorsDispatcher.hasError(RuntimeException()).shouldBeFalse()
        }

    private fun createCombinedLoadStates(
        refresh: LoadState = LoadState.NotLoading(false),
        prepend: LoadState = LoadState.NotLoading(false),
        append: LoadState = LoadState.NotLoading(false),
        source: LoadStates = LoadStates(
            refresh = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false)
        ),
        mediator: LoadStates? = null
    ) = CombinedLoadStates(
        refresh = refresh,
        prepend = prepend,
        append = append,
        source = source,
        mediator = mediator
    )
}
