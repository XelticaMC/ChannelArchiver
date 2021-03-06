package com.github.raink1208.channelarchiver.command

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.slf4j.LoggerFactory
import java.lang.Exception

class CommandHandler {
    private val logger = LoggerFactory.getLogger(CommandHandler::class.java)
    private val commandList = HashSet<CommandBase>()

    fun registerCommands(commands: Set<CommandBase>) {
        for (command in commands) {
            registerCommand(command)
        }
    }

    fun registerCommand(command: CommandBase) {
        logger.info("command: ${command.commandData.name}を登録した")
        commandList.add(command)
    }

    fun findCommand(command: String): CommandBase? {
        return commandList.find { it.commandData.name == command }
    }

    fun findAndExecute(command: SlashCommandInteractionEvent) {
        val cmd = findCommand(command.name)

        if (cmd == null) {
            logger.error("コマンドが見つかりませんでした (${command.user.name}: ${command.name})")
            return
        }

        try {
            cmd.execute(command)
            logger.info("コマンドを実行した (${command.user.name}: ${command.name})")
        } catch (e: Exception) {
            logger.error("コマンドの実行時にエラーが起きました (${command.user.name}: ${command.name})")
            e.printStackTrace()
        }
    }

    fun findAndExecute(command: String, message: Message, args: String) {
        val cmd = findCommand(command)
        if (cmd == null) {
            logger.error("コマンドが見つかりませんでした (${message.author.name}: ${command})")
            return
        }

        try {
            cmd.execute(command, message, args)
            logger.info("コマンドを実行した (${message.author.name}: ${command})")
        } catch (e: Exception) {
            logger.error("コマンドの実行時にエラーが起きました (${message.author.name}: ${command})")
            e.printStackTrace()
        }
    }
}