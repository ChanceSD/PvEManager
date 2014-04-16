package me.NoChance.PvEManager.Listeners;

import me.NoChance.PvEManager.PvEManager;
import me.NoChance.PvEManager.Config.Variables;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	private PvEManager plugin;

	public PlayerListener(PvEManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		if (Variables.punishmentsEnabled && Variables.killOnLogout) {
			if (plugin.inCombat.contains(event.getPlayer().getName())) {
				event.getPlayer().setHealth(0);
				if (Variables.broadcastLog)
					plugin.getServer().broadcastMessage(ChatColor.GRAY + event.getPlayer().getName() + " tried to PvE Log!");
			}
		}
		plugin.tasks.remove(event.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.isOp()) {
			if (plugin.update) {
				player.sendMessage("§6[§fPvEManager§6] " + "§2An update is available: §e" + plugin.newVersion);
				player.sendMessage("§6[§fPvEManager§6] " + "§2Your current version is: §ePvEManager v"
						+ plugin.getDescription().getVersion());
				player.sendMessage("§2Go to this page to download the latest version:");
				player.sendMessage("§2Link: §ehttp://dev.bukkit.org/bukkit-plugins/pvemanager/");
			}
		}
	}
}
