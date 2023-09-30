package me.NoChance.PvEManager;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.NoChance.PvEManager.Config.Variables;
import me.NoChance.PvEManager.Listeners.CommandListener;
import me.NoChance.PvEManager.Listeners.DamageListener;
import me.NoChance.PvEManager.Listeners.PlayerListener;
import me.chancesd.sdutils.updater.BukkitUpdater;
import me.chancesd.sdutils.updater.Updater;
import me.chancesd.sdutils.updater.Updater.UpdateResult;
import me.chancesd.sdutils.updater.Updater.UpdateType;

public final class PvEManager extends JavaPlugin {

	public HashSet<String> inCombat = new HashSet<>();
	public HashMap<String, BukkitTask> tasks = new HashMap<>();
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
			final Updater updater = new BukkitUpdater(this, 66406, UpdateType.VERSION_CHECK);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
				update = true;
				newVersion = updater.getLatestName();
				getLogger().info("Update Available: " + newVersion);
				getLogger().info("Link: http://dev.bukkit.org/bukkit-plugins/pvemanager/");
			} else {
				getLogger().info("No update found");
			}
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
		new Messages(this);
		variables = new Variables(this);
	}
}
