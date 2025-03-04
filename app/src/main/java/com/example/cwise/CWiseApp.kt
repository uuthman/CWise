package com.example.cwise

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


@HiltAndroidApp
class CWiseApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
}