package com._604robotics.robotnik;

/**
 * Class that has various settings for the framework
 *
 */
public class Settings {
	
	/* Class only has static config variables */
	
	private Settings () {};
	
	/** Value that controls debugging behavior
	 * 2 (default): Throw special-made exceptions on small problems
	 *     Default debug mode
	 * 1 : Passively continue past exceptions
	 *     May fatally error later
	 * 0 : Actively try to "swallow" errors (not implemented)
	 */
	public static final int DEBUG_THROW=2;

}
