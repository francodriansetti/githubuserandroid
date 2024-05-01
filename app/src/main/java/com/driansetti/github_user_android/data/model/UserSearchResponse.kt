package com.driansetti.github_user_android.data.model

import com.driansetti.github_user_android.data.api.User

/**
 * Data class to model the search results for GitHub users.
 */
data class UserSearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)