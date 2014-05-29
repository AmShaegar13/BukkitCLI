package de.wrenchbox.cli;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.wrenchbox.cli.jobs.CallbackHandler;
import de.wrenchbox.cli.jobs.JobManager;
import de.wrenchbox.cli.jobs.Worker;

/**
 * API for executing commands through Bukkit. Use the createJob() methods to
 * get started.
 * 
 * @author AmShaegar
 *
 */
public class BukkitCLI extends JavaPlugin {
	
	private Thread worker;
	private static JobManager manager;
	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		manager = new JobManager();
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
	}

	@Override
	public void onDisable() {
		worker.interrupt();
	}
	
	/**
	 * Getter for the plugin instance.
	 * 
	 * @return An instance of the plugin.
	 */
	public static Plugin getPlugin() {
		return plugin;
	}

	/**
	 * Queues a command for execution.
	 * 
	 * @param command Command to execute.
	 */
	public static void createJob(String command) {
		manager.createJob(command);
	}

	/**
	 * Queues a command for execution in a specific working directory.
	 * 
	 * @param command Command to execute.
	 * @param dir Working directory for this command.
	 */
	public static void createJob(String command, File dir) {
		manager.createJob(command, dir);
	}

	/**
	 * Queues a command for execution with a callback handler.
	 * 
	 * @param command Command to execute.
	 * @param callback CallbackHandler to handle exit code, stdout and stderror.
	 */
	public static void createJob(String command, CallbackHandler callback) {
		manager.createJob(command, callback);
	}

	/**
	 * Queues a command for execution with a callback handler in a specific
	 * working directory.
	 * 
	 * @param command Command to execute.
	 * @param dir Working directory for this command.
	 * @param callback CallbackHandler to handle exit code, stdout and stderror.
	 */
	public static void createJob(String command, File dir, CallbackHandler callback) {
		manager.createJob(command, dir, callback);
	}

}
