package com.driansetti.github_user_android

import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.data.repository.UserRepository
import com.driansetti.github_user_android.domain.usecase.SearchUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchUsersUseCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var searchUsersUseCase: SearchUsersUseCase

    @Before
    fun setUp() {
        mockRepository = mockk(relaxed = true)
        searchUsersUseCase = SearchUsersUseCase(mockRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when search users returns success, verify result matches expected API data`(): Unit = runBlocking {
        // Given
        val expectedUser = GitHubUser(
            login = "francodriansetti",
            avatarUrl = "https://avatars.githubusercontent.com/u/7194678?v=4",
            htmlUrl = "https://github.com/francodriansetti",
            followersUrl = "https://api.github.com/users/francodriansetti/followers",
            followingUrl = "https://api.github.com/users/francodriansetti/following{/other_user}",
            gistsUrl = "https://api.github.com/users/francodriansetti/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/francodriansetti/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/francodriansetti/subscriptions",
            organizationsUrl = "https://api.github.com/users/francodriansetti/orgs",
            reposUrl = "https://api.github.com/users/francodriansetti/repos",
            eventsUrl = "https://api.github.com/users/francodriansetti/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/francodriansetti/received_events",
            type = "User",
            siteAdmin = false,
            score = 1.0,
            name = null,
            company = null,
            blog = null,
            location = null,
            email = null,
            bio = null,
            publicRepos = 0,
            publicGists = 0,
            followers = 0,
            following = 0,
            hireable = null
        )
        val users = listOf(expectedUser)
        coEvery { mockRepository.searchUsers(any()) } returns Result.success(users)

        // When
        val result = searchUsersUseCase("francodriansetti")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(users, result.getOrNull())
        result.getOrNull()?.let {
            assertEquals("francodriansetti", it.first().login)
            assertEquals("https://github.com/francodriansetti", it.first().htmlUrl)
            assertEquals("User", it.first().type)
        }
    }
}
