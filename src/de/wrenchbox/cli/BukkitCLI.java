package de.wrenchbox.cli;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitCLI extends JavaPlugin {
	
	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
	}

	@Override
	public void onDisable() {
		
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

}
