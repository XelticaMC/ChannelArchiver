package com.github.raink1208.channelarchiver.command

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface CommandBase {
    val commandData: CommandData

    fun execute(event: SlashCommandEvent)
}