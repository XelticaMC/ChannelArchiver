package com.github.raink1208.channelarchiver

import com.github.raink1208.channelarchiver.commands.ChannelArchiveCommand
import com.github.raink1208.channelarchiver.listeners.CommandListener
import com.github.raink1208.channelarchiver.util.Config
import net.dv8tion.jda.api.JDABuilder
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

fun main() {
    val token = Config.getToken()

    if (token !== null) {
        Main(token).start()
    } else {
        throw IllegalArgumentException("tokenが見つかりません")
    }
}

class Main(token: String) {
    companion object {
        lateinit var instance: Main
        private set

        const val COMMAND_PREFIX = "arc!"
    }

    private val logger = LoggerFactory.getLogger(Main::class.java)

    val jda = JDABuilder.createDefault(token).build()
    val commandListener = CommandListener()

    fun start() {
        instance = this

        createTempDirectory()
        logger.info("created TempDirectory")

        jda.addEventListener(commandListener)
        logger.info("loaded CommandListener")
        jda.upsertCommand(ChannelArchiveCommand.commandData).queue()
        jda.getGuildById("559829649912496128")?.upsertCommand(ChannelArchiveCommand.commandData)?.queue()
    }

    private fun createTempDirectory() {
        if (getTempDirectory().exists()) return
        Files.createDirectory(getTempDirectory())
    }

    fun getTempDirectory(): Path {
        return Paths.get("./tmp")
    }
}