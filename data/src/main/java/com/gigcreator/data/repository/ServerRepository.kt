package com.gigcreator.data.repository

import com.gigcreator.data.models.Server
import java.io.IOException

interface ServerRepository {
    fun getServer(address: String, port: Int): Server

    @Throws(IOException::class)
    fun close()
}