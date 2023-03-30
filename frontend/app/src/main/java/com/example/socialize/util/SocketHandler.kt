package com.example.socialize.util

import android.util.Log
import com.example.socialize.config.BASE_URL
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket
    val ls: LocalStorage = LocalStorage()

    @Synchronized
    fun setSocket() {
        try {
            val options = IO.Options()
            options.forceNew = true
            options.reconnectionAttempts = Int.MAX_VALUE
            options.timeout = 10000
            options.query = "token=" + ls.getValue("authToken") + "&" + "zip=" + ls.getValue("zip")

            mSocket = IO.socket(BASE_URL, options)
        } catch (e: URISyntaxException) {
            Log.d("ERROR", e.toString())
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}