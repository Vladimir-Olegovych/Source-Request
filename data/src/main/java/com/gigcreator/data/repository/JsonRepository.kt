package com.gigcreator.data.repository

import android.content.Context
import com.gigcreator.data.models.ServerJson

interface JsonRepository {
    fun add(serverJson: ServerJson, context: Context)
    fun read(context: Context): Array<ServerJson>
}