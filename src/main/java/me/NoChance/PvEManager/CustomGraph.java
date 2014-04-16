package me.NoChance.PvEManager;

import java.io.IOException;
import org.mcstats.Metrics;

public class CustomGraph {

	private PvEManager plugin;

	public CustomGraph(PvEManager plugin) {
		this.plugin = plugin;
		initMetrics();
	}

	public void initMetrics() {
		try {
			Metrics metrics = new Metrics(plugin);
			metrics.start();
		} catch (IOException e) {
		}
	}
}
