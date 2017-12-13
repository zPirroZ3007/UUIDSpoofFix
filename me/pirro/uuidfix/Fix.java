/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007                        *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidfix;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Developed by zPirroZ3007 on 11/12/2017.
 */
public class Fix extends JavaPlugin implements Listener, PluginMessageListener
{
	// Declare instance
	public static Fix instance;

	// Declare configuration file
	FileConfiguration config = getConfig();

	// Declare messages file path
	File messages = new File(getDataFolder(), "language.yml");

	// Load messages configuration
	YamlConfiguration messagesFile = YamlConfiguration.loadConfiguration(messages);

	// Declare exempt file path
	File exempt = new File(getDataFolder(), "exempt.yml");

	// Load exempt configuration
	YamlConfiguration exemptFile = YamlConfiguration.loadConfiguration(exempt);

	// Integer that gets the BungeeCord's mode
	static int isOnline = 2;

	// This method will be called on plugin enable
	public void onEnable()
	{
		// Bind instance
		instance = this;

		// Save config defaults
		this.getConfig().addDefault("debug", false);
		this.getConfig().addDefault("join-msg", true);
		config.options().copyDefaults(true);
		this.saveConfig();

		// Declare messages file path
		File messages = new File(getDataFolder(), "language.yml");

		// Check if messages file already exists
		if (!messages.exists())
		{
			try (InputStream in = getResource("language.yml"))
			{
				// Copy file to the path
				Files.copy(in, messages.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// Declare exempt file path
		File exempt = new File(getDataFolder(), "exempt.yml");

		// Check if exempt file already exists
		if (!exempt.exists())
		{
			try (InputStream in = getResource("exempt.yml"))
			{
				// Copy file to the path
				Files.copy(in, exempt.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// Register outgoing plugin channel named "UUIDSpoofFix"
		getServer().getMessenger().registerOutgoingPluginChannel(this, "UUIDSpoofFix");

		// Register incoming plugin channel named "UUIDSpoofFix"
		getServer().getMessenger().registerIncomingPluginChannel(this, "UUIDSpoofFix", this);

		// Register listener
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		// Enable message
		Bukkit.getConsoleSender().sendMessage("\n" + "§9*----------------------------------------------------------------*" + "\n§bChecking online mode..." + "\n§7The online-mode is: §f" + onlineMode() + "§7!" + "\n\n§a§oUUIDSpoof - Fix §7by §ozPirroZ3007§r§7 has been enabled!" + "\n§7The §bUUID exploit§r§7 is now §bfixed§r§7!" + "\n§9*----------------------------------------------------------------*");
	}

	// This method will handle all the plugin messages received by BungeeCord
	@Override public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		// Handle the data
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		try
		{
			// Convert data to string
			String data = in.readLine().replaceAll("UUIDSpoofFix", "");

			// Broadcast message of BungeeCord detected
			if (isOnline == 2)
			{
				Bukkit.getConsoleSender().sendMessage("§aBungeeCord has been §ndetected§r§a! The mode has been set to: §f" + data);
			}

			// Set the mode true or false according to BungeeCord
			if (data.equals("true"))
			{
				isOnline = 1;
			}
			else
			{
				isOnline = 0;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// This method gets and returns online mode
	private boolean onlineMode()
	{
		if (isOnline == 2)
		{
			return Bukkit.getServer().getOnlineMode();
		}
		else
		{
			if (isOnline == 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	// Main method of this plugin
	@EventHandler(priority = EventPriority.HIGHEST) public void onLogin(PlayerLoginEvent event)
	{
		// Getting the player who tries to login
		Player player = event.getPlayer();

		// Check if the player is in exempt list
		if (!exemptFile.getStringList("whitelist").contains(player.getName()))
		{
			// This string returns UUID of the Player
			String uuid = player.getUniqueId().toString();

			// Calling the Fetcher
			Fetcher fetcher = new Fetcher();

			// Check if the fetchet UUID equals to the inputted UUID, inserting the online-mode.
			if (!fetcher.fetchUUID(player.getName(), onlineMode()).equals(uuid))
			{
				// Kick player for spoofed UUID
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, messagesFile.getString("kick-message").replaceAll("&", "§"));

				// Check if the the debug is enabled in config
				if (debug())
				{
					// Declaring a new TextComponent with the player name
					TextComponent name = new TextComponent(player.getName());

					// Set the hover of the declared TextComponent with IP Address, Spoofed UUID etc.
					name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(messagesFile.getString("ip-address").replaceAll("&", "§") + event.getRealAddress().getHostName() + "\n" + messagesFile.getString("spoofed-uuid").replaceAll("&", "§") + uuid + "\n" + messagesFile.getString("real-uuid").replaceAll("&", "§") + fetcher.fetchUUID(player.getName(), onlineMode()) + "\n" + messagesFile.getString("date").replaceAll("&", "§") + currentDate()).create()));

					// Check if the "debug-message" string contains name variable
					if (messagesFile.getString("debug-message").contains("%name%"))
					{
						// Split the string on the name variable
						String[] nameSplit = messagesFile.getString("debug-message").split("%name%");

						// Broadcast a message to the server.
						Bukkit.getServer().spigot().broadcast(new BaseComponent[] { new TextComponent(nameSplit[0].replaceAll("&", "§")), name, new TextComponent(nameSplit[1].replaceAll("&", "§")) });
					}
					else
					{
						// Broadcast a message to the server.
						Bukkit.getServer().spigot().broadcast(new BaseComponent[] { new TextComponent(messagesFile.getString("debug-message").replaceAll("&", "§")) });
					}
				}
			}
		}
	}

	// This method will be called after the player has executed the Login
	@EventHandler(priority = EventPriority.HIGHEST) public void onJoin(PlayerJoinEvent event)
	{
		// Getting the player that is joined
		Player player = event.getPlayer();

		// Check if the join message is enabled in config
		if (joinMsg())
		{
			// Text Components etc.
			String successString = messagesFile.getString("uuidcheck-success").replaceAll("&", "§");
			TextComponent success = new TextComponent("\n\n" + successString.replaceAll("%uuid%", player.getUniqueId().toString()));
			TextComponent a = new TextComponent("§7UUIDSpoof - Fix");
			a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Click here to go to\n§6the §7SpigotMC§6 Page of the plugin.").create()));
			a.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/uuidspoof-fix.26948/"));
			TextComponent b = new TextComponent(" §fby ");
			TextComponent c = new TextComponent("§b§ozPirroZ3007");
			c.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zPirroZ3007/"));

			// Send the join-message to the player
			player.spigot().sendMessage(new BaseComponent[] { a, b, c, success });
		}
	}

	// Handle commands method
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		// Check if the inputted command equals to "uuidfix"
		if (cmd.getName().equalsIgnoreCase("uuidfix"))
		{
			// Check if the sender of the command is a Player, and send him a message, else send a message to the console.
			if (sender instanceof Player)
			{
				TextComponent a = new TextComponent("§7UUIDSpoof - Fix");
				a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Click here to go to\n§6the §7SpigotMC§6 Page of the plugin.").create()));
				a.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/uuidspoof-fix.26948/"));
				TextComponent b = new TextComponent(" §fby ");
				TextComponent c = new TextComponent("§b§ozPirroZ3007");
				c.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/members/zpirroz3007.44244/"));
				((Player) sender).spigot().sendMessage(new BaseComponent[] { a, b, c });
				sender.sendMessage("§fWith this plugin you can fix the UUID-Spoof exploit!");
			}
			else
			{
				Bukkit.getConsoleSender().sendMessage("\n§7UUIDSpoof - Fix §fby §b§ozPirroZ3007\n§fWith this plugin you can fix the UUID-Spoof exploit!");
			}

			return true;
		}

		return false;
	}

	// Returns the debug boolean in the config
	private boolean debug()
	{
		return config.getBoolean("debug");
	}

	// Returns the join-msg boolean in the config
	private boolean joinMsg()
	{
		return config.getBoolean("join-msg");
	}

	// Returns the current date
	private String currentDate()
	{
		// Create a new date format
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		// Instance a new Date class
		Date date = new Date();

		return dateFormat.format(date);
	}
}