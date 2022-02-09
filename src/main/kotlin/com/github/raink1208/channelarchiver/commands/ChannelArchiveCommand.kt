package com.github.raink1208.channelarchiver.commands

import com.github.raink1208.channelarchiver.command.CommandBase
import com.github.raink1208.channelarchiver.exporting.ChannelExporter
import com.github.raink1208.channelarchiver.exporting.formats.TextFormat
import com.github.raink1208.channelarchiver.exporting.formats.ExportFormat
import com.github.raink1208.channelarchiver.exporting.formats.JSONFormat
import com.github.raink1208.channelarchiver.exporting.formats.MDFormat
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData

object ChannelArchiveCommand: CommandBase {
    override val commandData: CommandData = Commands.slash("archive", "チャンネルを外部ファイルに出力します (export type = [0 = txt, 1 = json])")
        .addOption(OptionType.CHANNEL, "channel", "アーカイブするチャンネル", false)
        .addOptions(OptionData(OptionType.INTEGER, "export_type", "出力タイプ(default = 0[txt])", false).addChoice("TXT", 0).addChoice("JSON", 1).addChoice("MD",2))

    override fun execute(event: SlashCommandInteractionEvent) {
        val channel = event.getOption("channel")?.asGuildChannel ?: event.textChannel
        val exportType = event.getOption("export_type")?.asLong?.toInt() ?: 0
        val format = getFormat(exportType)

        if (channel !is TextChannel) {
            event.reply("テキストチャンネルを指定してください").queue()
            return
        }

        if (format == null) {
            event.reply("フォーマットタイプが見つかりません").queue()
            return
        }

        event.reply("処理を開始しています...").queue()

        ChannelExporter(channel, format).export()
    }

    override fun execute(command: String, message: Message, args: String) {
        val channel = message.channel
        val exportType = args.toIntOrNull() ?: 0
        val format = getFormat(exportType)

        if (channel !is TextChannel) {
            message.channel.sendMessage("テキストチャンネルで実行してください").queue()
            return
        }

        if (format == null) {
            message.channel.sendMessage("フォーマットタイプが見つかりません").queue()
            return
        }

        message.channel.sendMessage("処理を開始しています...").queue()

        ChannelExporter(channel, format).export()
    }

    private fun getFormat(exportType: Int): ExportFormat? {
        return when (exportType) {
            ChannelExporter.TXT -> TextFormat()
            ChannelExporter.JSON -> JSONFormat()
            ChannelExporter.MD -> MDFormat()
            else -> null
        }
    }
}