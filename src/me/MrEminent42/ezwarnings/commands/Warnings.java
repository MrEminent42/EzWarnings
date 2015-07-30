package me.MrEminent42.ezwarnings.commands;

import me.MrEminent42.ezwarnings.EzWarnings;
import me.MrEminent42.ezwarnings.managers.WarningManager;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Variable;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warnings implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// /warnings [player]
		if (cmd.getName().equalsIgnoreCase("warnings") || cmd.getName().equalsIgnoreCase("punishments")) {
			if (args.length == 1) {
				if (sender.hasPermission("warnings.view.others")) {
					
					/*
					if (Bukkit.getPlayer(args[0]) == null) {
						EzWarnings.messages.sendTranslation(sender, "global.offline-player", true);
						return true;
					}
					*/
					
					String w = String.valueOf(WarningManager.getWarnings(Bukkit.getOfflinePlayer(args[0])));
					String p = String.valueOf(WarningManager.getPunishments(Bukkit.getOfflinePlayer(args[0])));
					EzWarnings.messages.sendTranslation(sender, "warnings.message-others", true, new Variable[] { new Variable("{player}", Bukkit.getOfflinePlayer(args[0]).getName()), new Variable("{warnings}", w), new Variable("{punishments}", p) });
					return true;
				} else {
					EzWarnings.messages.sendTranslation(sender, "global.no-permission", true);
					return true;
				}
			} else if (args.length == 0) {
				if (!(sender instanceof Player)) {
					EzWarnings.messages.sendTranslation(sender, "warnings.usage", false);
					return true;
				}
				
				if (sender.hasPermission("warnings.view")) {
					String w = String.valueOf(WarningManager.getWarnings(Bukkit.getOfflinePlayer(sender.getName())));
					String p = String.valueOf(WarningManager.getPunishments(Bukkit.getOfflinePlayer(sender.getName())));
					EzWarnings.messages.sendTranslation(sender, "warnings.message-self", true, new Variable[] { new Variable("{warnings}", w), new Variable("{punishments}", p) });
					return true;
				}
			}
		}
		return true;
	}
}
