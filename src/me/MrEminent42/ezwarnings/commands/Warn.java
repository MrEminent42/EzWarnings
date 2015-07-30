package me.MrEminent42.ezwarnings.commands;

import me.MrEminent42.ezwarnings.EzWarnings;
import me.MrEminent42.ezwarnings.managers.Type;
import me.MrEminent42.ezwarnings.managers.WarningManager;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Variable;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warn implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// /warn <player> <reason>
		if (cmd.getName().equalsIgnoreCase("warn")) {
			if (sender.hasPermission("warnings.warn")) {
				
				if (args.length == 0) {
					EzWarnings.messages.sendTranslation(sender, "warn.usage", true, new Variable("{reasons}", WarningManager.getWarningTypesForCommand()));
					return true;
				}
				
				if (args.length == 1) {
					EzWarnings.messages.sendTranslation(sender, "warn.usage", true, new Variable[] { new Variable("<player>", args[0]), new Variable("{reasons}", WarningManager.getWarningTypesForCommand()) });
					return true;
				}
				
				if (args.length == 2) {
					for (String s : WarningManager.getWarningTypes()) {
						if (s.equalsIgnoreCase(args[1])) {
							WarningManager.Warn(Bukkit.getOfflinePlayer(args[0]), Type.COMMANDWARNING, args[1].toLowerCase(), sender);
						}
					}
				}
				
				if (args.length > 2) {
					EzWarnings.messages.sendTranslation(sender, "warn.many-args", true, new Variable[] { new Variable("<player>", args[0]), new Variable("{reasons}", WarningManager.getWarningTypesForCommand()) });
				}
				
			} else {
				EzWarnings.messages.sendTranslation(sender, "global.no-permission", true);
				return true;
			}
		}
		return true;
	}
}
