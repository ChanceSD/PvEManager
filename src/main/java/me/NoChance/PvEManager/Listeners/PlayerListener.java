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
				player.sendMessage("" + "" + plugin.newVersion); //$NON-NLS-1$ //$NON-NLS-2$
				player.sendMessage("" + "" //$NON-NLS-1$ //$NON-NLS-2$
				        + plugin.getDescription().getVersion());
				player.sendMessage(""); //$NON-NLS-1$
				player.sendMessage(""); //$NON-NLS-1$
			}
		}
	}
}
