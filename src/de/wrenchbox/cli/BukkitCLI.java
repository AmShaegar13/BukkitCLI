package de.wrenchbox.cli;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.wrenchbox.cli.jobs.CallbackHandler;
import de.wrenchbox.cli.jobs.JobManager;
import de.wrenchbox.cli.jobs.Worker;

public class BukkitCLI extends JavaPlugin {
	
	private Thread worker;
	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		JobManager manager = new JobManager();
		worker = new Thread(new Worker(manager));
		worker.setName("Worker");
		worker.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				getLogger().warning(String.format("Uncaught exception in %s: %s", t.getName(), e.getMessage()));
				e.printStackTrace();
			}
		});
		worker.start();
		
		manager.createJob("touch last_run");
		manager.createJob("date", new CallbackHandler() {
			
			@Override
			public void execute(int exitCode, String out, String err) {
				if (exitCode == 0) {
					getLogger().info(String.format("System time: %s", out));
				} else {
					getLogger().warning(err);
				}
			}
		});
	}

	@Override
	public void onDisable() {
		worker.interrupt();
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

}
