package com.github.raink1208.channelarchiver.util

import org.yaml.snakeyaml.Yaml
import java.nio.file.Paths

object Config {
    private val file = Paths.get("./setting.yml").toFile()
    private val config: Map<*, *>

    init {
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("token: TOKEN HERE")
        }

        config = Yaml().loadAs(file.reader(Charsets.UTF_8), Map::class.java)
    }

    fun getToken(): String? {
        val token = config["token"]
        return if (token is String && token != "TOKEN HERE") {
            token
        } else {
            null
        }
    }
}