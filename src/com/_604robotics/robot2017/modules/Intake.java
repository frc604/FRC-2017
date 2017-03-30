package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.MultiOutput;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Intake extends Module {
	// TODO: ports file
	private Timer timer = new Timer();
	private boolean init;
	private MultiOutput mo = new MultiOutput(new PIDOutput[]{
			new Victor(0), 
			new Victor(1){{setInverted(true);}}
		});
	
	public Intake() {		
		this.set(new ElasticController() {{
            addDefault("Off", new Action() {
            	public void begin (ActionData data) {
            		timer.start();
            	}
            	public void run (ActionData data) {
            		if( !init && timer.get() < 0 ) {
            			mo.set(Calibration.INTAKE_POWER);
            		}
            		else {
            			mo.stopMotor();
            		}
            	}
            	@Override
				public void end (ActionData data) {
					timer.stop();
					timer.reset();
					init = false;
					mo.stopMotor();
				}
            });

            add("Run", new Action() {
                @Override
                public void run (ActionData data) {
                	mo.set(Calibration.INTAKE_POWER);
                }
                @Override
                public void end (ActionData data) {
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
