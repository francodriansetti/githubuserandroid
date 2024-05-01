package com.driansetti.github_user_android.ui.compose


import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.driansetti.github_user_android.domain.usecase.GetUserDetailsUseCase
import com.driansetti.github_user_android.domain.usecase.SearchUsersUseCase
import com.driansetti.github_user_android.ui.UserDetailsState
import com.driansetti.github_user_android.ui.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : ViewModel() {
    private val _userListState = MutableStateFlow<UserListState>(UserListState.Empty)
    val userListState: StateFlow<UserListState> = _userListState

    private val _userDetailsState = MutableStateFlow<UserDetailsState>(UserDetailsState.Empty)
    val userDetailsState: StateFlow<UserDetailsState> = _userDetailsState

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _userListState.value = UserListState.Loading
            val result = searchUsersUseCase(query)
            _userListState.value = result.fold(
                onSuccess = { users ->
                    if (users.isEmpty()) UserListState.Empty
                    else UserListState.Success(users)
                },
                onFailure = { e ->
                    UserListState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            _userDetailsState.value = UserDetailsState.Loading
            val result = getUserDetailsUseCase(username)
            _userDetailsState.value = result.fold(
                onSuccess = { user ->
                    UserDetailsState.Success(user)
                },
                onFailure = { e ->
                    UserDetailsState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }
}