package me.MrEminent42.ezwarnings.managers;

import java.util.List;

import me.MrEminent42.ezwarnings.EzWarnings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarningManager {
	
	static EzWarnings main;
	
	public WarningManager(EzWarnings main) {
		WarningManager.main = main;
	}
	
	public static void Warn(OfflinePlayer p, Type t, String reason, CommandSender warner) {
		if (!EzWarnings.warnings.contains(p.getUniqueId().toString())) {
			EzWarnings.warnings.setValue(p.getUniqueId().toString() + ".warns", 1);
			EzWarnings.warnings.save();
		} else {
			int warns = EzWarnings.warnings.getInt(p.getUniqueId().toString() + ".warns");
			EzWarnings.warnings.setValue(p.getUniqueId().toString() + ".warns", warns + 1);
			EzWarnings.warnings.save();
		}
		
		executeWarningActions(p, warner, reason, EzWarnings.warnings.getInt(p.getUniqueId().toString() + ".warns"));
	}
	
	public static int getWarnings(OfflinePlayer p) {
		return EzWarnings.warnings.getInt(p.getUniqueId().toString() + ".warns");
	}
	
	public static int getTotalWarnings(OfflinePlayer p, Type t) {
		return EzWarnings.totals.getInt(p.getUniqueId() + "." + t.toString().toLowerCase());
	}
	
	public static List<String> getWarningTypes() {
		return EzWarnings.config.getStringList("warning-types");
	}
	
	public static String getWarningTypesForCommand() {
		
		String types = "<";
		
		List<String> list = EzWarnings.config.getStringList("warning-types");
		String[] array = list.toArray(new String[0]);
		
		for (int i = 1; i < array.length - 1; i++) {
			types += array[i] + "/";
		}
		
		types += array[array.length - 1] + ">";
		
		return types;
	}
	
	public static void setWarnings(OfflinePlayer p, int amnt) {
		EzWarnings.warnings.setValue(p.getUniqueId() + ".warns", amnt);
		EzWarnings.warnings.save();
	}
	
	public static void executeWarningActions(OfflinePlayer victim, CommandSender warner, String reason, int amount) {
		for (String s : EzWarnings.config.getKeys("warnings")) {
			if (EzWarnings.config.getKeys(String.valueOf(amount)) != null) {
				for (String a : EzWarnings.config.getStringList(s + ".actions")) {
					String[] action = a.split(" ");
					if (action[0].equalsIgnoreCase("[consolecmd]")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), a.replaceFirst("[consolecmd] ", ""));
					} else if (action[0].equalsIgnoreCase("[warnbroadcast]")) {
						MessageManager.sendWarnBroadcast(victim, reason, amount, Bukkit.getPlayer(warner.getName()));
					} else if (action[0].equalsIgnoreCase("[msg]")) {
						a.replaceAll("%player%", victim.getName());
						Player p = Bukkit.getPlayer(action[1]);
						StringBuilder str = new StringBuilder();
						for (int i = 2; i < action.length; i++) str.append(action[i]);
						if (p != null) p.sendMessage(ChatColor.translateAlternateColorCodes('&', str.toString()));
					} else if (action[0].equalsIgnoreCase("[punish]")) {
						punish(Bukkit.getPlayer(action[1]), Type.CONFIGPUNISHMENT, reason);
					} else {
						continue;
					}
				}
			} else {
				for (String a : EzWarnings.config.getStringList("others" + ".actions")) {
					String[] action = a.split(" ");
					if (action[0].equalsIgnoreCase("[warnmsg]")) {
						for (Player pm : Bukkit.getOnlinePlayers()) EzWarnings.messages.sendTranslation(pm, "warn.warn-broadcast", true);
					} else if (action[0].equalsIgnoreCase("[consolecmd]")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), a.replaceFirst("[consolecmd] ", ""));
					} else if (action[0].equalsIgnoreCase("[warnbroadcast]")) {
						MessageManager.sendWarnBroadcast(victim, reason, amount, Bukkit.getPlayer(warner.getName()));
					} else if (action[0].equalsIgnoreCase("[msg]")) {
						a.replaceAll("%player%", victim.getName());
						Player p = Bukkit.getPlayer(action[1]);
						StringBuilder str = new StringBuilder();
						for (int i = 2; i < action.length; i++) str.append(action[i]);
						if (p != null) p.sendMessage(ChatColor.translateAlternateColorCodes('&', str.toString()));
					} else {
						continue;
					}
				}
			}
		}
	}
	
	public static void punish(OfflinePlayer p, Type t, String reason) {
		
		if (EzWarnings.warnings.getInt(p.getUniqueId().toString() + ".punishments") >= EzWarnings.config.getInt("already-punished.total-punishments") - 1) {
			
			WarningManager.setPunishments(p, 0);
			
			for (String c : EzWarnings.config.getStringList("already-punished.final-punishment")) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("{player}", p.getName()).replace("{reason}", reason));
			}
			return;
		}
		
		
		for (String c : EzWarnings.config.getStringList("warn.punishment")) {
			c = c.replace("{player}", p.getName().replace("{reason}", reason));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
		}
		
		
		if (!EzWarnings.warnings.contains(p.getUniqueId().toString())) {
			EzWarnings.warnings.setValue(p.getUniqueId().toString() + ".punishments", 1);
			EzWarnings.warnings.save();
		} else {
			int punishments = EzWarnings.warnings.getInt(p.getUniqueId().toString() + ".punishments");
			EzWarnings.warnings.setValue(p.getUniqueId().toString() + ".punishments", punishments + 1);
			EzWarnings.warnings.save();
		}
	}
	
	public static int getPunishments(OfflinePlayer p) {
		return EzWarnings.warnings.getInt(p.getUniqueId().toString() + ".punishments");
	}
	
	public static int getTotalPunishments(OfflinePlayer p, Type t) {
		return EzWarnings.totals.getInt(p.getUniqueId() + "." + t.toString().toLowerCase());
	}
	
	public static void setPunishments(OfflinePlayer p, int amnt) {
		EzWarnings.warnings.setValue(p.getUniqueId() + ".punishments", amnt);
		EzWarnings.warnings.save();
	}
	
}
