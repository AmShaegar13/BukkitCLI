package de.wrenchbox.cli.jobs;

import de.wrenchbox.cli.BukkitCLI;

/**
 * The worker takes {@link Job}s from the {@link JobManager} and executes them one after
 * another. (FIFO)
 * 
 * @author AmShaegar
 *
 */
public class Worker implements Runnable {
	
	private JobManager manager;

	public Worker(JobManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void run() {
		Job job = null;
		try {
			while ((job = manager.getNextJob()) != null) {
				job.run();
			}
		} catch (InterruptedException e) {
			BukkitCLI.getPlugin().getLogger().info("Worker stopped. Thread interrupted.");
		}
	}
	
}
