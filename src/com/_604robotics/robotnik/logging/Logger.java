package com._604robotics.robotnik.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logs information to the console.
 */
public class Logger {
	private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat LOG_FILE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	
	private static PrintStream LOG_FILE = null;
	
	static {
		try {
			File file = new File("/home/lvuser/robotnik_" + LOG_FILE_DATE_FORMAT.format(new Date()) + ".log");
			if(file.exists() || file.createNewFile()) {
				LOG_FILE = new PrintStream(new FileOutputStream(file));

				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						if (LOG_FILE != null) {
							warn("Closing log file now; further writes to the log file will fail.");
							
							LOG_FILE.flush();
							LOG_FILE.close();
							LOG_FILE = null;
						}
					}
				});
				
				Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException (Thread thread, Throwable t) {
						error("Uncaught exception in thread \"" + thread.getName() + "\".", t);
					}
				});
				
				log("Recording to log file \"" + file.getAbsolutePath() + "\" on roboRIO");
			} else {
				warn("Could not create log file.");
			}
		} catch (IOException e) {
			error("Could not open log file.", e);
		}
	}
	
	/**
	 * Logs a missing component.
	 * @param type Type of the component.
	 * @param name Name of the component.
	 */
	public static void missing (String type, String name) {
		warn("Missing " + type + " - " + name);
	}

	/**
	 * Logs information.
	 * @param message Message to log.
	 */
	public static void log (String message) {
		record(System.out, "[INFO] " + message);
	}

	/**
	 * Logs a warning.
	 * @param message Message to log.
	 */
	public static void warn (String message) {
		record(System.err, "[WARN] " + message);
	}

	/**
	 * Logs an error.
	 * @param message Message to log.
	 * @param t Throwable to log.
	 */
	public static void error (String message, Throwable t) {
		record(System.err, "[ERROR] " + message + ": (" + t.getClass().getName() + ") " + t.getMessage());
		trace(t);
	}

	private static void record (PrintStream std, String message) {
		final String line = "(" + LOG_DATE_FORMAT.format(new Date()) + ") " + message;
		
		std.println(line);
		if (LOG_FILE != null) {
			LOG_FILE.println(line);
		}
	}

	private static void trace (Throwable t) {
		t.printStackTrace();
		if (LOG_FILE != null) {
			LOG_FILE.println(t.toString());
		}
	}
}
