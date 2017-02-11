package com._604robotics.robot2017.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.prefabs.trigger.TriggerOr;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;

public class TeleopMode extends Coordinator {
    private final XboxController driver = new XboxController(0 /* Port Constant */);
    private final XboxController manipulator = new XboxController(1 /* Port Constant */);

    public TeleopMode () {        
        driver.leftStick.X.setDeadband(Calibration.TELEOP_DEADBAND);
        driver.leftStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);

        driver.leftStick.X.setFactor(Calibration.TELEOP_FACTOR);
        driver.leftStick.Y.setFactor(Calibration.TELEOP_FACTOR);

        driver.rightStick.X.setDeadband(Calibration.TELEOP_DEADBAND);
        driver.rightStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);

        driver.rightStick.X.setFactor(Calibration.TELEOP_FACTOR);
        driver.rightStick.Y.setFactor(Calibration.TELEOP_FACTOR);

        manipulator.leftStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);
        manipulator.rightStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);
    }

    @Override
    protected void apply (ModuleManager modules) {
    	/* Backup Drive */
    	{
    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
    				modules.getModule("Dashboard").getTrigger("Drive On"),
            		modules.getModule("Dashboard").getTrigger("Basic Drive"))));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Left Power", driver.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Right Power", driver.rightStick.Y));
    	}
    	/* Dynamic Drive */
    	{
    		this.bind(new Binding(modules.getModule("Drive").getAction("Dynamic Drive"), new TriggerAnd(
            		modules.getModule("Dashboard").getTrigger("Drive On"),
            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"))));
            this.fill(new DataWire(modules.getModule("Drive").getAction("Dynamic Drive"), "leftY", driver.leftStick.Y));
            this.fill(new DataWire(modules.getModule("Drive").getAction("Dynamic Drive"), "leftX", driver.leftStick.X));
            this.fill(new DataWire(modules.getModule("Drive").getAction("Dynamic Drive"), "rightY", driver.rightStick.Y));
            this.fill(new DataWire(modules.getModule("Drive").getAction("Dynamic Drive"), "rightX", driver.rightStick.X));
    	}
    	/* Dynamic Toggle Module */
    	{
    		TriggerAnd DynamicOn=new TriggerAnd(
            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
            		modules.getModule("Dashboard").getTrigger("Drive On")
            );
    		TriggerAnd DynamicConflict=new TriggerAnd(
            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
            		modules.getModule("Dashboard").getTrigger("Drive On"),
            		driver.buttons.A, driver.buttons.B
            );
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("Check"), 
        		new TriggerOr(DynamicOn, DynamicConflict)
        	));
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("OverrideTank"), new TriggerAnd(
            		DynamicOn, driver.buttons.A, new TriggerNot(driver.buttons.B)
            	)));
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("OverrideArcade"), new TriggerAnd(
            		DynamicOn, driver.buttons.B, new TriggerNot(driver.buttons.A)
            	)));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightY", driver.rightStick.Y));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightX", driver.rightStick.X));
    	}
    	/* Toggle Drive */
    	{
    		final TriggerToggle driveMode = new TriggerToggle(driver.buttons.X, false);
    		
    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
    					modules.getModule("Dashboard").getTrigger("Toggle Drive"),
    					driveMode.off)
    				));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Move Power", driver.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Rotate Power", driver.rightStick.X));
    		
    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
					modules.getModule("Dashboard").getTrigger("Toggle Drive"),
					driveMode.on)
				));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Left Power", driver.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Right Power", driver.rightStick.Y));
    	}
    	/* Climber */
    	{
    		final TriggerToggle climbToggle = new TriggerToggle(driver.buttons.Y, false);
            this.bind(new Binding(modules.getModule("Climber").getAction("Run"), climbToggle.on));
            this.bind(new Binding(modules.getModule("Climber").getAction("Idle"), climbToggle.off));
    	}        
    }
}
