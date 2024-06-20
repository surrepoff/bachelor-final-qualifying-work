package com.bessonov.musicappclient.manager

import android.content.Context
import com.bessonov.musicappclient.R
import java.io.InputStream
import java.util.Properties

class ConfigManager(private val context: Context) {

    private val properties: Properties = Properties()

    init {
        loadProperties()
    }

    private fun loadProperties() {
        val rawResource: InputStream = context.resources.openRawResource(R.raw.config)
        properties.load(rawResource)
        rawResource.close()
    }

    fun getProperty(key: String): String? {
        return properties.getProperty(key)
    }

    fun getServerIp(): String {
        return "http://" + properties.getProperty("server.ip") + ":" +
                properties.getProperty("server.port")
    }
}
