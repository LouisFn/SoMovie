package com.louisfn.somovie.domain.util

import com.louisfn.somovie.domain.model.PosterPath
import com.louisfn.somovie.test.shared.kotlin.MainDispatcherRule
import com.louisfn.somovie.test.testfixtures.android.data.domain.FakeTmdbConfigurationFactory
import com.louisfn.somovie.test.testfixtures.android.repository.FakeTmdbConfigurationRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ImageUrlProviderTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDispatcher = mainDispatcherRule.testDispatcher
    private val appScope = TestScope(testDispatcher)
    private val configurationRepository = FakeTmdbConfigurationRepository()

    private lateinit var imageUrlProvider: ImageUrlProvider

    @Before
    fun setUp() {
        imageUrlProvider = ImageUrlProvider(
            configurationRepository = configurationRepository,
            defaultDispatcher = testDispatcher,
            applicationScope = appScope
        )
    }

    @Test
    fun shouldReturnsOriginalUrl_whenRequestedWidthIsNull() =
        runTest {
            // Given
            // When
            val path = PosterPath("/poster.jpg")
            val url = imageUrlProvider.get(path, null)

            // Then
            val expected = "${FakeTmdbConfigurationFactory.secureBaseUrl}original${path.value}"
            url.shouldBe(expected)
        }

    @Test
    fun shouldReturnsOriginalUrl_whenRequestedWidthIsGreaterThanTheLargestAvailableWidth() =
        runTest {
            // Given
            // When
            val path = PosterPath("/poster.jpg")
            val url = imageUrlProvider.get(path, 1000)

            // Then
            val expected = "${FakeTmdbConfigurationFactory.secureBaseUrl}original${path.value}"
            url.shouldBe(expected)
        }

    @Test
    fun shouldReturnsTheUrlOfTheSmallestImage_whenRequestedWidthIsLessThanTheSmallestAvailableWidth() =
        runTest {
            // Given
            // When
            val path = PosterPath("/poster.jpg")
            val url = imageUrlProvider.get(path, 32)

            // Then
            val expected = "${FakeTmdbConfigurationFactory.secureBaseUrl}w92${path.value}"
            url.shouldBe(expected)
        }

    @Test
    fun shouldReturnsTheUrlWhoseImageWidthIsEqualsToTheRequestedWidth() =
        runTest {
            // Given
            // When
            val path = PosterPath("/poster.jpg")
            val url = imageUrlProvider.get(path, 342)

            // Then
            val expected = "${FakeTmdbConfigurationFactory.secureBaseUrl}w342${path.value}"
            url.shouldBe(expected)
        }

    @Test
    fun shouldReturnsTheUrlWhoseImageSizeIsTheClosestToTheRequestedWidth() =
        runTest {
            // Given
            // When
            val path = PosterPath("/poster.jpg")
            val url = imageUrlProvider.get(path, 320)

            // Then
            val expected = "${FakeTmdbConfigurationFactory.secureBaseUrl}w342${path.value}"
            url.shouldBe(expected)
        }
}
