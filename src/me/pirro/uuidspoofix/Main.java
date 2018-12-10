/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix;

import de.domedd.betternick.BetterNick;
import de.domedd.betternick.api.betternickapi.BetterNickAPI;
import me.pirro.uuidspoofix.chat.Messenger;
import me.pirro.uuidspoofix.commands.UUIDSpoofFix;
import me.pirro.uuidspoofix.config.ConfigManager;
import me.pirro.uuidspoofix.lang.Language;
import me.pirro.uuidspoofix.scheduler.Check;
import me.pirro.uuidspoofix.util.Util;
import me.pirro.uuidspoofix.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	private static Main instance;
	public static BetterNickAPI api;

	@Override public void onEnable()
	{
		instance = this;
		Util.checkSpigot();
		Util.registerListeners();
		Util.setupMode();
		Util.getBungeeCord();
		ConfigManager.setupConfigs();
		Language.loadMessages();
		Check.startTask();
		getCommand("uuidspoofix").setExecutor(new UUIDSpoofFix());
		Metrics metrics = new Metrics(this);
		Messenger.enableMessage();
		if (Bukkit.getPluginManager().getPlugin("BetterNick") != null)
			api = BetterNick.getApi();

	}

	@Override public void onDisable()
	{
		Messenger.disableMessage();
	}

	public static Plugin getInstance()
	{
		return instance;
	}
}
