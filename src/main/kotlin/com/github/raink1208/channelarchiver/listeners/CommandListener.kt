package com.github.raink1208.channelarchiver.listeners

import com.github.raink1208.channelarchiver.command.CommandBase
import com.github.raink1208.channelarchiver.command.CommandHandler
import com.github.raink1208.channelarchiver.commands.ChannelArchiveCommand
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
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
}