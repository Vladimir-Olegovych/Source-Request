package com.gigcreator.domain.usecase

import com.gigcreator.data.repository.ServerRepositoryImpl
import com.gigcreator.data.models.Server
import java.util.concurrent.Executors

class ServerUseCase(private val repository: ServerRepositoryImpl) {

    fun execute(address: String, port: Int): Server {
        return repository.getServer(address, port)
    }

}