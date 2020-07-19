package com.aquarius0715.diceplugin

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import java.lang.NumberFormatException

class Events(private val plugin: DicePlugin): Listener {

    @EventHandler
    fun onSendChat(event: PlayerChatEvent) {
        if (!plugin.dStats) {
            return
        }
        try {
            event.message.toInt()
        } catch (e: NumberFormatException) {
            return
        }
        if (event.message.toInt() < 0 || event.message.toInt() > 100) {
            event.player.sendMessage("${plugin.prefix}0以上100以下の数字を入力してください。")
            return
        }
        if (plugin.dMap.containsValue(event.message.toInt())) {
            event.player.sendMessage("${plugin.prefix}その数字は言われています。")
            return
        }

        plugin.dMap[event.player.uniqueId] = event.message.toInt()
        event.player.sendMessage("${plugin.prefix}${event.message.toInt()}を登録しました。")
        event.isCancelled
    }
}