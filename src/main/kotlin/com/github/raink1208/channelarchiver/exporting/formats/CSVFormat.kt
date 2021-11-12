package com.github.raink1208.channelarchiver.exporting.formats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CSVFormat: ExportFormat {
    override val extension: String = ".csv"
    override suspend fun execute(channel: MessageChannel): File {
        val file = createLogFile()

        withContext(Dispatchers.IO) {
            file.writer().use {
                for (message in channel.getHistoryBefore(channel.latestMessageId, 100).complete().retrievedHistory) {
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

        content.replace("\n", "\\n")
        content.replace(",", ".")
        return "$author,$content"
    }
}