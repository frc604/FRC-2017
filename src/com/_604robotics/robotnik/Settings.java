package com._604robotics.robotnik;

/**
 * Class that has various settings for the framework
 *
 */
public class Settings {
	
	/* Class only has static config variables */
	
	private Settings () {};
	
	/**
	 * Throw all errors for debugging.
	 */
	public static final int SET_DEBUG=2;
	
	/**
	 * Continue normally and possibly error out later. 
	 */
	public static final int SET_NORMAL=1;
	
	/**
	 * Actively try to suppress errors and continue on.
	 */
	public static final int SET_RELEASE=0;

	/** Value that controls debugging behavior
	 * 2 (default): Throw special-made exceptions on small problems
	 *     Default debug mode
	 * 1 : Passively continue past exceptions
	 *     May fatally error later
	 * 0 : Actively try to "swallow" errors (not implemented)
	 */
	public static final int DEBUG_THROW=SET_DEBUG;

}
