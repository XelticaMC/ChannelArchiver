package com.github.raink1208.channelarchiver.commands

import com.github.raink1208.channelarchiver.command.CommandBase
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object ChannelArchiveCommand: CommandBase {
    override val commandData: CommandData = CommandData("archive", "チャンネルを外部ファイルに出力します (export type = [0 = csv, 1 = json, 2 = html])")
        .addOption(OptionType.CHANNEL, "channel", "アーカイブするチャンネル")
        .addOption(OptionType.INTEGER, "export_type", "出力タイプ(default = 0[csv])")

    override fun execute(event: SlashCommandEvent) {
        val channel = event.getOption("channel") ?: event.channel

    }
}