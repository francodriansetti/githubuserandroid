package com.driansetti.github_user_android.ui

import com.driansetti.github_user_android.data.model.GitHubUser

sealed class UserListState {
    object Loading : UserListState()
    object Empty : UserListState()
    data class Success(val users: List<GitHubUser>) : UserListState()
    data class Error(val message: String) : UserListState()
}