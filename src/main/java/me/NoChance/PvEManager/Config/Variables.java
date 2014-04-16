package me.NoChance.PvEManager.Config;

import java.util.List;
import me.NoChance.PvEManager.PvEManager;

public class Variables {

	private PvEManager plugin;

	public static boolean inCombatEnabled;
	public static int timeInCombat;
	public static boolean stopCommands;
	public static boolean punishmentsEnabled;
	public static boolean killOnLogout;
	public static List<String> worldsExcluded;
	public static boolean updateCheck;
	public static boolean broadcastLog;
	public static boolean disableFly;

	public Variables(PvEManager plugin) {
		this.plugin = plugin;
		InitialiseVariables();
	}

	public void InitialiseVariables() {
		inCombatEnabled = getBoolean("In Combat.Enabled");
		timeInCombat = getInt("In Combat.Time(seconds)");
		stopCommands = getBoolean("In Combat.Stop Commands");
		punishmentsEnabled = getBoolean("In Combat.Punishments.Enabled");
		killOnLogout = getBoolean("In Combat.Punishments.Kill on Logout.Enabled");
		worldsExcluded = getStringList("World Exclusions");
		updateCheck = getBoolean("Update Check");
		broadcastLog = getBoolean("In Combat.Broadcast PvE Log");
		disableFly = getBoolean("Disable Fly");
	}

	public boolean getBoolean(String a) {
		return plugin.getConfig().getBoolean(a);
	}

	public int getInt(String a) {
		return plugin.getConfig().getInt(a);
	}
	
	public List<String> getStringList(String a){
		return plugin.getConfig().getStringList(a);
	}
	
}
