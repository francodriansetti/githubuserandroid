package com.driansetti.github_user_android.ui

import com.driansetti.github_user_android.data.model.GitHubUser

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    object Empty : UserDetailsState()
    data class Success(val user: GitHubUser) : UserDetailsState()
    data class Error(val message: String) : UserDetailsState()
}