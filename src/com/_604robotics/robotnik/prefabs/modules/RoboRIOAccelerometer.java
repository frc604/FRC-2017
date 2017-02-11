package com._604robotics.robotnik.prefabs.modules;

import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * Default module to access the RoboRIO's built-in accelerometer.
 */
public class RoboRIOAccelerometer extends Module {
	private final Accelerometer accel = new BuiltInAccelerometer(Accelerometer.Range.k4G); 
	
	/**
	 * Creates a new RoboRIO accelerometer using data names "xAccel", "yAccel", and "zAccel".
	 */
	public RoboRIOAccelerometer () {
		this("xAccel","yAccel","zAccel");
	}
	
	/**
	 * Creates a new RoboRIO accelerometer using the given data names.
	 * @param xName Name of X acceleration data.
	 * @param yName Name of Y acceleration data.
	 * @param zName Name of Z acceleration data.
	 */
	public RoboRIOAccelerometer (String xName, String yName, String zName) {
        this.set(new DataMap() {{
            add(xName, () -> accel.getX());
            add(yName, () -> accel.getY());
            add(zName, () -> accel.getZ());
        }});
	}
}
