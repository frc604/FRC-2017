package com._604robotics.robot2017.modes;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.prefabs.trigger.TriggerOr;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;

public class TeleopMode extends Coordinator {
    public static final XboxController driver = new XboxController(Ports.CONTROLLER_DRIVER);
    public static final XboxController testing = new XboxController(Ports.CONTROLLER_TEST);

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
        
        testing.leftStick.X.setDeadband(Calibration.TELEOP_DEADBAND);
        testing.leftStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);

        testing.leftStick.X.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);
        testing.leftStick.Y.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);

        testing.rightStick.X.setDeadband(Calibration.TELEOP_DEADBAND);
        testing.rightStick.Y.setDeadband(Calibration.TELEOP_DEADBAND);

        testing.rightStick.X.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);
        testing.rightStick.Y.setFactor(Calibration.TELEOP_FACTOR_DEFAULT);
    }

    @Override
    protected void apply (ModuleManager modules) {
    	/* Default Drive */
    	{
    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
    				new TriggerNot(driver.buttons.RB),
            		new TriggerNot(driver.buttons.B),
    				modules.getModule("Dashboard").getTrigger("Drive On"),
            		modules.getModule("Dashboard").getTrigger("Tank Drive"))));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Left Power", driver.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Right Power", driver.rightStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "Gear Ratio", modules.getModule("PseudoShifter").getData("Gear Ratio")));
    		
    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
    				new TriggerNot(driver.buttons.RB),
    				new TriggerNot(driver.buttons.Y),
            		new TriggerNot(driver.buttons.B),
    				modules.getModule("Dashboard").getTrigger("Drive On"),
            		modules.getModule("Dashboard").getTrigger("Arcade Drive"))));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Move Power", driver.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Rotate Power", driver.rightStick.X));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "Gear Ratio", modules.getModule("PseudoShifter").getData("Gear Ratio")));

    	}
    	/* Gear Pickup */
    	{    		
    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Retract"), driver.buttons.Y));
    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Extend"), new TriggerOr(driver.buttons.X, driver.buttons.B, driver.buttons.A)));
    		
    		this.bind(new Binding(modules.getModule("Intake").getAction("Off"), driver.buttons.Y));
    		this.bind(new Binding(modules.getModule("Intake").getAction("Forward"), driver.buttons.X));
    		this.bind(new Binding(modules.getModule("Intake").getAction("Reverse"), driver.buttons.B));
    	}
    	/* Dynamic Drive */
    	{
    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
            		new TriggerNot(driver.buttons.RB),
            		new TriggerNot(driver.buttons.Y),
            		new TriggerNot(driver.buttons.B),
    				modules.getModule("Dashboard").getTrigger("Drive On"),
            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
            		modules.getModule("DynamicToggle").getTrigger("Tank Drive"))));
    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
    				new TriggerNot(driver.buttons.RB),
    				new TriggerNot(driver.buttons.Y),
            		new TriggerNot(driver.buttons.B),
            		modules.getModule("Dashboard").getTrigger("Drive On"),
            		modules.getModule("Dashboard").getTrigger("Dynamic Drive"),
            		modules.getModule("DynamicToggle").getTrigger("Arcade Drive"))));
    		
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("OverrideTank"), new TriggerAnd(
            		driver.buttons.A, new TriggerNot(driver.buttons.B))));
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("OverrideArcade"), new TriggerAnd(
            		driver.buttons.B, new TriggerNot(driver.buttons.A))));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightY", driver.rightStick.Y));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightX", driver.rightStick.X));
    	}
    	/* Manual Regulator */
    	{
    		final TriggerToggle charge = new TriggerToggle(driver.buttons.LT, false);
    		
    		this.bind(new Binding(modules.getModule("ManualRegulator").getAction("Charge"), charge.on));
    		this.bind(new Binding(modules.getModule("ManualRegulator").getAction("Idle"), charge.off));
    	}
    	
    	/* Pseudo Shifter */
    	{
    		final TriggerToggle shift = new TriggerToggle(driver.buttons.LB, false);
    		
    		this.bind(new Binding(modules.getModule("PseudoShifter").getAction("Low Gear"), shift.on));
    		this.bind(new Binding(modules.getModule("PseudoShifter").getAction("High Gear"), shift.off));
    	}
    	
    	/* Toggle Drive */
/*    	{
    		final TriggerToggle driveMode = new TriggerToggle(driver.buttons.X, false);
    		
    		this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), new TriggerAnd(
    				modules.getModule("Dashboard").getTrigger("Drive On"),
    				modules.getModule("Dashboard").getTrigger("Toggle Drive"),
    				driveMode.off)));

    		this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), new TriggerAnd(
    				modules.getModule("Dashboard").getTrigger("Drive On"),
					modules.getModule("Dashboard").getTrigger("Toggle Drive"),
					driveMode.on)));
    	}
    	/* Xbox Flip Axis */
    	{
    		this.bind(new Binding(modules.getModule("XboxFlip").getAction("Flip"), driver.buttons.RB));
    	}
    	/* Calibrate*/
    	{
       		this.bind(new Binding(modules.getModule("Drive").getAction("Calibrate"), driver.buttons.Back));
    	}
    	/* Rumble Test */
    	{
    		this.bind(new Binding(modules.getModule("RumbleControl").getAction("On")));
    		this.fill(new DataWire(modules.getModule("RumbleControl").getAction("On"), "High Power Rumble", testing.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("RumbleControl").getAction("On"), "Low Power Rumble", testing.rightStick.Y));
    	}
    	/* Bridge */
    	{
    		this.bind(new Binding(modules.getModule("Arbitrary").getAction("Test"), modules.getModule("Activator").getTrigger("Test Trigger")));
    		
    		/*
    		this.bind(new Binding(modules.getModule("Activator").getAction("Initiate"), new TriggerAnd(
    				driver.buttons.Start,
    				new TriggerNot(modules.getModule("Arbitrary").getTrigger("Complete One")),
    				new TriggerNot(modules.getModule("Arbitrary").getTrigger("Complete Two")),
    				new TriggerNot(modules.getModule("Arbitrary").getTrigger("Complete Three"))
    			)));
    		
    		this.bind(new Binding(modules.getModule("Arbitrary").getAction("Step One"), modules.getModule("Activator").getTrigger("Step One")));
    		this.bind(new Binding(modules.getModule("Arbitrary").getAction("Step Two"), modules.getModule("Activator").getTrigger("Step Two")));
    		this.bind(new Binding(modules.getModule("Arbitrary").getAction("Step Three"), modules.getModule("Activator").getTrigger("Step Three")));
    		
    		this.bind(new Binding(modules.getModule("Activator").getAction("Step One Finished"), modules.getModule("Arbitrary").getTrigger("Complete One")));
    		this.bind(new Binding(modules.getModule("Activator").getAction("Step Two Finished"), modules.getModule("Arbitrary").getTrigger("Complete Two")));
    		this.bind(new Binding(modules.getModule("Activator").getAction("Step Three Finished"), modules.getModule("Arbitrary").getTrigger("Complete Three")));
    		*/
    	}
    }
}