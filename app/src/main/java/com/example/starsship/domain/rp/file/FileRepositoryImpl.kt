package com.example.starsship.domain.rp.file

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepositoryImpl @Inject constructor(
    private val context : Context
): FileRepository {
    override suspend fun getFileContent(source: Int): String {
        return context.resources.openRawResource(source).bufferedReader().use {
            it.readText()
        }
    }
}