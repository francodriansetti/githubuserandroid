package com.driansetti.github_user_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.driansetti.github_user_android.ui.compose.UserDetailsScreen
import com.driansetti.github_user_android.ui.compose.UserListScreen
import com.driansetti.github_user_android.ui.compose.UserViewModel
import com.driansetti.github_user_android.ui.theme.GithubuserandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubuserandroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserNavigation()
                }
            }
        }
    }
}

@Composable
fun UserNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "userList") {
        composable("userList") {
            val userViewModel: UserViewModel = hiltViewModel()
            UserListScreen(userViewModel = userViewModel, navController = navController)
        }
        composable("userDetails/{username}") { backStackEntry ->
            val userViewModel: UserViewModel = hiltViewModel()
            UserDetailsScreen(username = backStackEntry.arguments?.getString("username") ?: "", userViewModel = userViewModel)
        }
    }
}