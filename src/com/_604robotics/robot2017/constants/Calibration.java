package com._604robotics.robot2017.constants;

public final class Calibration {
    private Calibration () {}
    
    /* Teleop Xbox Controller Constants */
    public static final double TELEOP_DEADBAND = 0.3;
    public static final double TELEOP_FACTOR_DEFAULT = -1;
    
    /* Drive Left Constants */
    public static final double DRIVE_LEFT_PID_P = 0.02;
    public static final double DRIVE_LEFT_PID_I = 0;
    public static final double DRIVE_LEFT_PID_D = 0.005;
    public static final double DRIVE_LEFT_PID_MAX = 1.0;
    public static final double DRIVE_LEFT_PID_TOLERANCE = 20;

    /* Drive Right Constants */
    public static final double DRIVE_RIGHT_PID_P = 0.02;
    public static final double DRIVE_RIGHT_PID_I = 0;
    public static final double DRIVE_RIGHT_PID_D = 0.005;
    public static final double DRIVE_RIGHT_PID_MAX = 1.0;
    public static final double DRIVE_RIGHT_PID_TOLERANCE = 20;
    
    public static final double ULTRA_SEPARATION = 22.0;
    public static final double ULTRA_TARGET = 5;
    public static final double RANGE = 72.0; // 6 feet
    
    /* Drive Right Constants */
    public static final double DRIVE_ULTRA_PID_P = 0.02;
    public static final double DRIVE_ULTRA_PID_I = 0;
    public static final double DRIVE_ULTRA_PID_D = 0.0005;
    public static final double DRIVE_ULTRA_PID_MAX = 1.0;
    public static final double DRIVE_ULTRA_PID_TOLERANCE = 20;

    /* Auton constants */
    // All imaginary; needs multiplication by complex conjugate?
    public static final double FWD_CLICKS=865;//sqrt(-1)
    public static final double BKWD_CLICKS=-865;//sqrt(-1)
    public static final double ROT_CLICKS=500;//sqrt(-1)
    
    public static final double KINEMATIC_TIMEM = 0.9;
    public static final double KINEMATIC_TIMELR = 2.2;
    public static final double WAIT = 2.5;
    public static final double ROTATE_TIME = 0.42;
    public static final double ROTATE_POWER = 0.5;
    
    public static final double DRIVE_ROTATE_PID_P = 0.02;
    public static final double DRIVE_ROTATE_PID_I = 0;
    public static final double DRIVE_ROTATE_PID_D = 0.005;
    public static final double DRIVE_ROTATE_PID_MAX = 1.0;
    public static final double DRIVE_ROTATE_PID_TOLERANCE = 20;
    
    public static final double KINEMATIC_POWER = 0.5;
    public static final double KINEMATIC_POWER2 = 1.0;
    
    public static final double ROTATE_TARGET_A = 90;
    public static final double ROTATE_TARGET_B = -90;
    public static final double ROTATE_TOLERANCE = 1.5;
    
    public static final double ROTATE_ANGLE = 90;
}
