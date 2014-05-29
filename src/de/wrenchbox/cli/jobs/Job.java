package de.wrenchbox.cli.jobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import de.wrenchbox.cli.BukkitCLI;

public class Job implements Runnable {

	private String command;
	private File dir;
	private CallbackHandler callback;
	private static Runtime runtime = Runtime.getRuntime();

	public Job(String command) {
		this.command = command;
	}
	
	public Job(String command, File dir) {
		this(command);
		this.dir = dir;
	}
	
	public Job(String command, CallbackHandler callback) {
		this(command);
		this.callback = callback;
	}
	
	public Job(String command, File dir, CallbackHandler callback) {
		this(command, dir);
		this.callback = callback;
	}

	public String getCommand() {
		return command;
	}
	
	@Override
	public void run() {
		Logger logger = BukkitCLI.getPlugin().getLogger();
		if (command == null) {
			logger.warning("Unable to execute command. Cannot be null.");
			return;
		}
		try {
			Process p;
			if(dir == null) {
				p = runtime.exec(command);
			} else {
				p = runtime.exec(command, null, dir);
			}
			int exitCode = p.waitFor();
			if (callback != null) {
				callback.onExecute(exitCode,readFromStream(p.getInputStream()), readFromStream(p.getErrorStream()));
			} else if (exitCode != 0) {
				logger.warning(String.format("Failed to execute '%s'. Exit code: %d", command, exitCode));
			}
			return;
		} catch (IOException e) {
			onError(e, String.format("Unable to execute '%s'. %s: %s", command, e.getClass().getSimpleName(), e.getMessage()));
		} catch (IllegalArgumentException e) {
			onError(e, "Unable to execute command. Cannot be empty.");
		} catch (InterruptedException e) {
			onError(e, "Interrupted while waiting for process to end.");
		}
	}

	private void onError(Throwable t, String message) {
		if (callback != null) {
			callback.onError(t, command, message);
		} else {
			BukkitCLI.getPlugin().getLogger().warning(message);
		}
	}

	private String readFromStream(InputStream stream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		boolean first = true;
		StringBuilder out = new StringBuilder();
		while ((line = in.readLine()) != null) {
			if (first) {
				first = false;
			} else {
				out.append(System.getProperty("line.separator"));
			}
			out.append(line);
		}
		return out.toString();
	}

}
