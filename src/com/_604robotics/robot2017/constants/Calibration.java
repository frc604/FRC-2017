package com._604robotics.robot2017.constants;

public final class Calibration {
    private Calibration () {}
    
    /* Teleop Xbox Controller Constants */
    public static final double TELEOP_DEADBAND = 0.3;
    public static final double TELEOP_FACTOR = -1;
    
    /* Drive Movement Constants */
    public static final double DRIVE_MOVE_PID_P = 0.02;
    public static final double DRIVE_MOVE_PID_I = 0;
    public static final double DRIVE_MOVE_PID_D = 0.005;
    public static final double DRIVE_MOVE_PID_MAX = 1.0;
    public static final double DRIVE_MOVE_PID_TOLERANCE = 20;

    /* Drive Rotation Constants */
    // NONE OF THESE ARE REAL
    public static final double DRIVE_ROTATE_PID_P = 0.02;
    public static final double DRIVE_ROTATE_PID_I = 0;
    public static final double DRIVE_ROTATE_PID_D = 0.005;
    public static final double DRIVE_ROTATE_PID_MAX = 1.0;
    public static final double DRIVE_ROTATE_PID_TOLERANCE = 20;
    
}
