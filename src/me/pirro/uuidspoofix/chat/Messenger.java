/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.chat;

import me.pirro.uuidspoofix.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Messenger
{
	public static void sendMessage(Player player, String message)
	{
		player.sendMessage("§6[UUIDSpoofFix] §a" + message);
	}

	public static void sendError(Player player, String message)
	{
		player.sendMessage("§c[UUIDSpoofFix] " + message);
	}

	public static void enableMessage()
	{
		Bukkit.getConsoleSender().sendMessage("\n§9*----------------------------------------------------------------*" + "\n§bChecking online mode..." + "\n§7The online-mode is: §f" + Util.getMode() + "§7!" + "\n\n§a§oUUIDSpoof - Fix §7by §ozPirroZ3007§r§7 has been enabled!" + "\n§7The §bUUID exploit§r§7 is now §bfixed§r§7!" + "\n§9*----------------------------------------------------------------*");
	}

	public static void disableMessage()
	{
		Bukkit.getConsoleSender().sendMessage("\n§4*----------------------------------------------------------------*" + "\n§c§oUUIDSpoof - Fix §7by §ozPirroZ3007§r§7 has been disabled!" + "\n§4*----------------------------------------------------------------*\"");
	}
}