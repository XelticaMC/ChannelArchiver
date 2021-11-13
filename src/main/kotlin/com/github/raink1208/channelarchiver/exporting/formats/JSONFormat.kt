package com.github.raink1208.channelarchiver.exporting.formats

import com.github.raink1208.channelarchiver.models.AuthorJSONModel
import com.github.raink1208.channelarchiver.models.MessageJSONModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageHistory
import java.io.File

class JSONFormat : ExportFormat {
    override val extension: String = ".json"
    override suspend fun execute(channel: MessageChannel): File {
        val file = createLogFile()
        val history = MessageHistory(channel)
        val list = mutableListOf<MessageJSONModel>()
        withContext(Dispatchers.IO) {
            while (true) {
                val retrieve = history.retrievePast(100).complete()
                if (retrieve.isEmpty()) break
            }

            val messages = history.retrievedHistory.reversed()

            for (message in messages) {
                val messageModel = MessageJSONModel()
                val authorModel = AuthorJSONModel()
                messageModel.id = message.id
                messageModel.content = message.contentRaw
                messageModel.channelId = message.channel.id

                authorModel.username = message.author.name
                authorModel.id = message.author.id

                messageModel.author = authorModel

                list.add(messageModel)
            }
            file.writeText(Gson().toJson(list))
        }
        return file
    }
}