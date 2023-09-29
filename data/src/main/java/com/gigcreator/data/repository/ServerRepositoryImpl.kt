package com.gigcreator.data.repository

import com.gigcreator.data.libs.DataReader
import com.gigcreator.data.libs.DataWriter
import com.gigcreator.data.models.Server
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ServerRepositoryImpl: ServerRepository {

    private val socket = DatagramSocket()
    private val defaultRequest = createRequest()

    override fun getServer(address: String, port: Int): Server =
        try {
            getInfo(InetAddress.getByName(address), port, 0)
        } catch (e: Throwable) {
            Server()
        }

    private fun getInfo(address: InetAddress, port: Int, challenge: Long, retries: Int = 0): Server {
        val request = if (challenge == 0L) defaultRequest else createRequest(challenge)
        var packet = DatagramPacket(request, request.size, address, port)
        socket.send(packet)
        val buffer = ByteArray(65535)
        packet = DatagramPacket(buffer, buffer.size)
        socket.receive(packet)
        val reader = DataReader(ByteArrayInputStream(buffer))

        if (reader.readUInt32() != 4294967295L)
            throw IllegalStateException("cut")

        val responseType = reader.readUInt8()
        if (responseType == 0x41)
            if (retries == 10)
                throw IllegalStateException("Failed after 10 retries!") else
                return getInfo(address, port, reader.readUInt32(), retries + 1)

        if (responseType != 0x49)
            throw IllegalStateException("Legacy shit!")

        return Server(
            address.hostAddress, port,
            reader.readUInt8(),
            reader.readUTF8(),
            reader.readUTF8(),
            reader.readUTF8(),
            reader.readUTF8(),
            reader.readUInt16(),
            reader.readUInt8(),
            reader.readUInt8()
        )
    }


    override fun close() {
        socket.close()
    }

    companion object {

        private val TEXT = createTextBytes("Source Engine Query")



        private fun createRequest(challenge: Long = 0L): ByteArray {
            val output = ByteArrayOutputStream()
            output.write(-1)
            output.write(-1)
            output.write(-1)
            output.write(-1)
            output.write(0x54)
            output.write(TEXT)
            if (challenge != 0L) DataWriter(output).writeUInt32(challenge)
            return output.toByteArray()
        }

        private fun createTextBytes(text: String): ByteArray {
            val output = ByteArrayOutputStream()
            val writer = DataWriter(output)
            writer.writeUTF8(text)
            return output.toByteArray()
        }

    }
}