package com.github.raink1208.channelarchiver.exporting.formats

import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class JSONFormat : ExportFormat {
    override val extension: String = ".json"
    override suspend fun execute(channel: MessageChannel): File {
        val file = createLogFile()
        TODO("Not yet implemented")
    }
}