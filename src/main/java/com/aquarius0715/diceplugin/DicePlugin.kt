package com.aquarius0715.diceplugin

import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class DicePlugin : JavaPlugin() {

    val prefix = "${ChatColor.BOLD}[${ChatColor.AQUA}${ChatColor.BOLD}Dice${ChatColor.WHITE}${ChatColor.BOLD}]"
    val dice = Dice(this)
    var dStats = false
    var dMap = mutableMapOf<UUID, Int>()

    override fun onEnable() {
        Objects.requireNonNull(getCommand("dice"))!!.setExecutor(DiceCommands(this))
        server.pluginManager.registerEvents(Events(this), this)
        saveDefaultConfig()
        saveConfig()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}