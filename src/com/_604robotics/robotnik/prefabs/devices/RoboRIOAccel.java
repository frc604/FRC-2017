package com._604robotics.robotnik.prefabs.devices;

import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * Default module to access RoboRIO built in accelerometer.
 */
public class RoboRIOAccel extends Module {
	Accelerometer accel = new BuiltInAccelerometer(Accelerometer.Range.k4G); 
	
	/**
	 * Constructor that uses default DataMap names.
	 * The default names are "xAccel", "yAccel", and "zAccel".
	 */
	public RoboRIOAccel() {
		this("xAccel","yAccel","zAccel");
	}
	
	/**
	 * Constructor that allows specification of custom DataMap names.
	 * @param xName Name of xAccel data.
	 * @param yName Name of yAccel data.
	 * @param zName Name of zAccel data.
	 */
	public RoboRIOAccel(String xName, String yName, String zName) {
		this.CreateAccelData(xName, yName, zName);
	}
	
	/**
	 * Sets up DataMap for RoboRIOAccel module.
	 * @param xName
	 * @param yName
	 * @param zName
	 */
	private void CreateAccelData(String xName, String yName, String zName) {
		this.set(new DataMap() {{
			add(xName, new Data() {
				public double run() {
					return accel.getX();
				}
			});
			add(yName, new Data() {
				public double run () {
					return accel.getY();
				}
			});
			add(zName, new Data() {
				public double run () {
					return accel.getZ();
				}
			});
		}});
	}

}
