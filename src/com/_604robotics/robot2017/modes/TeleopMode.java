package com._604robotics.robot2017.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;

public class TeleopMode extends Coordinator {
    public static final XboxController driver = new XboxController(Ports.CONTROLLER_DRIVER);

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
            		driver.buttons.A, new TriggerNot(driver.buttons.B))));
        	this.bind(new Binding(modules.getModule("DynamicToggle").getAction("OverrideArcade"), new TriggerAnd(
            		driver.buttons.B, new TriggerNot(driver.buttons.A))));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightY", driver.rightStick.Y));
        	this.fill(new DataWire(modules.getModule("DynamicToggle").getAction("Check"), "rightX", driver.rightStick.X));
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
    	/* Ultrasonic */
    	{
    		this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Align"), driver.buttons.Y));

    	}
    	/* Ultra Drive */
/*    	{
    		this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Drive"), new TriggerAnd(
    				new TriggerNot(driver.buttons.RB),
    				new TriggerNot(driver.buttons.Y),
            		driver.buttons.B,
    				modules.getModule("Dashboard").getTrigger("Drive On"))));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Drive"), "Move Power", driver.leftStick.Y));
    		this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Drive"), "Rotate Power", driver.rightStick.X));
    	}*/
    	/* Xbox Flip Axis */
    	{
    		this.bind(new Binding(modules.getModule("XboxFlip").getAction("Flip"), driver.buttons.RB));
    	}
    	/* Shifter */
    	{
            this.bind(new Binding(modules.getModule("GearShifter").getAction("High Gear"), new TriggerToggle(driver.buttons.LB, false).on));
    	}
    	/* Gear Broom */
    	{
    		final TriggerToggle broomToggle = new TriggerToggle(driver.buttons.X, false);
    		
    		this.bind(new Binding(modules.getModule("Broom").getAction("Retract"), broomToggle.off));
    		this.bind(new Binding(modules.getModule("Broom").getAction("Extend"), broomToggle.on));
    	}
    	/* Climber */
    	{
       		this.bind(new Binding(modules.getModule("Climber").getAction("Run"), driver.buttons.LT));
    		this.fill(new DataWire(modules.getModule("Climber").getAction("Run"), "Power", driver.triggers.Left));            
    	}
    	/* SpikeLight */
    	{
            this.bind(new Binding(modules.getModule("SpikeLight").getAction("On"), driver.buttons.RT));
    	}
    	
    	/* Calibrate*/
    	{
       		this.bind(new Binding(modules.getModule("Drive").getAction("Calibrate"), driver.buttons.Back));
    	}
    }
}
