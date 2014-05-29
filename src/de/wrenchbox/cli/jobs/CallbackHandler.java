package de.wrenchbox.cli.jobs;

import de.wrenchbox.cli.BukkitCLI;


/**
 * This abstract class needs to be extended and is used as callback after
 * running a command.
 * 
 * @author AmShaegar
 *
 */
public abstract class CallbackHandler {
	
	/**
	 * This method is invoked after the command was successfully executed.
	 * 
	 * @param exitCode The exit code returned by the executed command.
	 * @param out The standard output of the process executed. 
	 * @param err The error output of the process executed.
	 */
	public abstract void onExecute(int exitCode, String out, String err);
	
	/**
	 * This method may be overridden in order to handle errors from executing
	 * commands.
	 * 
	 * @param t The exception that occurred.
	 * @param command The command that caused the exception.
	 * @param message A descriptive error message.
	 */
	public void onError(Throwable t, String command, String message) {
		BukkitCLI.getPlugin().getLogger().warning(message);
	};
	
}
