/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.listener;

import me.pirro.uuidspoofix.Main;
import me.pirro.uuidspoofix.chat.Messenger;
import me.pirro.uuidspoofix.config.ConfigManager;
import me.pirro.uuidspoofix.lang.Language;
import me.pirro.uuidspoofix.scheduler.Check;
import me.pirro.uuidspoofix.util.Fetcher;
import me.pirro.uuidspoofix.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class Login implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR) public void onPlayerLogin(PlayerLoginEvent event)
	{
		if (Util.bungeeCheck())
		{
			if (Fetcher.fetchOffline(event.getPlayer()).equals(event.getPlayer().getUniqueId()))
			{
				Util.setMode(false);
				Util.bungeeChecked();
				Bukkit.getServer().getConsoleSender().sendMessage("§aThe mode has been set to: §ffalse§a!");
				return;
			}

			if (Fetcher.fetchOnline(event.getPlayer()).equals(event.getPlayer().getUniqueId().toString().replaceAll("-", "")))
			{
				Util.setMode(true);
				Util.bungeeChecked();
				Bukkit.getServer().getConsoleSender().sendMessage("§aThe mode has been set to: §ftrue§a!");
				return;
			}
		}

		if (ConfigManager.exempt().getList("whitelist").contains(event.getPlayer().getName()))
			return;

		if (Main.getInstance().getConfig().getBoolean("fast-login"))
		{
			if (!Fetcher.fetchOnline(event.getPlayer()).equals(event.getPlayer().getUniqueId().toString().replaceAll("-", "")) || !Fetcher.fetchOffline(event.getPlayer()).equals(event.getPlayer().getUniqueId()))
			{
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', Language.kickmessage));

				if (Main.getInstance().getConfig().getBoolean("debug"))
					for (Player p : Bukkit.getOnlinePlayers())
						Messenger.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Language.debugmessage.replaceAll("%name%", event.getPlayer().getName()).replaceAll("%ip%", event.getRealAddress().getHostName()).replaceAll("%uuid%", Fetcher.fetchOnline(event.getPlayer())).replaceAll("%fakeuuid%", event.getPlayer().getUniqueId().toString().replaceAll("-", ""))));
			}
			return;
		}

		if (!Util.getMode())
		{
			if (!Fetcher.fetchOffline(event.getPlayer()).equals(event.getPlayer().getUniqueId()))
			{
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', Language.kickmessage));

				if (Main.getInstance().getConfig().getBoolean("debug"))
					for (Player p : Bukkit.getOnlinePlayers())
						Messenger.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Language.debugmessage.replaceAll("%name%", event.getPlayer().getName()).replaceAll("%ip%", event.getRealAddress().getHostName()).replaceAll("%uuid%", Fetcher.fetchOffline(event.getPlayer()).toString()).replaceAll("%fakeuuid%", event.getPlayer().getUniqueId().toString().replaceAll("-", ""))));
			}
		}
		else
		{
			if (!Fetcher.fetchOnline(event.getPlayer()).equals(event.getPlayer().getUniqueId().toString().replaceAll("-", "")))
			{
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', Language.kickmessage));

				if (Main.getInstance().getConfig().getBoolean("debug"))
					for (Player p : Bukkit.getOnlinePlayers())
						Messenger.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Language.debugmessage.replaceAll("%name%", event.getPlayer().getName()).replaceAll("%ip%", event.getRealAddress().getHostName()).replaceAll("%uuid%", Fetcher.fetchOnline(event.getPlayer())).replaceAll("%fakeuuid%", event.getPlayer().getUniqueId().toString().replaceAll("-", ""))));
			}
		}
	}
}
