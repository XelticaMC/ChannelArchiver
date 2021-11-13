package com.github.raink1208.channelarchiver.exporting.formats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageHistory
import java.io.File

class TextFormat: ExportFormat {
    override val extension: String = ".txt"
    override suspend fun execute(channel: MessageChannel): File {
        val file = createLogFile()
        val history = MessageHistory(channel)
        withContext(Dispatchers.IO) {
            while (true) {
                val retrieve = history.retrievePast(100).complete()
                if (retrieve.isEmpty()) break
            }

            val messages = history.retrievedHistory.reversed()

            file.writer().use {
                for (message in messages) {
                    it.write(getRow(message))
                    it.write("\n")
                }
            }
        }
        return file
    }

    private fun getRow(message: Message): String {
        val author = message.author.name
        val content = message.contentRaw

        val replaced = content.replace("\n", "  ")

        return "$author: $replaced"
    }
}