package com.gigcreator.domain.usecase

import android.content.Context
import com.gigcreator.data.models.ServerJson
import com.gigcreator.data.repository.JsonRepositoryImpl

class JsonUseCase(private val repository: JsonRepositoryImpl) {

    fun add(serverJson: ServerJson, context: Context){
        repository.add(serverJson, context)
    }

    fun read(context: Context): Array<ServerJson> {
        return repository.read(context)
    }
}