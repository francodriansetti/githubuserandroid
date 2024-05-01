package com.driansetti.github_user_android.ui.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.driansetti.github_user_android.data.model.GitHubUser
import com.driansetti.github_user_android.ui.UserDetailsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

// SuppressLint annotation is used here to ignore specific IDE or lint warnings.
// OptIn is used to denote the use of experimental Material3 API features.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(username: String, userViewModel: UserViewModel) {
    // Collects user details state from the ViewModel as a Compose state.
    val userDetails = userViewModel.userDetailsState.collectAsState().value

    // LaunchedEffect is used here to call the getUserDetails method when the username changes.
    LaunchedEffect(key1 = username) {
        userViewModel.getUserDetails(username)
    }

    // Scaffold is a Material3 layout structure that provides slots for app bars, content, and other elements.
    Scaffold(
        topBar = {
            // TopAppBar displays a simple title.
            TopAppBar(title = { Text(text = "User Details") })
        }
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Region: User Details Content
            when (userDetails) {
                is UserDetailsState.Loading -> {
                    Log.d("UserDetailsScreen", "Loading")
                    CircularProgressIndicator()
                }
                is UserDetailsState.Error -> {
                    Log.d("UserDetailsScreen", "Error: ${userDetails.message}")
                    Text("Error: ${userDetails.message}")
                }
                is UserDetailsState.Success -> {
                    Log.d("UserDetailsScreen", "Showing details")
                    Spacer(modifier = Modifier.padding(8.dp))
                    UserDetails(user = userDetails.user)
                }
                UserDetailsState.Empty -> {
                    Log.d("UserDetailsScreen", "Empty State")
                    Text("No details available.")
                }
            }
            // EndRegion
        }
    }
}

@Composable
fun UserDetails(user: GitHubUser) {
    // Region: UserDetails Layout
    Column {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "User Avatar",
            modifier = Modifier.padding(34.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Username: ${user.login}", style = MaterialTheme.typography.bodyLarge)
        user.name?.let { Text(text = "Name: $it", style = MaterialTheme.typography.bodyLarge) }
        user.company?.let { Text(text = "Company: $it", style = MaterialTheme.typography.bodyLarge) }
        user.blog?.let { Text(text = "Blog: $it", style = MaterialTheme.typography.bodyLarge) }
        user.location?.let { Text(text = "Location: $it", style = MaterialTheme.typography.bodyLarge) }
        Text(text = "Public Repos: ${user.publicRepos}", style = MaterialTheme.typography.bodyLarge)
        user.hireable?.let { Text(text = "Hireable: ${if (it) "Yes" else "No"}", style = MaterialTheme.typography.bodyLarge) }
        user.bio?.let { Text(text = "Bio: $it", style = MaterialTheme.typography.bodyLarge) }
        Spacer(modifier = Modifier.fillMaxSize().padding(16.dp))
    }
    // EndRegion
}
