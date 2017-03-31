package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.MultiOutput;
import com._604robotics.robotnik.prefabs.trigger.SimpleTriggerMap;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Intake extends Module {
	// TODO: ports file
	private Timer timer = new Timer();
	private Timer timer2 = new Timer();
	private boolean gearIn;
	private boolean gearInNotif;
	private boolean init;
	private boolean running;
	private MultiOutput mo = new MultiOutput(new PIDOutput[]{
			new Victor(Calibration.INTAKE_FORWARD_MOTOR), 
			new Victor(Calibration.INTAKE_REVERSE_MOTOR){{setInverted(true);}}
		});
	
	private DigitalInput boop1 = new DigitalInput(1); // not real
	private DigitalInput boop2 = new DigitalInput(2); // also not real
	
	private SimpleTriggerMap stm;
	
	public Intake() {	
		stm = new SimpleTriggerMap();
		stm.add("Rumble");
		gearIn = false;
		gearInNotif = false;
		running = false;
		this.set(new TriggerMap() {{
			add("Running", () -> running);
			add("Rumble", stm.getTrigger("Rumble"));
		}});
		this.set(new StateController() {{
            addDefault("Off", new Action() {
            	public void begin (ActionData data) {
            		timer.start();
            	}
            	public void run (ActionData data) {
            		if( !init && timer.get() < 0.25 ) {
            			running = true;
            			mo.set(-Calibration.INTAKE_POWER);
            		}
            		else {
            			running = false;
            			mo.stopMotor();
            		}
            	}
            	@Override
				public void end (ActionData data) {
					timer.stop();
					timer.reset();
					init = false;
					running = false;
					mo.stopMotor();
				}
            });

            add("Forward", new Action() {
                @Override
                public void run (ActionData data) {
                	running = true;
                	if( !boop1.get() && !boop2.get() ) {
                		mo.set(-Calibration.INTAKE_POWER);
                		gearInNotif = false;
                		gearIn = false;
                		timer2.reset();
                		timer2.stop();
                	}
                	else {
                		if( !gearIn ) {
                			gearIn = true;
                			timer2.start();
                		}
                		else if( timer2.get() < 0.5 ) {
                			gearInNotif = true;
                		}
                		else {
                			gearInNotif = false;
                		}
                	}
                }
                @Override
                public void end (ActionData data) {
                	running = true;
                	mo.stopMotor();
                }
            });
            
            add("Reverse", new Action() {
                @Override
                public void run (ActionData data) {
                	running = true;
                	mo.set(Calibration.INTAKE_POWER);
                }
                @Override
                public void end (ActionData data) {
                	running = false;
                	mo.stopMotor();
                }
            });
        }});
	}
	@Override
	protected void begin() {
		init = true;
	}
}
