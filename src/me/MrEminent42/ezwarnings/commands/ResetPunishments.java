package me.MrEminent42.ezwarnings.commands;

import me.MrEminent42.ezwarnings.EzWarnings;
import me.MrEminent42.ezwarnings.managers.WarningManager;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Variable;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetPunishments implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setwarnings")) {
			
			int w = 0;
			
			if (args.length == 1) w = 0;
			
			if (args.length == 2) {
				if (sender.hasPermission("warnings.reset.punishments")) {
					
					/*
					if (Bukkit.getPlayer(args[0]) == null) {
						EzWarnings.messages.sendTranslation(sender, "global.offline-player", true);
						return true;
					}
					*/
					
					try {
						w = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						EzWarnings.messages.sendTranslation(sender, "resetpunishments.usage", true);
					}
					
					WarningManager.setPunishments(Bukkit.getOfflinePlayer(args[0]), w);
					EzWarnings.messages.sendTranslation(sender, "resetpunishments.message", true, new Variable("{player}", args[0]));
				} else {
					EzWarnings.messages.sendTranslation(sender, "global.no-permission", true);
					return true;
				}
				
			} else {
				EzWarnings.messages.sendTranslation(sender, "resetpunishments.usage", true);
				return true;
			}
		}
		return true;
	}

}
