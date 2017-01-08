package com._604robotics.robot2017.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.joystick.JoystickController;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;

import edu.wpi.first.wpilibj.Joystick;

public class TeleopMode extends Coordinator {
    private final XboxController driver = new XboxController(0 /* Port Constant */);
    private final XboxController manipulator = new XboxController(0 /* Port Constant */);

    public TeleopMode () {        
        driver.leftStick.X.setDeadband(0 /* Calibration Constant */);
        driver.leftStick.Y.setDeadband(0 /* Calibration Constant */);

        driver.leftStick.X.setFactor(0 /* Calibration Constant */);
        driver.leftStick.Y.setFactor(0 /* Calibration Constant */);

        driver.rightStick.X.setDeadband(0 /* Calibration Constant */);
        driver.rightStick.Y.setDeadband(0 /* Calibration Constant */);

        driver.rightStick.X.setFactor(0 /* Calibration Constant */);
        driver.rightStick.Y.setFactor(0 /* Calibration Constant */);

        manipulator.leftStick.Y.setDeadband(0 /* Calibration Constant */);
        manipulator.rightStick.Y.setDeadband(0 /* Calibration Constant */);
    }

    @Override
    protected void apply (ModuleManager modules) {
        
    }
}
