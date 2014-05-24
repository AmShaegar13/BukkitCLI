package de.wrenchbox.cli.jobs;

public abstract class CallbackHandler {
	
	public abstract void execute(int exitCode, String out, String err);
	
}
