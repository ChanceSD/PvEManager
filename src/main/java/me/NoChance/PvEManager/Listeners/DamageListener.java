package me.NoChance.PvEManager.Listeners;

import me.NoChance.PvEManager.PvEManager;
import me.NoChance.PvEManager.Config.Variables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
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
		Player player = null;
		Entity mob = null;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Monster) {
			player = (Player) event.getDamager();
			mob = event.getEntity();
		}
		if (event.getDamager() instanceof Monster && event.getEntity() instanceof Player) {
			mob = event.getDamager();
			player = (Player) event.getEntity();
		}
		if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Monster) {
			Projectile proj = (Projectile) event.getDamager();
			if (proj.getShooter() instanceof Player) {
				player = (Player) proj.getShooter();
				mob = event.getEntity();
			}
		}
		if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
			Projectile proj = (Projectile) event.getDamager();
			if (proj.getShooter() instanceof Monster) {
				mob = (Entity) proj.getShooter();
				player = (Player) event.getEntity();
			}
		}

		if (player != null && mob != null) {
			if (Variables.disableFly && player.isFlying()) {
				disableFly(player);
			}
			if (Variables.inCombatEnabled) {
				if (!plugin.inCombat.contains(player.getName()) && !player.hasPermission("pvemanager.nocombat")) {
					inCombat(player);
				}
			}
		}
	}

	public void inCombat(Player player) {
		String pl = player.getName();
		if(plugin.inCombat.contains(pl)){
			Bukkit.getScheduler().cancelTask(plugin.tasks.get(pl));
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
		plugin.tasks.put(player, plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable() {
			public void run() {
				if (plugin.getServer().getPlayerExact(player) != null)
					plugin.getServer().getPlayerExact(player).sendMessage("§6[§8PvEManager§6] §aYou are no longer in combat");

				plugin.inCombat.remove(player);
			}
		}, Variables.timeInCombat * 20));
	}

}