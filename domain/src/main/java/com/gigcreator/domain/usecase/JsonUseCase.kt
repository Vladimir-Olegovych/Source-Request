package com.gigcreator.domain.usecase

import com.gigcreator.data.models.ServerJson
import com.gigcreator.data.repository.JsonRepositoryImpl

class JsonUseCase(private val repository: JsonRepositoryImpl) {

    fun add(serverJson: ServerJson){
        repository.add(serverJson)
    }

    fun set(array: ArrayList<ServerJson>) {
        return repository.set(array)
    }

    fun read(): Array<ServerJson> {
        return repository.read()
    }
}