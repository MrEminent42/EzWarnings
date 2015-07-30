package me.MrEminent42.ezwarnings;

import org.bukkit.plugin.java.JavaPlugin;

import me.MrEminent42.ezwarnings.commands.ResetPunishments;
import me.MrEminent42.ezwarnings.commands.ResetWarnings;
import me.MrEminent42.ezwarnings.commands.Warn;
import me.MrEminent42.ezwarnings.commands.Warnings;
import me.MrEminent42.ezwarnings.managers.WarningManager;
import me.mrCookieSlime.CSCoreLibPlugin.PluginUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Localization;
import CSCoreLibSetup.CSCoreLibLoader;

public class EzWarnings extends JavaPlugin {

	public static Localization messages;
	public static Config config;
	public static Config warnings;
	public static Config totals;
	
	public void onEnable() {
		
		CSCoreLibLoader loader = new CSCoreLibLoader(this);
		
		if (loader.load()) {
			
			PluginUtils utils = new PluginUtils(this);
			utils.setupConfig();
			EzWarnings.config = utils.getConfig();
			utils.setupLocalization();
			EzWarnings.messages = utils.getLocalization();
			utils.setupUpdater(86931, getFile());
			utils.setupMetrics();
			
			EzWarnings.warnings = new Config(getDataFolder() + "/warnings.yml");
			EzWarnings.totals = new Config(getDataFolder() + "/totals.yml");
		}
		
		getCommand("warn").setExecutor(new Warn());
		getCommand("setwarnings").setExecutor(new ResetWarnings());
		getCommand("warnings").setExecutor(new Warnings());
		getCommand("setpunishments").setExecutor(new ResetPunishments());
		
		new WarningManager(this);
		
		messages.setPrefix("&c&l[&e&lWarnings&c&l] ");
		
		messages.setDefault("warn.usage", "&cUsage: /warn <player> {reasons}");
		messages.setDefault("warn.many-args", new String[] { "&cToo many arguments!", "&cUsage: /warn <player> {reasons}" });
		messages.setDefault("warn.warn-broadcast", "&d{player} &c&lwas warned by {warner} for &b&l{reason} - {amount}");
		messages.setDefault("warn.console-warn-message", "&c{player} was warned by {warner} for {reason} - {amount}.");
		
		messages.setDefault("warnings.usage", "&cUsage: /warnings <player>");
		messages.setDefault("warnings.many-args", new String[] { "&cToo many arguments!", "&cUsage: /warnings [player]" });
		messages.setDefault("warnings.message-others", "&c{player} has {warnings} and {punishments} punishments.");
		messages.setDefault("warnings.message-self", "&cYou have {warnings} warnings and {punishments} punishments.");
		
		messages.setDefault("resetwarnings.usage", "&cUsage: /resetwarnings <player> <amount>");
		messages.setDefault("resetwarnings.message", "&aYou have reset {player}'s warnings.");
		
		messages.setDefault("resetwarnings.usage", "&cUsage: /resetpunishments <player> <amount>");
		messages.setDefault("resetwarnings.message", "&aYou have reset {player}'s punishments.");
		
		messages.setDefault("global.no-permission", "&cYou dont have permission for this command!");
		messages.save();
	}
}
