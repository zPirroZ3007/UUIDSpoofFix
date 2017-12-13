/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007                        *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidfix.connector;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Developed by zPirroZ3007 on 11/12/2017.
 */
public class BungeeConnector extends Plugin implements Listener
{
	// Declare instance
	public static BungeeConnector instance;

	// What the plugin does when it gets enabled
	public void onEnable()
	{
		// Cast the istance to this method
		instance = this;

		// Register the listener
		getProxy().getPluginManager().registerListener(this, this);

		// Register the "UUIDSpoofFix" channel
		getProxy().registerChannel("UUIDSpoofFix");

		// Create a new scheduled repeating task that will repeat every second
		getProxy().getScheduler().schedule(instance, new Runnable()
		{
			@Override public void run()
			{
				// Create a cycle for every online player
				for(ProxiedPlayer player : getProxy().getPlayers())
				{
					// Send the mode to bukkit
					sendToBukkit("UUIDSpoofFix", "" + getProxy().getConfig().isOnlineMode(), player.getServer().getInfo());
				}
			}
		}, 1, 1, TimeUnit.SECONDS);

		getLogger().info("§aConnector Enabled!");
	}

	// What the plugin does when it gets disabled
	public void onDisable()
	{
		getLogger().info("§cConnector Disabled!");
	}

	// This method will send a message to the specified server
	public void sendToBukkit(String channel, String message, ServerInfo server)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(stream);

		try
		{
			out.writeUTF(channel);
			out.writeUTF(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		server.sendData("UUIDSpoofFix", stream.toByteArray());
	}
}