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
    public static final XboxController driver = new XboxController(Ports.CONTROLLER_DRIVER);
    public static final XboxController override = new XboxController(Ports.OVERRIDE_DRIVER);

    public TeleopMode () {
    	/* Default factor set here; changed in other parts of the code */
        driver.leftStick.X.setDeadband(Calibration.TELEOP_DEADBAND);
        driver.leftStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);

        driver.leftStick.X.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);
        driver.leftStick.Y.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);

        driver.rightStick.X.setDeadband(Calibration.TELEOP_DEADBAND);
        driver.rightStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);

        driver.rightStick.X.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);
        driver.rightStick.Y.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);
    }

    @Override
    protected void apply (ModuleManager modules) {
    	/* Override Toggle */
    	final TriggerToggle overrideToggle = new TriggerToggle(override.buttons.Start, false);
    	
    	/* Default Controls */
    	{
	    	/* Default Drive */
	    	{
	    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
	    				new TriggerNot(driver.buttons.RB),
	            		new TriggerNot(driver.buttons.B),
	    				modules.getModule("Dashboard").getTrigger("Drive On"),
	            		modules.getModule("Dashboard").getTrigger("Tank Drive"))));
	    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Left Power", driver.leftStick.Y));
	    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Right Power", driver.rightStick.Y));
	    		
	    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
	    				new TriggerNot(driver.buttons.RB),
	    				new TriggerNot(driver.buttons.Y),
	            		new TriggerNot(driver.buttons.B),
	    				modules.getModule("Dashboard").getTrigger("Drive On"),
	            		modules.getModule("Dashboard").getTrigger("Arcade Drive"))));
	    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Move Power", driver.leftStick.Y));
	    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Rotate Power", driver.rightStick.X));
	    	}
    	}
    	
    	/* Driver Controls */
    	{
	    	/* Dynamic Drive */
	    	{
	    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
	            		overrideToggle.off,
	    				new TriggerNot(driver.buttons.RB),
	            		new TriggerNot(driver.buttons.Y),
	            		new TriggerNot(driver.buttons.B),
	    				modules.getModule("Dashboard").getTrigger("Drive On"),
	            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
	            		modules.getModule("DynamicToggle").getTrigger("Tank Drive"))));
	    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
	    				overrideToggle.off,
	    				new TriggerNot(driver.buttons.RB),
	    				new TriggerNot(driver.buttons.Y),
	            		new TriggerNot(driver.buttons.B),
	            		modules.getModule("Dashboard").getTrigger("Drive On"),
	            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
	            		modules.getModule("DynamicToggle").getTrigger("Arcade Drive"))));
	    		
	    		this.bind(new Binding(modules.getModule("DynamicToggle").getAction("Check Driver"), overrideToggle.off));
	        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check Driver"), "rightY", driver.rightStick.Y));
	        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check Driver"), "rightX", driver.rightStick.X));
	    		
	    	}
	    	
	    	/* Xbox Flip Axis */
	    	{
	    		this.bind(new Binding(modules.getModule("XboxFlip").getAction("Flip"), new TriggerAnd(
	    				overrideToggle.off,
	    				driver.buttons.RB
	    				)));
	    	}
	    	/* Shifter */
	    	{
	            this.bind(new Binding(modules.getModule("GearShifter").getAction("High Gear"), new TriggerAnd(
	            		overrideToggle.off,
	            		new TriggerToggle(driver.buttons.LB, false).on
	            		)));
	    	}
	    	/* Gear Pickup */
	    	{    		
	    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Retract"), new TriggerAnd(
	    				overrideToggle.off,
	    				driver.buttons.Y)));
	    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Extend"), new TriggerAnd(
	    				overrideToggle.off,
	    				new TriggerOr(driver.buttons.X, driver.buttons.B, driver.buttons.A))));
	    		
	    		this.bind(new Binding(modules.getModule("Intake").getAction("Off"), new TriggerAnd(
	    				overrideToggle.off,
	    				driver.buttons.Y)));
	    		this.bind(new Binding(modules.getModule("Intake").getAction("Forward"), new TriggerAnd(
	    				overrideToggle.off,
	    				driver.buttons.X)));
	    		this.bind(new Binding(modules.getModule("Intake").getAction("Reverse"), new TriggerAnd(
	    				overrideToggle.off,
	    				driver.buttons.B)));
	    	}
	    	/* Climber */
	    	{
	       		this.bind(new Binding(modules.getModule("Climber").getAction("Run Driver"), new TriggerAnd(
	       				overrideToggle.off,
	       				driver.buttons.LT)));
	    		this.fill(new DataWire(modules.getModule("Climber").getAction("Run Driver"), "Power", driver.triggers.Left));            
	    	}
	    	/* SpikeLight */
	    	{
	            this.bind(new Binding(modules.getModule("SpikeLight").getAction("On"), new TriggerAnd(
	            		overrideToggle.off,
	            		new TriggerToggle(driver.buttons.RT, false).on)));
	    	}
    	}
    	
    	/* Override Controls */
    	{
	    	/* Dynamic Drive */
	    	{
	    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
	            		overrideToggle.on,
	    				new TriggerNot(override.buttons.RB),
	            		new TriggerNot(override.buttons.Y),
	            		new TriggerNot(override.buttons.B),
	    				modules.getModule("Dashboard").getTrigger("Drive On"),
	            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
	            		modules.getModule("DynamicToggle").getTrigger("Tank Drive"))));
	    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
	    				overrideToggle.on,
	    				new TriggerNot(override.buttons.RB),
	    				new TriggerNot(override.buttons.Y),
	            		new TriggerNot(override.buttons.B),
	            		modules.getModule("Dashboard").getTrigger("Drive On"),
	            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
	            		modules.getModule("DynamicToggle").getTrigger("Arcade Drive"))));
	    		
	    		this.bind(new Binding(modules.getModule("DynamicToggle").getAction("Check Override"), overrideToggle.on));
	        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check Override"), "rightY", override.rightStick.Y));
	        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check Override"), "rightX", override.rightStick.X));
	    		
	    	}
	    	
	    	/* Xbox Flip Axis */
	    	{
	    		this.bind(new Binding(modules.getModule("XboxFlip").getAction("Flip"), new TriggerAnd(
	    				overrideToggle.on,
	    				override.buttons.RB
	    				)));
	    	}
	    	/* Shifter */
	    	{
	            this.bind(new Binding(modules.getModule("GearShifter").getAction("High Gear"), new TriggerAnd(
	            		overrideToggle.on,
	            		new TriggerToggle(override.buttons.LB, false).on
	            		)));
	    	}
	    	/* Gear Pickup */
	    	{    		
	    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Retract"), new TriggerAnd(
	    				overrideToggle.on,
	    				override.buttons.Y)));
	    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Extend"), new TriggerAnd(
	    				overrideToggle.on,
	    				new TriggerOr(override.buttons.X, override.buttons.B, override.buttons.A))));
	    		
	    		this.bind(new Binding(modules.getModule("Intake").getAction("Off"), new TriggerAnd(
	    				overrideToggle.on,
	    				override.buttons.Y)));
	    		this.bind(new Binding(modules.getModule("Intake").getAction("Forward"), new TriggerAnd(
	    				overrideToggle.on,
	    				override.buttons.X)));
	    		this.bind(new Binding(modules.getModule("Intake").getAction("Reverse"), new TriggerAnd(
	    				overrideToggle.on,
	    				override.buttons.B)));
	    	}
	    	/* Climber */
	    	{
	       		this.bind(new Binding(modules.getModule("Climber").getAction("Run"), new TriggerAnd(
	       				overrideToggle.on,
	       				override.buttons.LT)));
	    		this.fill(new DataWire(modules.getModule("Climber").getAction("Run Override"), "Power", override.triggers.Left));            
	    	}
	    	/* SpikeLight */
	    	{
	            this.bind(new Binding(modules.getModule("SpikeLight").getAction("On"), new TriggerAnd(
	            		overrideToggle.on,
	            		new TriggerToggle(override.buttons.RT, false).on)));
	    	}
    	}
    	
    	/* Experimental Controls */
    	{
	    	/* Rumbling */
	    	/*
	    	{
	    		this.bind(new Binding(modules.getModule("RumbleControl").getAction("On"), modules.getModule("Intake").getTrigger("Rumble")));
	    		this.fill(new DataWire(modules.getModule("RumbleControl").getAction("On"), "High Power Rumble", 1D));
	    		this.fill(new DataWire(modules.getModule("RumbleControl").getAction("On"), "Low Power Rumble", 0D));    		
	    	}
	    	*/
	    	
	    	/* Calibrate*/
	    	{
	       		//this.bind(new Binding(modules.getModule("Drive").getAction("Calibrate"), driver.buttons.Back));
	    	}
    	}
    }
}
