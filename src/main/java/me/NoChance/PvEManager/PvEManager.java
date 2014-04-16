package me.NoChance.PvEManager;

import java.util.HashMap;
import java.util.HashSet;

import me.NoChance.PvEManager.Config.*;
import me.NoChance.PvEManager.Listeners.*;
import me.NoChance.PvEManager.Updater.UpdateResult;
import org.bukkit.plugin.java.JavaPlugin;

public final class PvEManager extends JavaPlugin {

	public HashSet<String> inCombat = new HashSet<String>();
	public HashMap<String, Integer> tasks = new HashMap<String, Integer>();
	public Variables variables;
	public boolean update;
	public String newVersion;

	@Override
	public void onEnable() {
		loadFiles();
		if (Variables.stopCommands && Variables.inCombatEnabled) {
			new CommandListener(this);
		}
		new DamageListener(this);
		new PlayerListener(this);
		new CustomGraph(this);
		if (Variables.updateCheck) {
			getLogger().info("Checking for updates...");
			Updater updater = new Updater(this, 66406, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
				update = true;
				newVersion = updater.getLatestName();
				getLogger().info("Update Available: " + newVersion);
				getLogger().info("Link: http://dev.bukkit.org/bukkit-plugins/pvemanager/");
			} else
				getLogger().info("No update found");
		}
	}


	public void loadFiles() {
		if (getConfig().getInt("Config Version") == 0 || getConfig().getInt("Config Version") < 2) {
			getConfig().options().copyDefaults(true);
			getConfig().set("Config Version", 2);
			this.saveConfig();
		}
		this.saveDefaultConfig();
		this.reloadConfig();
		variables = new Variables(this);
	}
}