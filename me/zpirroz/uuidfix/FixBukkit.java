/**
 * This plugin has been writted by @zPirroZ3007
 * You are not allowed to resell this plugin, or claim this code as your own.
 * You have been warned.
 */
package me.zpirroz.uuidfix;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class FixBukkit extends JavaPlugin implements Listener
{
	private static Plugin plugin;
	FileConfiguration config = getConfig();
	static Connection conn;
	Statement s;

	@Override
	public void onEnable()
	{
		plugin = this;
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		this.getConfig().addDefault("debug", false);
		this.getConfig().addDefault("join-msg", true);
		this.getConfig().addDefault("mysql.enabled", false);
		this.getConfig().addDefault("mysql.address", "localhost");
		this.getConfig().addDefault("mysql.port", "3306");
		this.getConfig().addDefault("mysql.username", "root");
		this.getConfig().addDefault("mysql.password", "passwd");
		this.getConfig().addDefault("mysql.dbname", "database");
		config.options().copyDefaults(true);
		this.saveConfig();

		String DB_NAME = config.getString("mysql.dbname");
		String DB_ADDRESS = config.getString("mysql.address");
		String DB_USERNAME = config.getString("mysql.username");
		String DB_PASSWORD = config.getString("mysql.password");
		String DB_PORT = config.getString("mysql.port");

		if (config.getBoolean("mysql.enabled") == true)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Bukkit.getConsoleSender().sendMessage("§6About to connect to database");

				conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + DB_ADDRESS + ":" + DB_PORT + "/" + DB_NAME + "?autoReconnect=true", DB_USERNAME, DB_PASSWORD);

				Bukkit.getConsoleSender().sendMessage("§aSuccessfully connected!");

				this.s = ((Statement) conn.createStatement());

				String createTable = "CREATE TABLE IF NOT EXISTS `uuidlog` (`name` varchar(255) NOT NULL,`uuid` varchar(255) NOT NULL,`date` varchar(255) NOT NULL) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
				this.s.executeUpdate(createTable);
			}
			catch (Exception ex)
			{
				Bukkit.getConsoleSender().sendMessage("§cCannot connect to the database! Please check your configuration! The plugin is going to be disabled!");
				Bukkit.getPluginManager().disablePlugin(plugin);
			}
		}
		
		Bukkit.getConsoleSender().sendMessage("§8[§7UUIDSpoof - Fix§8] §aHas been enabled!");
	}

	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage("§8[§7UUIDSpoof - Fix§8] §cHas been disabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("uuidfix"))
		{
			if (args.length == 0)
			{
				sender.sendMessage("§cUsage: /uuidfix [check, info]");
			}
			else if (args[0].equalsIgnoreCase("check"))
			{
				if (sender.hasPermission("uuidfix.check"))
				{
					if (config.getBoolean("mysql.enabled") == true)
					{
						if (args.length == 2)
						{
							if (args[1].contains("-"))
							{
								String sql = "SELECT * FROM `uuidlog` WHERE uuid='" + args[1] + "';";
								try
								{
									ResultSet rs = this.s.executeQuery(sql);

									if (rs.next())
									{
										sender.sendMessage("§aResults of your search:" + "\n§eUsername: " + rs.getString("name") + "\n§eUUID: " + rs.getString("uuid") + "\nDate: " + rs.getString("date"));
									}
									else
									{
										sender.sendMessage("§cNo results have been found for your search!");
									}
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
							else
							{
								String sql = "SELECT * FROM `uuidlog` WHERE name='" + args[1] + "';";
								try
								{
									ResultSet rs = this.s.executeQuery(sql);

									if (rs.next())
									{
										sender.sendMessage("§aResults of your search:" + "\n§eUsername: " + rs.getString("name") + "\n§eUUID: " + rs.getString("uuid") + "\nDate: " + rs.getString("date"));
									}
									else
									{
										sender.sendMessage("§cNo results have been found for your search!");
									}
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}
						else
						{
							sender.sendMessage("§cUsage: /uuidfix check [username/uuid]");
						}
					}
					else
					{
						sender.sendMessage("§cPlease enable MySQL on your config!");
					}
				}
				else
				{
					sender.sendMessage("§cYou don't have permission for this command!");
				}
			}
			else if (args[0].equalsIgnoreCase("info"))
			{
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
			}
			else
			{
				sender.sendMessage("§cUsage: /uuidfix [check, info]");
			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = (Player) event.getPlayer();
		String uuid = player.getUniqueId().toString();

		if (UUIDFetcher.get(player.getName().toString()).contains(uuid))
		{
			if (config.getBoolean("debug") == true)
			{
				Bukkit.getServer().broadcastMessage("§6" + player.getName().toString() + "§e's UUID is §6" + uuid);
			}

			if (config.getBoolean("join-msg") == true)
			{
				TextComponent a = new TextComponent("§7UUIDSpoof - Fix");
				a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Click here to go to\n§6the §7SpigotMC§6 Page of the plugin.").create()));
				a.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/uuidspoof-fix.26948/"));
				TextComponent b = new TextComponent(" §fby ");
				TextComponent c = new TextComponent("§b§ozPirroZ3007");
				c.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/members/zpirroz3007.44244/"));
				player.spigot().sendMessage(new BaseComponent[] { a, b, c });
				player.sendMessage("§fWith this plugin you can fix the UUID-Spoof exploit!");
			}
		}
		else
		{
			if (config.getBoolean("debug") == true)
			{
				Bukkit.getServer().broadcastMessage("§4" + player.getName().toString() + " §ctried to spoof his UUID!");
			}

			player.kickPlayer("§cError!\n§7§nSeems that your UUID is Spoofed! Maybe this is an error, please try to restart your client or change version!");

			if (config.getBoolean("mysql.enabled") == true)
			{
				String sql = "INSERT INTO `uuidlog`(`name`, `uuid`, `date`) VALUES ('" + player.getName().toString() + "','" + uuid + "','" + new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date()) + "')";
				try
				{
					this.s.executeUpdate(sql);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
			}
		}
	}
}
