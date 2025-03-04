package com.example.cwise

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.cwise.data.database.ConverterDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class CWiseAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: ConverterDatabase

    protected lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
    }

    @After
    fun tearDown() {
        db.close()
    }
}