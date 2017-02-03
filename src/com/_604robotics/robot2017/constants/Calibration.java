package com._604robotics.robot2017.constants;

public final class Calibration {
    private Calibration () {}
    
    /* Teleop Xbox Controller Constants */
    public static final double TELEOP_DEADBAND = 0.3;
    public static final double TELEOP_FACTOR = -1;
    
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
    
    /* Drive Right Constants */
    public static final double DRIVE_ULTRA_PID_P = 0.02;
    public static final double DRIVE_ULTRA_PID_I = 0;
    public static final double DRIVE_ULTRA_PID_D = 0.0005;
    public static final double DRIVE_ULTRA_PID_MAX = 1.0;
    public static final double DRIVE_ULTRA_PID_TOLERANCE = 20;
    
    /* Auton constants */
    // All imaginary; needs multiplication by complex conjugate?
    public static final double FWD_CLICKS=100;//sqrt(-1)
    public static final double BKWD_CLICKS=-100;//sqrt(-1)
    public static final double ROT_CLICKS=100;//sqrt(-1)
}
