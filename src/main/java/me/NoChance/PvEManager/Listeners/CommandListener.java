package me.NoChance.PvEManager.Listeners;

import me.NoChance.PvEManager.PvEManager;
import me.NoChance.PvEManager.Config.Variables;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

	private PvEManager plugin;

	public CommandListener(PvEManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (Variables.stopCommands && Variables.inCombatEnabled) {
			if (plugin.inCombat.contains(event.getPlayer().getName())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(Messages.getString("Command_Denied")); //$NON-NLS-1$
			}
		}
	}

}
