package com.gigcreator.data.repository

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.gigcreator.data.models.Server
import com.gigcreator.data.models.ServerJson
import java.io.File

class JsonRepositoryImpl(private val context: Context): JsonRepository {

    private val objectMapper = ObjectMapper()

    override fun add(serverJson: ServerJson) {
        try {
            objectMapper.writeValue(File(context.filesDir, "server.json"), read().plus(serverJson))
        } catch (e: Throwable) {
            objectMapper.writeValue(File(context.filesDir, "server.json"), arrayOf(serverJson))
        }
    }

    override fun set(array: ArrayList<ServerJson>) =
        objectMapper.writeValue(File(context.filesDir, "server.json"), array)


    override fun read(): Array<ServerJson> =
        objectMapper.readValue(File(context.filesDir, "server.json"), Array<ServerJson>::class.java)
}