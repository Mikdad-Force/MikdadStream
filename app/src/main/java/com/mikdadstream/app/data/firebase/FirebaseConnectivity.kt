package com.mikdadstream.app.data.firebase

import java.net.InetSocketAddress
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FirebaseConnectivity {

    private val hosts = listOf(
        "identitytoolkit.googleapis.com",
        "firestore.googleapis.com",
    )

    suspend fun isReachable(timeoutMs: Int = 3500): Boolean = withContext(Dispatchers.IO) {
        hosts.any { host -> tlsHandshakeOk(host, timeoutMs) }
    }

    private fun tlsHandshakeOk(host: String, timeoutMs: Int): Boolean = runCatching {
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        (factory.createSocket() as SSLSocket).use { socket ->
            socket.soTimeout = timeoutMs
            socket.connect(InetSocketAddress(host, 443), timeoutMs)
            socket.startHandshake()
            true
        }
    }.getOrDefault(false)
}
