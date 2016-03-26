package me.NoChance.PvEManager.Listeners;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;

import me.NoChance.PvEManager.PvEManager;

public class Messages {

	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$
	private static ResourceBundle RESOURCE_BUNDLE;

	public Messages(final PvEManager p) {
		try {
			final File file = new File(p.getDataFolder().getPath());
			final URL[] urls = { file.toURI().toURL() };
			final ClassLoader loader = new URLClassLoader(urls);
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault(), loader);
		} catch (final MalformedURLException e) {// TODO Auto-generated catch block
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
