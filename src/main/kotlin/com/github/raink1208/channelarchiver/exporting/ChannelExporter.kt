package com.github.raink1208.channelarchiver.exporting

import com.github.raink1208.channelarchiver.exporting.formats.ExportFormat
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.TextChannel

class ChannelExporter(private val channel: TextChannel, private val format: ExportFormat) {
    companion object {
        const val TXT = 0
        const val JSON = 1
    }

    fun export() {
        runBlocking {
            launch {
                val file = format.execute(channel)
                channel.sendFile(file).queue()
            }
        }
    }
}