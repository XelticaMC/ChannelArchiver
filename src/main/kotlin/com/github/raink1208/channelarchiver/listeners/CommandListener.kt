package com.github.raink1208.channelarchiver.listeners

import com.github.raink1208.channelarchiver.Main
import com.github.raink1208.channelarchiver.command.CommandBase
import com.github.raink1208.channelarchiver.command.CommandHandler
import com.github.raink1208.channelarchiver.commands.ChannelArchiveCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandListener: ListenerAdapter() {
    val commandHandler = CommandHandler()
    private val commands = setOf<CommandBase>(ChannelArchiveCommand)

    init {
        commandHandler.registerCommands(commands)
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        commandHandler.findAndExecute(event)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        val command = event.message.contentRaw.split(" ")

        if (command[0].isEmpty()) return
        if (!command[0].startsWith(Main.COMMAND_PREFIX)) return

        val cmd = command[0].drop(Main.COMMAND_PREFIX.length)
        val args = command.drop(1).joinToString(" ")

        commandHandler.findAndExecute(cmd, event.message, args)
    }
}