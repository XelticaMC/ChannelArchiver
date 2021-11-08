package com.github.raink1208.channelarchiver.command

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.slf4j.LoggerFactory
import java.lang.Exception

class CommandHandler {
    val logger = LoggerFactory.getLogger(CommandHandler::class.java)
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

    fun unregisterCommands(commands: Set<CommandBase>) {
        for (command in commands) {
            unregisterCommand(command)
        }
    }

    fun unregisterCommand(command: CommandBase) {
        logger.info("command: ${command.commandData.name}を登録解除した")
        commandList.remove(command)
    }

    fun findCommand(command: String): CommandBase? {
        return commandList.find { it.commandData.name == command }
    }

    fun findAndExecute(command: SlashCommandEvent) {
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
}