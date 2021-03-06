package com.github.raink1208.channelarchiver.command

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface CommandBase {
    val commandData: CommandData

    fun execute(event: SlashCommandInteractionEvent)
    fun execute(command: String, message: Message, args: String)
}