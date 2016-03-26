package me.NoChance.PvEManager.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.NoChance.PvEManager.PvEManager;
import me.NoChance.PvEManager.Config.Variables;

public class PlayerListener implements Listener {

	private final PvEManager plugin;

	public PlayerListener(final PvEManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLogout(final PlayerQuitEvent event) {
		if (Variables.punishmentsEnabled && Variables.killOnLogout) {
			if (plugin.inCombat.contains(event.getPlayer().getName())) {
				event.getPlayer().setHealth(0);
				if (Variables.broadcastLog) {
					plugin.getServer().broadcastMessage(Messages.getString("PvE_Log_Broadcast").replace("%p", event.getPlayer().getName())); //$NON-NLS-1$
				}
			}
		}
		plugin.tasks.remove(event.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerLogin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (player.isOp()) {
			if (plugin.update) {
				player.sendMessage("§6[§fPvEManager§6] " + "§2An update is available: §e" + plugin.newVersion);
				player.sendMessage("§6[§fPvEManager§6] " + "§2Your current version is: §ePvEManager v" + plugin.getDescription().getVersion());
				player.sendMessage("§2Go to this page to download the latest version:");
				player.sendMessage("§2Link: §ehttp://dev.bukkit.org/bukkit-plugins/pvemanager/");
			}
		}
	}
}
