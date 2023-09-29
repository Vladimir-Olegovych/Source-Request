package com.gigcreator.data.repository

import com.gigcreator.data.models.ServerJson

interface JsonRepository {
    fun add(serverJson: ServerJson)
    fun set(array: ArrayList<ServerJson>)
    fun read(): Array<ServerJson>
}