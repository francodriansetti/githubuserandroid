package com.driansetti.github_user_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoreApplication : Application() {
    // Required by Hilt, can add application-level initializations here
}