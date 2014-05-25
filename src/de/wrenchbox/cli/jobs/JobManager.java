package de.wrenchbox.cli.jobs;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

public class JobManager {
	
	private LinkedBlockingQueue<Job> jobs;
	
	public JobManager() {
		jobs = new LinkedBlockingQueue<Job>();
	}
	
	public void createJob(String command) {
		jobs.add(new Job(command));
	}
	
	public void createJob(String command, File dir) {
		jobs.add(new Job(command, dir));
	}
	
	public void createJob(String command, CallbackHandler callback) {
		jobs.add(new Job(command, callback));
	}
	
	public void createJob(String command, File dir, CallbackHandler callback) {
		jobs.add(new Job(command, dir, callback));
	}

	public Job getNextJob() throws InterruptedException {
		return jobs.take();
	}

}
