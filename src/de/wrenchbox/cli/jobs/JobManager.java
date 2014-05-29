package de.wrenchbox.cli.jobs;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

public class JobManager {
	
	private LinkedBlockingQueue<Job> jobs;
	
	public JobManager() {
		jobs = new LinkedBlockingQueue<Job>();
	}

	/**
	 * Queues a command for execution.
	 * 
	 * @param command Command to execute.
	 */
	public void createJob(String command) {
		jobs.add(new Job(command));
	}

	/**
	 * Queues a command for execution in a specific working directory.
	 * 
	 * @param command Command to execute.
	 * @param dir Working directory for this command.
	 */
	public void createJob(String command, File dir) {
		jobs.add(new Job(command, dir));
	}

	/**
	 * Queues a command for execution with a callback handler.
	 * 
	 * @param command Command to execute.
	 * @param callback CallbackHandler to handle exit code, stdout and stderror.
	 */
	public void createJob(String command, CallbackHandler callback) {
		jobs.add(new Job(command, callback));
	}

	/**
	 * Queues a command for execution with a callback handler in a specific
	 * working directory.
	 * 
	 * @param command Command to execute.
	 * @param dir Working directory for this command.
	 * @param callback CallbackHandler to handle exit code, stdout and stderror.
	 */
	public void createJob(String command, File dir, CallbackHandler callback) {
		jobs.add(new Job(command, dir, callback));
	}

	protected Job getNextJob() throws InterruptedException {
		return jobs.take();
	}

}
