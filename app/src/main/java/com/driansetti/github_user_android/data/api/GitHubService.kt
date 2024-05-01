package com.driansetti.github_user_android.data.api

import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.data.model.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    /**
     * Search for GitHub users based on a query string.
     * @param query the search keywords or qualifiers (e.g., followers:>100).
     * @return a [Response] containing a [UserSearchResponse] object.
     */
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): Response<UserSearchResponse>

    /**
     * Get detailed information about a specific GitHub user.
     * @param username the GitHub username.
     * @return a [Response] containing a [GitHubUserDetails] object.
     */
    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): Response<GitHubUserDetails>
}



/**
 * Data class to represent a basic GitHub user in search results.
 */
data class User(
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val followersUrl: String = "",
    val followingUrl: String = "",
    val gistsUrl: String = "",
    val starredUrl: String = "",
    val subscriptionsUrl: String = "",
    val organizationsUrl: String = "",
    val reposUrl: String = "",
    val eventsUrl: String = "",
    val receivedEventsUrl: String = "",
    val type: String = "",
    val siteAdmin: Boolean = false,
    val score: Double = 0.0
)

/**
 * Data class for detailed user information.
 */
data class GitHubUserDetails(
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val public_repos: Int,
    val public_gists: Int,
    val followers: Int,
    val following: Int,
    val created_at: String,
    val updated_at: String,
    val bio: String?,
    val twitter_username: String?,
    val hireable: Boolean?,
    val star_count: Int
)


// Mapping from basic User (search results) to GitHubUser
fun User.toGitHubUser(): GitHubUser = GitHubUser(
    login = login,
    avatarUrl = avatar_url,
    htmlUrl = html_url,
    name = null,
    company = null,
    blog = null,
    location = null,
    email = null,
    publicRepos = 0,
    publicGists = 0,
    followers = 0,
    following = 0,
    hireable = null,
    bio = null,
    followersUrl = "",
    followingUrl = "",
    gistsUrl = "",
    starredUrl = "",
    subscriptionsUrl = "",
    organizationsUrl = "",
    reposUrl = "",
    eventsUrl = "",
    receivedEventsUrl = "",
    type = "",
    siteAdmin = false,
    score = 0.0
)

// Mapping from detailed GitHubUserDetails to GitHubUser
fun GitHubUserDetails.toGitHubUser(): GitHubUser = GitHubUser(
    login = login,
    avatarUrl = avatar_url,
    htmlUrl = html_url,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    publicRepos = public_repos,
    publicGists = public_gists,
    followers = followers,
    following = following,
    hireable = hireable,
    bio = bio,
    followersUrl = "",
    followingUrl = "",
    gistsUrl = "",
    starredUrl = "",
    subscriptionsUrl = "",
    organizationsUrl = "",
    reposUrl = "",
    eventsUrl = "",
    receivedEventsUrl = "",
    type = "",
    siteAdmin = false,
    score = 0.0
)

