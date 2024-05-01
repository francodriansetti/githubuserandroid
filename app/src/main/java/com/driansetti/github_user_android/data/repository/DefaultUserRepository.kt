package com.driansetti.github_user_android.data.repository

import com.driansetti.github_user_android.data.api.GitHubService
import com.driansetti.github_user_android.data.api.toGitHubUser
import com.driansetti.github_user_android.data.model.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
// This class provides the concrete implementation of the UserRepository interface, utilizing a GitHubService.
class DefaultUserRepository @Inject constructor(
    private val githubService: GitHubService
) : UserRepository {

    // Region: User Search
    // Returns a Result object containing a list of GitHubUser objects or an error message.
    override suspend fun searchUsers(query: String): Result<List<GitHubUser>> {
        return try {
            withContext(Dispatchers.IO) {
                val response = githubService.searchUsers(query)
                if (response.isSuccessful) {
                    response.body()?.let { searchResponse ->
                        Result.success(searchResponse.items.map { it.toGitHubUser() })
                    } ?: Result.failure(Exception("No data found"))
                } else if (response.code() == 403) {
                    Result.failure(Exception("403 GitHub API rate limit exceeded"))
                } else {
                    Result.failure(Exception("Error fetching users: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}", e))
        }
    }
    // End Region

    // Region: User Details
    // Returns a Result object containing a GitHubUser object or an error message.
    override suspend fun getUserDetails(username: String): Result<GitHubUser> {
        return try {
            withContext(Dispatchers.IO) {
                val response = githubService.getUserDetails(username)
                if (response.isSuccessful) {
                    response.body()?.let { userDetails ->
                        // Convert the GitHubUserDetails object to a GitHubUser object
                        Result.success(userDetails.toGitHubUser())
                    } ?: Result.failure(Exception("User not found"))
                } else if (response.code() == 403) {
                Result.failure(Exception("403 GitHub API rate limit exceeded"))
            } else {
                Result.failure(Exception("Error fetching users: ${response.message()}"))
            }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}", e))
        }
    }
}
// End Region