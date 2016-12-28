/**
 * This plugin has been writted by @zPirroZ3007
 * You are not allowed to resell this plugin, or claim this code as your own.
 * You have been warned.
 * This is the Bungee version.
 * At today does not exist a client that can bypass BungeeCord, but if it will be bypassed. Here it is the Bungee version.
 */
package me.zpirroz.uuidfix;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class FixBungee extends Plugin implements Listener
{
	@Override
	public void onEnable()
	{
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
		String uuid = player.getUniqueId().toString();
		
		if(UUIDFetcher.get(player.getName().toString()).contains(uuid))
		{	
		}
		else
		{
			TextComponent msg = new TextComponent("§cError!\n§7§nSeems that your UUID is Spoofed! Maybe this is an error, please try to restart your client or change version!");
			player.disconnect(new BaseComponent[]{ msg });
		}
	}
}
