package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;

public class Intake extends Module {
	private Timer timer = new Timer();
	
	public Intake() {
		
		this.set(new ElasticController() {{
            addDefault("Off", new Action() {
            	public void begin (ActionData data) {
            		
            	}
            	public void run (ActionData data) {
            		
            	}
				public void end (ActionData data) {
	
				}
            });

            add("Run", new Action() {
                @Override
                public void run (ActionData data) {
                	//TODO: run motor
                }
            });
        }});
	}
}
