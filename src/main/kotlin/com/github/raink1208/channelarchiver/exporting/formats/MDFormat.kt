package com.github.raink1208.channelarchiver.exporting.formats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageHistory
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

class MDFormat:ExportFormat {
    override val extension: String = ".md"
    override suspend fun execute(channel: MessageChannel): File {
        val file = createLogFile()
        val history = MessageHistory(channel)
        withContext(Dispatchers.IO) {
            val pw = PrintWriter(BufferedWriter(FileWriter(file, StandardCharsets.UTF_8)))
            pw.write("## " + channel.name + "\n\n")
            while (true) {
                val retrieve = history.retrievePast(100).complete()
                if (retrieve.isEmpty()) break
            }

            val messages = history.retrievedHistory.reversed()

            for (message in messages) {
                val author = message.author.name
                val content = message.contentDisplay
                val timeStamp = message.timeCreated.plusHours(9).toLocalTime()
                content.replace("\n", "  ")
                pw.write("---\n\n")
                pw.write("$author $timeStamp\n\n")
                pw.write("$content\n\n")
            }
            pw.close()
        }
        return file
    }
}