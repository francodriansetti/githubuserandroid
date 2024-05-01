package com.driansetti.github_user_android.domain.usecase

import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.data.repository.UserRepository
import javax.inject.Inject
/**
 * Use case for fetching detailed user information from GitHub.
 * This use case encapsulates the logic required to fetch user details,
 * ensuring that the username is valid before making a request to the repository.
 *
 * @property userRepository The repository responsible for user data operations.
 */
class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    /**
     * Invokes the use case with a given username.
     *
     * @param username The GitHub username to fetch details for.
     * @return A [Result] containing either a [GitHubUser] if successful, or an error if not.
     */
    suspend operator fun invoke(username: String): Result<GitHubUser> {
        // Check if the provided username is blank and return a failure if true.
        if (username.isBlank()) {
            return Result.failure(IllegalArgumentException("Username cannot be empty"))
        }
        // Delegate to the UserRepository to fetch the user details.
        return userRepository.getUserDetails(username)
    }
}
