/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.scheduler;

import me.pirro.uuidspoofix.Main;
import me.pirro.uuidspoofix.chat.Messenger;
import me.pirro.uuidspoofix.config.ConfigManager;
import me.pirro.uuidspoofix.lang.Language;
import me.pirro.uuidspoofix.util.Fetcher;
import me.pirro.uuidspoofix.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Check
{
	public static void startTask()
	{
		Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable()
		{
			@Override public void run()
			{
				if (Main.getInstance().getConfig().getBoolean("fast-login"))
				{
					for (Player p : Bukkit.getOnlinePlayers())
					{
						if (ConfigManager.exempt().getList("whitelist").contains(p.getName()))
							continue;
						if (!Fetcher.fetchOnline(p).equals(p.getUniqueId().toString().replaceAll("-", "")) || !Fetcher.fetchOffline(p).equals(p.getUniqueId()))
						{
							p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Language.kickmessage));

							if (Main.getInstance().getConfig().getBoolean("debug"))
								for (Player p2 : Bukkit.getOnlinePlayers())
									Messenger.sendMessage(p2, ChatColor.translateAlternateColorCodes('&', Language.debugmessage.replaceAll("%name%", p.getName()).replaceAll("%ip%", p.getAddress().getHostName()).replaceAll("%uuid%", Fetcher.fetchOnline(p)).replaceAll("%fakeuuid%", p.getUniqueId().toString().replaceAll("-", ""))));
						}
					}
					return;
				}

				if (!Util.getMode())
				{
					for (Player p : Bukkit.getOnlinePlayers())
					{
						if (ConfigManager.exempt().getList("whitelist").contains(p.getName()))
							continue;
						if (!Fetcher.fetchOffline(p).equals(p.getUniqueId()))
						{
							p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Language.kickmessage));

							if (Main.getInstance().getConfig().getBoolean("debug"))
								for (Player p2 : Bukkit.getOnlinePlayers())
									Messenger.sendMessage(p2, ChatColor.translateAlternateColorCodes('&', Language.debugmessage.replaceAll("%name%", p.getName()).replaceAll("%ip%", p.getAddress().getHostName()).replaceAll("%uuid%", Fetcher.fetchOffline(p).toString()).replaceAll("%fakeuuid%", p.getUniqueId().toString().replaceAll("-", ""))));
						}
					}
				}
				else
				{
					for (Player p : Bukkit.getOnlinePlayers())
					{
						if (ConfigManager.exempt().getList("whitelist").contains(p.getName()))
							continue;
						if (!Fetcher.fetchOnline(p).equals(p.getUniqueId().toString().replaceAll("-", "")))
						{
							p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Language.kickmessage));

							if (Main.getInstance().getConfig().getBoolean("debug"))
								for (Player p2 : Bukkit.getOnlinePlayers())
									Messenger.sendMessage(p2, ChatColor.translateAlternateColorCodes('&', Language.debugmessage.replaceAll("%name%", p.getName()).replaceAll("%ip%", p.getAddress().getHostName()).replaceAll("%uuid%", Fetcher.fetchOnline(p).replaceAll("%fakeuuid%", p.getUniqueId().toString().replaceAll("-", "")))));
						}
					}
				}
			}
		}, 5L, 5L);
	}
}
