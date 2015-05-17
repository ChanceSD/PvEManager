package me.NoChance.PvEManager.Listeners;

import me.NoChance.PvEManager.PvEManager;
import me.NoChance.PvEManager.Config.Variables;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageListener implements Listener {

	private PvEManager plugin;

	public DamageListener(PvEManager plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void PlayerDamageListener(EntityDamageByEntityEvent event) {
		if (Variables.worldsExcluded.contains(event.getEntity().getWorld().getName()))
			return;
		Entity attacker = (Entity) (event.getDamager() instanceof Projectile ? ((Projectile) event.getDamager()).getShooter() : event.getDamager());
		Entity defender = event.getEntity();
		if (attacker instanceof Player && defender instanceof Player || !(attacker instanceof Player) && !(defender instanceof Player))
			return;
		Player player = (Player) (attacker instanceof Player ? attacker : defender);
		if (Variables.disableFly && player.isFlying()) {
			disableFly(player);
		}
		if (Variables.inCombatEnabled) {
			if (!plugin.inCombat.contains(player.getName()) && !player.hasPermission("pvemanager.nocombat")) {
				inCombat(player);
			}
		}
	}

	public void inCombat(Player player) {
		String pl = player.getName();
		if (plugin.inCombat.contains(pl)) {
			plugin.tasks.get(pl).cancel();
			Timer(pl);
			return;
		}
		player.sendMessage("§6[§8PvEManager§6] §cYou are In Combat!");
		plugin.inCombat.add(pl);
		Timer(pl);
	}

	public void disableFly(Player player) {
		player.setFlying(false);
		player.setAllowFlight(false);
	}

	public void Timer(final String player) {
		plugin.tasks.put(player, new BukkitRunnable() {
			public void run() {
				if (plugin.getServer().getPlayerExact(player) != null)
					plugin.getServer().getPlayerExact(player).sendMessage("§6[§8PvEManager§6] §aYou are no longer in combat");
				plugin.inCombat.remove(player);
			}
		}.runTaskLater(plugin, Variables.timeInCombat * 20));
	}

}