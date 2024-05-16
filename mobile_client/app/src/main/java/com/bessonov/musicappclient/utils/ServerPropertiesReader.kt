package com.bessonov.musicappclient.utils

import java.util.Properties

private const val SERVER = "server.properties"

object ServerPropertiesReader {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader?.getResourceAsStream(SERVER)
        properties.load(file)
    }

    fun getProperty(key: String): String = properties.getProperty(key)
}
