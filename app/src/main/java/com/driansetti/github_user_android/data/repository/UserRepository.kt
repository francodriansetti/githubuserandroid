package com.driansetti.github_user_android.data.repository

import com.driansetti.github_user_android.data.model.GitHubUser

interface UserRepository {
    suspend fun searchUsers(query: String): Result<List<GitHubUser>>
    suspend fun getUserDetails(username: String): Result<GitHubUser>
}