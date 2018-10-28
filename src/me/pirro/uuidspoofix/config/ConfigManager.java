/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.config;

import me.pirro.uuidspoofix.Main;
import me.pirro.uuidspoofix.lang.Language;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager
{
	private static FileConfiguration config = Main.getInstance().getConfig();
	private static File messages;
	private static File exempt;

	public static void setupConfigs()
	{
		Main.getInstance().getConfig().addDefault("debug", false);
		Main.getInstance().getConfig().addDefault("join-msg", true);
		Main.getInstance().getConfig().addDefault("fast-login", false);
		config.options().copyDefaults(true);
		Main.getInstance().saveConfig();

		messages = new File(Main.getInstance().getDataFolder(), "language.yml");

		if (!messages.exists())
		{
			try (InputStream in = Main.getInstance().getResource("language.yml"))
			{
				Files.copy(in, messages.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		exempt = new File(Main.getInstance().getDataFolder(), "exempt.yml");

		if (!exempt.exists())
		{
			try (InputStream in = Main.getInstance().getResource("exempt.yml"))
			{
				Files.copy(in, exempt.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static YamlConfiguration language()
	{
		return YamlConfiguration.loadConfiguration(messages);
	}

	public static YamlConfiguration exempt()
	{
		return YamlConfiguration.loadConfiguration(exempt);
	}

	public static void reload()
	{
		Language.loadMessages();
		Main.getInstance().reloadConfig();
	}
}
