package com.louisfn.somovie.feature.home.watchlist

import androidx.activity.ComponentActivity
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.louisfn.somovie.feature.home.watchlist.WatchlistAction.ShowUndoSwipeToDismissSnackbar
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.ContentState
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.LoadNextPageState
import com.louisfn.somovie.feature.login.LogInTestTag
import com.louisfn.somovie.test.fixtures.domain.FakeMovieFactory
import com.louisfn.somovie.test.fixtures.feature.login.FakeLogInManager
import com.louisfn.somovie.test.shared.compose.SnackBarShortDuration
import com.louisfn.somovie.test.shared.paging.createLazyPagingItems
import com.louisfn.somovie.ui.common.R
import com.louisfn.somovie.ui.component.ComponentTestTag
import com.louisfn.somovie.ui.theme.AppTheme
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class WatchListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    //region Account state

    @Test
    fun shouldShowLoginContent_whenStateIsAccountDisconnected() {
        // Given
        val uiState = WatchlistUiState.AccountDisconnected

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(emptyList<MovieItem>())

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            onNodeWithTag(LogInTestTag.LogInLayout)
                .assertIsDisplayed()
            onNodeWithTag(WatchlistTestTag.WatchlistContent)
                .assertDoesNotExist()
        }
    }

    @Test
    fun shouldShowAccountContent_whenStateIsAccountLoggedIn() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn()

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(emptyList<MovieItem>())

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            onNodeWithTag(WatchlistTestTag.WatchlistContent)
                .assertIsDisplayed()
            onNodeWithTag(LogInTestTag.LogInLayout)
                .assertDoesNotExist()
        }
    }

    //endregion

    //region ContentState

    @Test
    fun shouldShowRetry_whenContentStateIsRetry() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.RETRY,
            loadNextPageState = LoadNextPageState.IDLE,
        )

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(emptyList<MovieItem>())

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            onNodeWithTag(ComponentTestTag.Retry)
                .assertIsDisplayed()
            onNodeWithTag(WatchlistTestTag.LazyColum)
                .assertDoesNotExist()
            onNodeWithTag(ComponentTestTag.IndeterminateProgressIndicator)
                .assertDoesNotExist()
        }
    }

    @Test
    fun shouldShowLoader_whenContentStateIsLoading() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.LOADING,
            loadNextPageState = LoadNextPageState.IDLE,
        )

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(emptyList<MovieItem>())

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            onNodeWithTag(ComponentTestTag.IndeterminateProgressIndicator)
                .assertIsDisplayed()
            onNodeWithTag(ComponentTestTag.Retry)
                .assertDoesNotExist()
            onNodeWithTag(WatchlistTestTag.LazyColum)
                .assertDoesNotExist()
        }
    }

    @Test
    fun shouldShowLazyColumnItems_whenContentStateIsSuccess() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.SUCCESS,
            loadNextPageState = LoadNextPageState.IDLE,
        )
        val items = FakeMovieFactory.create(2)
            .map { MovieItem(movie = it, isHidden = false) }
        val firstItem = items[0]
        val secondItem = items[1]

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(items)

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            with(
                onNodeWithTag(WatchlistTestTag.LazyColum)
                    .onChildren()
                    .filter(hasClickAction()),
            ) {
                assertCountEquals(2)
                get(0).assert(hasText(firstItem.movie.title) and hasText(firstItem.movie.overview))
                get(1).assert(hasText(secondItem.movie.title) and hasText(secondItem.movie.overview))
            }
        }
    }

    //endregion

    //region Lazy column Items

    @Test
    fun shouldShowLazyColumnItemsWithLoaderItem_whenContentStateIsSuccessAndNextPageStateIsLoading() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.SUCCESS,
            loadNextPageState = LoadNextPageState.LOADING,
        )
        val items = FakeMovieFactory.create(2)
            .map { MovieItem(movie = it, isHidden = false) }

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(items)

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            with(
                onNodeWithTag(WatchlistTestTag.LazyColum).onChildren(),
            ) {
                filter(hasClickAction())
                    .assertCountEquals(2)
                onLast()
                    .assert(hasTestTag(ComponentTestTag.IndeterminateProgressIndicator))
            }
        }
    }

    @Test
    fun shouldShowLazyColumnItemsWithRetryItem_whenContentStateIsSuccessAndNextPageStateIsLoading() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.SUCCESS,
            loadNextPageState = LoadNextPageState.RETRY,
        )
        val items = FakeMovieFactory.create(2)
            .map { MovieItem(movie = it, isHidden = false) }

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(items)

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            with(
                onNodeWithTag(WatchlistTestTag.LazyColum).onChildren(),
            ) {
                filter(hasClickAction())
                    .assertCountEquals(3)
                onLast()
                    .assert(hasTestTag(ComponentTestTag.TextRetry))
            }
        }
    }

    @Test
    fun shouldNotShowLazyColumnItem_whenItemIsHidden() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.SUCCESS,
            loadNextPageState = LoadNextPageState.IDLE,
        )

        val firstItem = MovieItem(FakeMovieFactory.create(), true)
        val secondItem = MovieItem(FakeMovieFactory.create(), false)
        val items = listOf(firstItem, secondItem)

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(items)

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            with(
                onNodeWithTag(WatchlistTestTag.LazyColum)
                    .onChildren()
                    .filter(hasClickAction()),
            ) {
                assertCountEquals(1)
                get(0).assert(hasText(secondItem.movie.title))
            }
        }
    }

    @Test
    fun shouldNotShowLazyColumn_whenContentStateIsSuccessAndPagingItemsIsEmpty() {
        // Given
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.SUCCESS,
            loadNextPageState = LoadNextPageState.IDLE,
        )

        // When
        composeTestRule.setContent {
            val pagingItems = createLazyPagingItems(emptyList<MovieItem>())

            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    pagingItems = pagingItems,
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                )
            }
        }

        // Then
        with(composeTestRule) {
            onNodeWithTag(ComponentTestTag.IndeterminateProgressIndicator)
                .assertDoesNotExist()
            onNodeWithTag(ComponentTestTag.Retry)
                .assertDoesNotExist()
            onNodeWithTag(WatchlistTestTag.LazyColum)
                .assertDoesNotExist()
        }
    }

    //endregion

    //region Swipe to dismiss movie item

    @Test
    fun shouldSwipedItem_whenPerformSwipeRight() {
        // Given
        val movie = FakeMovieFactory.create()

        // When
        var isSwiped = false
        composeTestRule.setContent {
            AppTheme {
                WatchlistMovieItem(
                    movie = movie,
                    isHidden = false,
                    onClick = {},
                    onSwipe = { isSwiped = true },
                )
            }
        }
        composeTestRule.onRoot()
            .performTouchInput { swipeRight() }

        // Then
        isSwiped.shouldBeTrue()

        composeTestRule
            .onNodeWithTag(WatchlistTestTag.MovieItemContent)
            .assertIsNotDisplayed()
        composeTestRule
            .onNodeWithTag(WatchlistTestTag.MovieItemDismissedBackground)
            .assertIsDisplayed()
    }

    @Test
    fun shouldNotSwipedItem_whenPerformSwipeLeft() {
        // Given
        val movie = FakeMovieFactory.create()

        // When
        var isSwiped = false
        composeTestRule.setContent {
            AppTheme {
                WatchlistMovieItem(
                    movie = movie,
                    isHidden = false,
                    onClick = {},
                    onSwipe = { isSwiped = true },
                )
            }
        }
        composeTestRule.onRoot()
            .performTouchInput { swipeLeft() }

        // Then
        isSwiped.shouldBeFalse()
        composeTestRule
            .onNodeWithTag(WatchlistTestTag.MovieItemContent)
            .assertIsDisplayed()
    }

    //endregion

    //region Undo dismissed Snackbar

    @Test
    fun shouldInvokedonSnackbarActionPerform_whenSnackbarActionClicked() {
        // Given
        val movieId = 1L
        val action = flowOf<WatchlistAction>(ShowUndoSwipeToDismissSnackbar(movieId))
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.NONE,
            loadNextPageState = LoadNextPageState.IDLE,
        )
        val snackBarActionText =
            composeTestRule.activity.getString(R.string.watchlist_remove_from_watchlist_action)

        // When
        var isSnackbarActionPerformed = false
        composeTestRule.setContent {
            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    action = action,
                    pagingItems = createLazyPagingItems(emptyList()),
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                    onSnackbarActionPerform = { isSnackbarActionPerformed = true },
                    onSnackbarDismiss = {},
                )
            }
        }
        composeTestRule.onNodeWithText(snackBarActionText)
            .performClick()

        // Then
        isSnackbarActionPerformed.shouldBeTrue()
        composeTestRule.onNodeWithText(snackBarActionText)
            .assertDoesNotExist()
    }

    @Test
    fun shouldInvokeonSnackbarDismiss_whenSnackbarActionClicked() {
        // Given
        val movieId = 1L
        val action = flowOf<WatchlistAction>(ShowUndoSwipeToDismissSnackbar(movieId))
        val uiState = WatchlistUiState.AccountLoggedIn(
            contentState = ContentState.NONE,
            loadNextPageState = LoadNextPageState.IDLE,
        )
        val snackBarMessage =
            composeTestRule.activity.getString(R.string.watchlist_remove_from_watchlist_confirm_message)

        // When
        var isSnackbarDismissed = false
        composeTestRule.setContent {
            AppTheme {
                WatchlistScreen(
                    uiState = uiState,
                    action = action,
                    pagingItems = createLazyPagingItems(emptyList()),
                    scaffoldState = rememberScaffoldState(),
                    logInManager = FakeLogInManager(),
                    onMovieClick = {},
                    onMovieSwipe = {},
                    onSnackbarActionPerform = {},
                    onSnackbarDismiss = { isSnackbarDismissed = true },
                )
            }
        }
        composeTestRule.mainClock.advanceTimeBy(SnackBarShortDuration)

        // Then
        isSnackbarDismissed.shouldBeTrue()
        composeTestRule.onNodeWithText(snackBarMessage)
            .assertDoesNotExist()
    }

    //endregion
}
