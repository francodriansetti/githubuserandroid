package com.driansetti.github_user_android.domain.usecase

import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.data.repository.UserRepository
import javax.inject.Inject

/**
 * Use case for searching GitHub users based on a query.
 * This class encapsulates the logic to query GitHub users,
 * delegating the data fetching to the UserRepository.
 *
 * @property userRepository The repository responsible for handling user data operations.
 */
class SearchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    /**
     * Invokes the use case with a given search query.
     *
     * This method provides a clean and direct way to perform user searches,
     * abstracting the complexities of data retrieval from the UI layer.
     *
     * @param query The search string used to query GitHub users.
     * @return A [Result] containing either a list of [GitHubUser] objects if successful, or an error if not.
     */
    suspend operator fun invoke(query: String): Result<List<GitHubUser>> {
        // Execute the user search operation through the UserRepository.
        return userRepository.searchUsers(query)
    }
}
