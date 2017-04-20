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

import net.spinda.frc.RecordableJoystick;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;

public class TeleopMode extends Coordinator {
    public static final RecordableJoystick joystick = new RecordableJoystick(Ports.CONTROLLER_DRIVER);
    public static final XboxController driver = new XboxController(joystick);

    private final XboxController manipulator = new XboxController(Ports.CONTROLLER_MANIPULATOR);

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
        /* Recorder */
        {
            final TriggerToggle recordToggle = new TriggerToggle(manipulator.buttons.A, false);
            this.bind(new Binding(modules.getModule("Recorder").getAction("Record"), new TriggerAnd(
                    modules.getModule("GameMode").getTrigger("Teleop"),
                    recordToggle.on)));

            this.bind(new Binding(modules.getModule("Recorder").getAction("Play"), modules.getModule("GameMode").getTrigger("Autonomous")));
        }
        
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
            		driver.buttons.A, new TriggerNot(driver.buttons.Back))));
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("OverrideArcade"), new TriggerAnd(
            		driver.buttons.B, new TriggerNot(driver.buttons.Start))));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightY", driver.rightStick.Y));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightX", driver.rightStick.X));
    		
    	}
    	
    	/* Xbox Flip Axis */
    	{
    		this.bind(new Binding(modules.getModule("XboxFlip").getAction("Flip"), driver.buttons.RB));
    	}
    	/* Shifter */
    	{
            this.bind(new Binding(modules.getModule("GearShifter").getAction("High Gear"), new TriggerToggle(driver.buttons.LB, false).on));
    	}
    	/* Gear Pickup */
    	{    		
    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Retract"), driver.buttons.Y));
    		this.bind(new Binding(modules.getModule("FlipFlop").getAction("Extend"), new TriggerOr(driver.buttons.X, driver.buttons.B, driver.buttons.A)));
    		
    		this.bind(new Binding(modules.getModule("Intake").getAction("Off"), driver.buttons.Y));
    		this.bind(new Binding(modules.getModule("Intake").getAction("Forward"), driver.buttons.X));
    		this.bind(new Binding(modules.getModule("Intake").getAction("Reverse"), driver.buttons.B));
    	}
    	/* Climber */
    	{
       		this.bind(new Binding(modules.getModule("Climber").getAction("Run"), driver.buttons.LT));
    		this.fill(new DataWire(modules.getModule("Climber").getAction("Run"), "Power", driver.triggers.Left));            
    	}
    	/* SpikeLight */
    	{
            this.bind(new Binding(modules.getModule("SpikeLight").getAction("On"), new TriggerToggle(driver.buttons.RT, false).on));
    	}
    	
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
