package com.github.raink1208.channelarchiver.exporting.formats

import com.github.raink1208.channelarchiver.Main
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

interface ExportFormat {
    val extension: String
    suspend fun execute(channel: MessageChannel): File

    fun createLogFile(): File {
        val time = System.currentTimeMillis()
        return kotlin.io.path.createTempFile(Main.instance.getTempDirectory(), time.toString(), extension).toFile()
    }
}