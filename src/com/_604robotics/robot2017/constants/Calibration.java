package com._604robotics.robot2017.constants;

public final class Calibration {
    private Calibration () {}
    
    /* Teleop Xbox Controller Constants */
    public static final double TELEOP_DEADBAND = 0.3;
    public static final double TELEOP_FACTOR_DEFAULT = -1;
    
    /* Drive Left Constants */
    public static final double DRIVE_LEFT_PID_P = 0.02;
    public static final double DRIVE_LEFT_PID_I = 0;
    public static final double DRIVE_LEFT_PID_D = 0.02;
    public static final double DRIVE_LEFT_PID_MAX = Math.sqrt(0.5); // automatically squared to 0.7
    public static final double DRIVE_LEFT_PID_TOLERANCE = 20;

    /* Drive Right Constants - unused */
    public static final double DRIVE_RIGHT_PID_P = 0.04;
    public static final double DRIVE_RIGHT_PID_I = 0;
    public static final double DRIVE_RIGHT_PID_D = 0.04;
    public static final double DRIVE_RIGHT_PID_MAX = 0.84; // automatically squared to 0.7
    public static final double DRIVE_RIGHT_PID_TOLERANCE = 20;
    
    public static final double ULTRA_SEPARATION = 22.0;
    public static final double ULTRA_TARGET = 5;
    
    /* Drive Right Constants */
    public static final double DRIVE_ULTRA_PID_P = 0.02;
    public static final double DRIVE_ULTRA_PID_I = 0;
    public static final double DRIVE_ULTRA_PID_D = 0.0005;
    public static final double DRIVE_ULTRA_PID_MAX = 1.0;
    public static final double DRIVE_ULTRA_PID_TOLERANCE = 10;

    /* Auton constants */
    public static final double FWD_CLICKS_SMOL = 400;
    public static final double FWD_CLICKS_X = 400;

    public static final double FWD_CLICKS=810;
    public static final double LONG_FWD_CLICKS=2800;
    public static final double FWD_CLICKS_ALT=1250;
    public static final double BKWD_CLICKS=-550;
    public static final double ROT_CLICKS=500;
    
    
    public static final double KINEMATIC_TIMEM = 0.9;
    public static final double KINEMATIC_TIMELR = 2.2;
    public static final double ROTATE_WAIT = 0.5;
    public static final double ROTATE_TIME = 0.42;
    public static final double ROTATE_POWER = 0.5;
    
    public static final double DRIVE_ROTATE_PID_P = 0.04;
    public static final double DRIVE_ROTATE_PID_I = 0;
    public static final double DRIVE_ROTATE_PID_D = 0.08;
    public static final double DRIVE_ROTATE_PID_MAX = Math.sqrt(0.7);
    public static final double DRIVE_ROTATE_PID_TOLERANCE = 1/6;
   
    public static final double KINEMATIC_POWER = 0.7;
    public static final double KINEMATIC_POWER2 = 1.0;
    
    public static final double ROTATE_TARGET_A = 90;
    public static final double ROTATE_TARGET_B = 40;
    /* All for "Testing 1" */
    public static final double ROTATE_TURN_INIT_FOWARD = 1730;
    public static final double ROTATE_TURN_FINAL_FOWARD = 2000;
    public static final double ROTATE_TURN_TARGET = 53; // min: 37 - max: 55 -- this hit 52 degrees
    public static final double ROTATE_TOLERANCE = 3;
    public static final double ROTATE_WIGGLE_TARGET = 5;
    public static final double ROTATE_LEFT_TARGET = 50;
    public static final double ROTATE_RIGHT_TARGET = 56;
    
    public static final double ROTATE_ANGLE = 90;
    
    public static final double INTAKE_POWER = 0.8;
    
    public static final double TESTING_ANGLE = 60;
    public static final double CAL_ONE = 400;
    public static final double CAL_TWO = 800;
    public static final double CAL_THREE = 1200;
    
    public static final double CAL_ONE_DIST = 20;
    public static final double CAL_TWO_DIST = 40;
    public static final double CAL_THREE_DIST = 60;
    public static final double TARGET = 80;
    
    public static final double TESTING_CLICKS = 1346.4;
    public static final double FAIL_SAFE_TIME = 3.5;
    public static final double FAIL_SAFE_POWER_LEFT = 0.881;
    public static final double FAIL_SAFE_POWER_RIGHT = 0.889;
    public static final double SIDESTEP_POWER_2 = Math.sqrt(0.5);
    
    public static final double BLUE_LEFT_STEP = 1980;
    public static final double BLUE_RIGHT_STEP = 1180;
    public static final double RED_LEFT_STEP = 1500;
    public static final double RED_RIGHT_STEP = 2000;
    public static final double WIGGLE_WAIT = 3;
    public static final double TIMER_FORWARD = 3;
}
