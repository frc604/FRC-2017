package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.CameraServer;

public class Vision extends Module {
    public Vision () {
    	CameraServer.getInstance().startAutomaticCapture("cam0", "/dev/video0");
    }
}
