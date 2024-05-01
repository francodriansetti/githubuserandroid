package com.driansetti.github_user_android.di

import com.driansetti.github_user_android.data.api.GitHubService
import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.data.repository.DefaultUserRepository
import com.driansetti.github_user_android.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideUserRepository(gitHubService: GitHubService): UserRepository {
        return DefaultUserRepository(gitHubService)
    }
}

class SearchUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(query: String): Result<List<GitHubUser>> {
        return userRepository.searchUsers(query)
    }
}

class GetUserDetailsUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String): Result<GitHubUser> {
        return userRepository.getUserDetails(username)
    }
}