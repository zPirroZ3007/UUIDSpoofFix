package me.zpirroz.uuidfix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
  implements Listener
{
	FileConfiguration config = getConfig();
	@Override
    public void onEnable()
    {
    	Bukkit.getServer().getPluginManager().registerEvents(this, this);
    	Bukkit.getConsoleSender().sendMessage("§3UUIDSpoofFix loaded!");
    	Bukkit.getConsoleSender().sendMessage("§6You are now protected from UUID Spoofing!");
    	Bukkit.getConsoleSender().sendMessage("§aThis plugin was developed by zPirroZ3007");
    	Bukkit.getConsoleSender().sendMessage("§aYou do not have the right to resell this plugin!");
    	Bukkit.getConsoleSender().sendMessage("§8This plugin is intended for §cOFFLINE §8UUIDs, but you can use it on your online-mode server!");
    	Bukkit.getConsoleSender().sendMessage("§9You can change it on Config!");
    	
    	this.getConfig().options().header("Enable this to broadcast the player's UUID at every login!");
    	this.getConfig().addDefault("debug", false);
    	config.options().copyDefaults(true);
    	this.saveConfig();
    	this.getConfig().options().header("Set the mode according with your server. (Example: online-mode=false is mode=offline");
    	this.getConfig().addDefault("online-mode", false);
        config.options().copyDefaults(true);
        this.saveConfig();
    }
    
    public void onDisable()
    {
    	getLogger().info(ChatColor.RED + "The plugin has been disabled, you are not protected from UUID Spoofing!");
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event)
    {
    	String UUID = event.getPlayer().getUniqueId().toString();
    	String username = event.getPlayer().getName().toString();
    	
    	if(config.getBoolean("debug"))
    	{
    		Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName().toString() + "'s UUID is " + ChatColor.RED + event.getPlayer().getUniqueId().toString());
    	}else{
    		
    	}
    	
        try
        {
        	if(config.getBoolean("online-mode"))
        	{
        	    URL uuidresult = new URL ("http://uuidfix.altervista.org/api.php?mode=online&name=" + username);
                BufferedReader in = new BufferedReader(new InputStreamReader(uuidresult.openStream()));
                String risultato = in.readLine();
                in.close();
                if(risultato.contains(UUID))
                {
                	event.getPlayer().sendMessage(ChatColor.BLUE + "You passed the UUID check with success! Your UUID is");
                	event.getPlayer().sendMessage(ChatColor.RED + event.getPlayer().getUniqueId().toString());
                	event.getPlayer().sendMessage("");
                	event.getPlayer().sendMessage(ChatColor.GOLD + "---------------------------------------");
                	event.getPlayer().sendMessage(ChatColor.GREEN + "This server is protected from UUID Spoofing by");
                	event.getPlayer().sendMessage(ChatColor.GOLD + "UUIDSpoofFix" + ChatColor.BLUE + " Developed by zPirroZ3007!");
                	event.getPlayer().sendMessage(ChatColor.GOLD + "---------------------------------------");
                }else{
                	event.getPlayer().kickPlayer(ChatColor.RED + "Seems that your UUID is spoofed, maybe an error, please restart your client or change version!");
                }
        	}else{
        	    URL uuidresult = new URL ("http://uuidfix.altervista.org/api.php?mode=offline&name=" + username);
                BufferedReader in = new BufferedReader(new InputStreamReader(uuidresult.openStream()));
                String risultato = in.readLine();
                in.close();
                if(risultato.contains(UUID))
                {
                	event.getPlayer().sendMessage(ChatColor.BLUE + "You passed the UUID check with success! Your UUID is");
                	event.getPlayer().sendMessage(ChatColor.RED + event.getPlayer().getUniqueId().toString());
                	event.getPlayer().sendMessage("");
                	event.getPlayer().sendMessage(ChatColor.GOLD + "---------------------------------------");
                	event.getPlayer().sendMessage(ChatColor.GREEN + "This server is protected from UUID Spoofing by");
                	event.getPlayer().sendMessage(ChatColor.GOLD + "UUIDSpoofFix" + ChatColor.BLUE + " Developed by zPirroZ3007!");
                	event.getPlayer().sendMessage(ChatColor.GOLD + "---------------------------------------");
                }else{
                	event.getPlayer().kickPlayer(ChatColor.RED + "Seems that your UUID is spoofed, maybe an error, please restart your client or change version!");
                }
        	}

        }
        catch (MalformedURLException localMalformedURLException) {}catch (IOException localIOException) 
        {
        	
        }
    }
}