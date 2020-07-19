package com.aquarius0715.diceplugin

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DiceCommands(val plugin: DicePlugin): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when(label) {
            "dice" -> {
                when(args.size) {
                    0 -> plugin.dice.normalSingleDice(sender as Player)
                    1 -> {
                        when(args[0]) {
                            "100d" -> plugin.dice.oneHundredD(sender as Player)
                            else -> plugin.dice.chooseNumberSingleDice(sender as Player, args[1])
                        }
                    }
                }
            }
        }

        return false
    }
}