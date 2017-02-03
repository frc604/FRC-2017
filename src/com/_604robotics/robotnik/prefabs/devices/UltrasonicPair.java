package com._604robotics.robotnik.prefabs.devices;
import com._604robotics.robotnik.prefabs.devices.AnalogUltrasonic;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicPair {

	private AnalogUltrasonic left;
	private AnalogUltrasonic right;
	private double separation;
	
	public UltrasonicPair()
	{
		this.left = new AnalogUltrasonic(0);
		this.right = new AnalogUltrasonic(1);
		this.separation = 1D;
	}
	public UltrasonicPair(int left, int right, double separation)
	{
		this.left = new AnalogUltrasonic(left);
		this.right = new AnalogUltrasonic(right);
		this.separation = 1D;
	}
	public double getDifference()
	{
		return right.getDistance() - left.getDistance();
	}
	public double getDifference(int sample)
	{
		return right.getDistance(sample) - left.getDistance(sample);
	}
	/*
	 * returns the angle of the robot relative to a wall
	 * If it is facing the wall, the angle is zero.
	 * Facing to the left returns a negative angle.
	 */
	public double getAngle()
	{
		double angle = Math.acos(separation/Math.pow((Math.pow(separation, 2) + Math.pow(getDifference(), 2)), 0.5));
		if( getDifference() < 0 )
		{
			return angle * -1;
		}
		else
		{
			return angle;
		}
	}
	public double getAngle(int sample)
	{
		double angle = Math.acos(separation/Math.pow((Math.pow(separation, 2) + Math.pow(getDifference(sample), 2)), 0.5));
		if( getDifference(sample) < 0 )
		{
			return angle * -1;
		}
		else
		{
			return angle;
		}
	}
	public String getDirection()
	{
		if( this.getDifference() < 0 )
		{
			return "left";
		}
		else if( this.getDifference() > 0 )
		{
			return "right";
		}
		else
		{
			return "straight";
		}
	}
	public String getDirection(double tolerance)
	{
		if( this.getDifference() < -tolerance )
		{
			return "left";
		}
		if( this.getDifference() > tolerance )
		{
			return "right";
		}
		else
		{
			return "straight";
		}
	}
	public double getDistance()
	{
		double leftDistance = left.getDistance();
		double rightDistance = right.getDistance();
		return Math.min(leftDistance, rightDistance);
	}
	public double getDistance(int sample)
	{
		double leftDistance = left.getDistance(sample);
		double rightDistance = right.getDistance(sample);
		return Math.min(leftDistance, rightDistance);
	}
}
