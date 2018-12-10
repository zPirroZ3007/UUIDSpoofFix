/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.util;

import de.domedd.betternick.api.betternickapi.BetterNickAPI;
import me.pirro.uuidspoofix.Main;
import me.pirro.uuidspoofix.config.ConfigManager;
import me.pirro.uuidspoofix.listener.Join;
import me.pirro.uuidspoofix.listener.Login;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;

public class Util
{
	private static boolean mode;
	public static boolean toCheckBungee;

	public static boolean usingBetterNick()
	{
		return (Bukkit.getPluginManager().getPlugin("BetterNick") != null);
	}

	public static BetterNickAPI getBetterNick()
	{
		return Main.api;
	}

	public static boolean getMode()
	{
		return mode;
	}

	public static void setupMode()
	{
		mode = Bukkit.getOnlineMode();
	}

	public static void setMode(boolean setmode)
	{
		mode = setmode;
	}

	public static void checkSpigot()
	{
		try
		{
			Class.forName("net.md_5.bungee.api.ChatColor");
		}
		catch (ClassNotFoundException e)
		{
			Main.getInstance().getLogger().warning("Error! This server is not runnig Spigot or PaperSpigot! Make sure to run that to make this plugin work.");
			Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
		}
	}

	public static void getBungeeCord()
	{
		try
		{
			File fileToRead = new File(Main.getInstance().getServer().getWorldContainer().getAbsolutePath() + "/spigot.yml");

			BufferedReader br = new BufferedReader(new FileReader(fileToRead));

			String line = null;

			while ((line = br.readLine()) != null)
			{
				if (line.contains("bungeecord: true") || line.contains("bungeecord:true"))
				{
					toCheckBungee = true;
					Bukkit.getConsoleSender().sendMessage("§aBungeeCord has been §ndetected!§a Waiting for a player to join to set online mode...");
				}
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static boolean bungeeCheck()
	{
		return toCheckBungee;
	}

	public static void bungeeChecked()
	{
		toCheckBungee = false;
	}

	public static void debugMessage(Player player, String uuid)
	{
		Bukkit.getServer().broadcastMessage(ConfigManager.language().getString("debug-message"));
	}

	public static void registerListeners()
	{
		Main.getInstance().getServer().getPluginManager().registerEvents(new Login(), Main.getInstance());
		Main.getInstance().getServer().getPluginManager().registerEvents(new Join(), Main.getInstance());
	}
}
