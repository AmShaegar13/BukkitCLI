package de.wrenchbox.cli.jobs;

import java.util.concurrent.LinkedBlockingQueue;

public class JobManager {
	
	private LinkedBlockingQueue<Job> jobs;
	
	public JobManager() {
		jobs = new LinkedBlockingQueue<Job>();
	}
	
	public void createJob(String command) {
		jobs.add(new Job(command));
	}
	
	public void createJob(String command, CallbackHandler callback) {
		jobs.add(new Job(command, callback));
	}

	public Job getNextJob() throws InterruptedException {
		return jobs.take();
	}

}
