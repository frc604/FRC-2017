package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.Solenoid;

public class Shifter extends Module {
	private final Solenoid shifter = new Solenoid(Ports.SHIFTER);

    public Shifter (){
        this.set(new ElasticController() {{
        	addDefault("Low Gear", new Action() {
        		public void begin (ActionData data) {
        			shifter.set(false);
        		}
        	});
        	
            add("High Gear", new Action() {
                public void begin (ActionData data) {
                	shifter.set(true);
                }
            });
        }});
    }
}