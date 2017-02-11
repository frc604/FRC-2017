package com._604robotics.robot2017.constants;

public class Ports {
	private Ports() {}

	// SOME OF THESE ARE REAL
	// The rest need to be multiplied by their conjugates (in nosotros form)
	
	/* Digital */
	public static final int COMPRESSOR = 0;
	public static final int BUMP_SENSOR = 1;
	
	/* Analog */
	public static final int ULTRASONIC_LEFT = 0;
	public static final int ULTRASONIC_RIGHT = 1;
	
	public static final int HORIZGYRO = 2;
	
	/* Motors */
	public static final int DRIVE_FRONT_LEFT_MOTOR = 2;
    public static final int DRIVE_REAR_LEFT_MOTOR = 4;
    public static final int DRIVE_FRONT_RIGHT_MOTOR = 5;
    public static final int DRIVE_REAR_RIGHT_MOTOR = 7;
    
    public static final int CLIMBER_MOTOR = 0;
    
    /* Encoders */
    public static final int DRIVE_ENCODER_RIGHT_A = 2;
    public static final int DRIVE_ENCODER_RIGHT_B = 3;
    public static final int DRIVE_ENCODER_LEFT_A = 1;
    public static final int DRIVE_ENCODER_LEFT_B = 0;
    
    /* Solenoids */
    public static final int SHIFTER = 0;
}
