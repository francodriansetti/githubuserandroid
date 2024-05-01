package com.driansetti.github_user_android.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.ui.UserListState

@Composable
fun UserListScreen(
    userViewModel: UserViewModel = viewModel(),
    navController: NavController
) {
    // State management for search input and debouncing logic
    var searchText by remember { mutableStateOf("") }
    var lastSearchTime by remember { mutableLongStateOf(0L) }

    // Region: User Search UI
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    val currentTime = System.currentTimeMillis()
                    // Throttle requests to no more than one per second
                    if (currentTime - lastSearchTime > 1000 && searchText.length > 1) {
                        lastSearchTime = currentTime
                        userViewModel.searchUsers(searchText)
                    }
                },
                label = { Text("Search GitHub Users") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchText.length > 1) {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastSearchTime > 1000) {
                                lastSearchTime = currentTime
                                userViewModel.searchUsers(searchText)
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                },
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            UserList(userViewModel = userViewModel, navController)
        }
    }
    // EndRegion
}

@Composable
fun UserList(userViewModel: UserViewModel, navController: NavController) {
    val state = userViewModel.userListState.collectAsState()

    // Region: User List Display
    when (val userList = state.value) {
        is UserListState.Loading -> CircularProgressIndicator()
        is UserListState.Error -> Text("Error: ${userList.message}")
        is UserListState.Success -> {
            LazyColumn {
                items(userList.users) { user ->
                    ListItem(user = user, navController )
                }
            }
        }
        is UserListState.Empty -> Text("No users found.")
    }
    // EndRegion
}

@Composable
fun ListItem(user: GitHubUser, navController: NavController) {
    // Region: Single User Display Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("userDetails/${user.login}") },

        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.login, style = MaterialTheme.typography.headlineMedium)
            Text(text = "URL: ${user.htmlUrl}", style = MaterialTheme.typography.bodyLarge)
        }
    }
    // EndRegion
}
