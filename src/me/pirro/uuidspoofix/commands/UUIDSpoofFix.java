package me.pirro.uuidspoofix.commands;

import me.pirro.uuidspoofix.chat.Messenger;
import me.pirro.uuidspoofix.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UUIDSpoofFix implements CommandExecutor
{
	@Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("uuidspoofix"))
		{
			if (sender instanceof Player)
			{
				Player player = (Player) sender;
				if (args.length != 1)
				{
					Messenger.sendMessage(player, "§2UUIDSpoof - Fix §adeveloped by §f§o§nzPirroZ3007.\n§9Download it on: §7https://www.spigotmc.org/resources/uuidspoof-fix.26948/");
				}
				else if (args[0].equalsIgnoreCase("reload"))
				{
					if (player.isOp())
					{
						ConfigManager.reload();
						Messenger.sendMessage(player, "The config has been reloaded.");
					}
					else
						Messenger.sendError(player, "You don't have the permission to execute this command!");
				}
				else
				{
					Messenger.sendMessage(player, "§2UUIDSpoof - Fix §adeveloped by §f§o§nzPirroZ3007.\n§9Download it on: §7https://www.spigotmc.org/resources/uuidspoof-fix.26948/");
				}
				return true;
			}
		}
		return false;
	}
}
