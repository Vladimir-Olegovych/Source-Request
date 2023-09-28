package com.gigcreator.data.repository

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.gigcreator.data.models.ServerJson
import java.io.File

class JsonRepositoryImpl: JsonRepository {

    private val objectMapper = ObjectMapper()

    override fun add(serverJson: ServerJson, context: Context) {
        try {
            objectMapper.writeValue(File(context.filesDir, "server.json"), read(context).plus(serverJson))
        } catch (e: Throwable) {
            objectMapper.writeValue(File(context.filesDir, "server.json"), arrayOf(serverJson))
        }
    }

    override fun read(context: Context): Array<ServerJson> = objectMapper.readValue(File(context.filesDir, "server.json"), Array<ServerJson>::class.java)
}