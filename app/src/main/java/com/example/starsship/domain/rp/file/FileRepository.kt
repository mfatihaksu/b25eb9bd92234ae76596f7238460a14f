package com.example.starsship.domain.rp.file

interface FileRepository {

    suspend fun getFileContent(source: Int) : String
}