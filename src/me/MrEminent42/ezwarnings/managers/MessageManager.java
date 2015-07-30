package me.MrEminent42.ezwarnings.managers;

import me.MrEminent42.ezwarnings.EzWarnings;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Localization;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Variable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Messages;

public class MessageManager {
	
	private static Localization messages = EzWarnings.messages;
	
	public static void sendWarnBroadcast(OfflinePlayer victim, String reason, int amount, CommandSender warner) {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			messages.sendTranslation(p, "warn.warn-broadcast", true, new Variable[] { 
					new Variable("{player}", p.getName()), new Variable("{reason}", reason), new Variable("{warner}", warner.getName()) ,
					new Variable("{amount}", String.valueOf(WarningManager.getWarnings(victim) + 1) + "/" + EzWarnings.config.getInt("warn.total-warns"))
			});
					
			if (EzWarnings.config.getBoolean("log-to-console")) EzWarnings.messages.sendTranslation(Bukkit.getConsoleSender(), "warn.console-warn-message", true, new Variable[] { 
				new Variable("{player}", victim.getName()), new Variable("{reason}", reason), new Variable("{warner}", warner.getName()),
				new Variable("{amount}", String.valueOf(WarningManager.getWarnings(victim) + 1) + "/" + EzWarnings.config.getInt("warn.total-warns"))
			});
		}	
	}
}
