package com.example.cwise.core.utils

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow

fun TextFieldState.textAsFlow(): Flow<CharSequence>
        = snapshotFlow { text }