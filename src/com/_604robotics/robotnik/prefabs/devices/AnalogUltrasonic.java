package com._604robotics.robotnik.prefabs.devices;

import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogUltrasonic extends AnalogInput {

	int m_port = 0;
	private final double INCHES_PER_VOLT = 42.56;
	
	public AnalogUltrasonic()
	{
		super(0);
	}
	public AnalogUltrasonic(int port)
	{
		super(port);
		this.m_port = port;
	}
	public double getAnalog()
	{
		double total = 0;
		for( int f=0; f<64; f++ )
		{
			total += super.getValue();
		}
		total /= 64;
		return total;
	}
	public double getAnalog(int sample)
	{
		double total = 0;
		for( int f=0; f<sample; f++ )
		{
			total += super.getValue();
		}
		total /= sample;
		return total;
	}
	public double getVoltage()
	{
		double total = 0;
		for( int f=0; f<64; f++ )
		{
			total += super.getVoltage();
		}
		total /= 64;
		return total;
	}
	public double getVoltage(int sample)
	{
		double total = 0;
		for( int f=0; f<sample; f++ )
		{
			total += super.getVoltage();
		}
		total /= sample;
		return total;
	}
	public double getDistance()
	{
		double voltage = this.getVoltage();
		return voltage * this.INCHES_PER_VOLT;
	}
	public double getDistance(int sample)
	{
		double voltage = this.getVoltage(sample);
		return voltage * this.INCHES_PER_VOLT;
	}
	public double getPort()
	{
		return this.m_port;
	}
}
