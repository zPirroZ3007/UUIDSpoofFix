/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.listener;

import me.pirro.uuidspoofix.chat.Messenger;
import me.pirro.uuidspoofix.lang.Language;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR) public void onPlayerJoin(PlayerJoinEvent event)
	{
		Messenger.sendMessage(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', Language.joinmessage.replaceAll("%uuid%", event.getPlayer().getUniqueId().toString())));
	}
}
