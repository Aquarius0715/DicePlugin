package com.aquarius0715.diceplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.lang.NumberFormatException


class Dice(val plugin: DicePlugin) {

    fun normalSingleDice(player: Player) {
        player.sendMessage("${plugin.prefix}${ChatColor.YELLOW}${ChatColor.BOLD}6面ダイスを降って${(0..6).random()}が出ました。")
    }

    fun chooseNumberSingleDice(player: Player, args: String) {
        try {
            args.toInt()
        } catch (e: NumberFormatException) {
            player.sendMessage("${plugin.prefix}数字を入力してください。")
            return
        }
        player.sendMessage("${plugin.prefix}${ChatColor.YELLOW}${ChatColor.BOLD}${args}面ダイスを振って${(0..args.toInt()).random()}が出ました。")
    }

    fun oneHundredD(player: Player) {
        if (plugin.dStats) {
            player.sendMessage("${plugin.prefix}100Dはすでに始まっています。")
            return
        }
        Bukkit.broadcastMessage("${plugin.prefix}100Dが開始されました！")
        plugin.dStats = true
        var time = plugin.config.getInt("time")

        object: BukkitRunnable() {
            override fun run() {
                if (time % 60 == 0 && time != 0) {
                    Bukkit.broadcastMessage("${plugin.prefix}残り${time / 60}分です。")
                }
                if (time == 30) {
                    Bukkit.broadcastMessage("${plugin.prefix}残り${time}秒です。")
                }
                if (time in 0..10) {
                    Bukkit.broadcastMessage("${plugin.prefix}残り${time}秒です。")
                }
                if (time == -1) {
                    Bukkit.broadcastMessage("${plugin.prefix}受付が終了しました。")
                    Bukkit.broadcastMessage("${plugin.prefix}${ChatColor.WHITE}${ChatColor.BOLD}ダイスを振っています...${ChatColor.MAGIC}aaa")
                }
                if (time == -4) {
                    oneHundredDLogic()
                    cancel()
                }
                time--
            }
        }.runTaskTimer(plugin, 0, 20)
    }

    fun oneHundredDLogic() {
        val rnd = (0..100).random()
        var count = 0

        Bukkit.broadcastMessage("${plugin.prefix}100面ダイスを振って${rnd}が出ました。")

        for (player in Bukkit.getOnlinePlayers()) {
            if (plugin.dMap.getValue(player.uniqueId) == rnd) {
                Bukkit.broadcastMessage("${plugin.prefix}${player.displayName}さんがピッタリです！！！")
                count++
            }

            if (plugin.dMap.getValue(player.uniqueId) == rnd + 1 || plugin.dMap.getValue(player.uniqueId) == rnd - 1) {
                Bukkit.broadcastMessage("${plugin.prefix}${player.displayName}さんがニアミスです！！！")
                count++
            }
        }

        if (count == 0) {
            Bukkit.broadcastMessage("${plugin.prefix}当選者が誰もいませんでした...")
        }

        plugin.dMap.clear()
        plugin.dStats = false
        Bukkit.broadcastMessage("${plugin.prefix}100Dが終了しました。")
    }
}