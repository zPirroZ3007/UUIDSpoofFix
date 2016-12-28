/**
 * This plugin has been writted by @zPirroZ3007
 * You are not allowed to resell this plugin, or claim this code as your own.
 * You have been warned.
 * This is the Bungee version.
 * At today does not exist a client that can bypass BungeeCord, but if it will be bypassed. Here it is the Bungee version.
 */
package me.zpirroz.uuidfix;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

import com.google.common.base.Charsets;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class FixBungee extends Plugin implements Listener
{
	@SuppressWarnings("unused")
	@Override
	public void onEnable()
	{
		if (!getDataFolder().exists())
		{
			getDataFolder().mkdir();
		}
		File configFile = new File(getDataFolder(), "config.yml");
		if (!getDataFolder().exists())
			getDataFolder().mkdir();

		File file = new File(getDataFolder(), "config.yml");

		if (!file.exists())
		{
			try (InputStream in = getResourceAsStream("config.yml"))
			{
				Files.copy(in, file.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		getProxy().getPluginManager().registerListener(this, this);
		getLogger().info("§8[§7UUIDSpoof - Fix§8] §aHas been enabled!");
	}

	public void onDisable()
	{
		getLogger().info("§8[§7UUIDSpoof - Fix§8] §cHas been disabled!");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PostLoginEvent event)
	{
		ProxiedPlayer player = (ProxiedPlayer) event.getPlayer();
		UUID uuidFetch = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(Charsets.UTF_8));

		try
		{
			Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
			if (configuration.getBoolean("online-mode") == false)
			{
				String uuid = player.getUniqueId().toString();
				if (uuidFetch.toString().contains(uuid))
				{
				}
				else
				{
					TextComponent msg = new TextComponent("§cError!\n§7§nSeems that your UUID is Spoofed! Maybe this is an error, please try to restart your client or change version!");
					player.disconnect(new BaseComponent[] { msg });
				}
				getLogger().info("§6" + player.getName().toString() + "§e's REAL UUID is §6" + uuidFetch.toString());
			}
			else
			{
				String uuid = player.getUniqueId().toString().replaceAll("-", "");
				if (UUIDFetcher.get(player.getName().toString()).contains(uuid))
				{
				}
				else
				{
					TextComponent msg = new TextComponent("§cError!\n§7§nSeems that your UUID is Spoofed! Maybe this is an error, please try to restart your client or change version!");
					player.disconnect(new BaseComponent[] { msg });
				}
				getLogger().info("§6" + player.getName().toString() + "§e's REAL UUID is §6" + UUIDFetcher.get(player.getName().toString()));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
