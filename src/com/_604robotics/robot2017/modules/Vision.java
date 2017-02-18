package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.CameraServer;

public class Vision extends Module {
    public Vision () {
    	// Stream capture to dashboard.
        CameraServer.getInstance().startAutomaticCapture("cam0",0);
    }
}
