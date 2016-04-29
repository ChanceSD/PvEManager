package me.NoChance.PvEManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;

public class Messages {

	private static final String BUNDLE_NAME = "messages";
	private static ResourceBundle RESOURCE_BUNDLE;

	public Messages(final PvEManager p) {
		try {
			p.saveResource("messages.properties", false);
			final File file = new File(p.getDataFolder().getPath());
			final URL[] urls = { file.toURI().toURL() };
			final ClassLoader loader = new URLClassLoader(urls);
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault(), loader);
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static String getString(final String key) {
		try {
			return ChatColor.translateAlternateColorCodes('&', RESOURCE_BUNDLE.getString(key));
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
