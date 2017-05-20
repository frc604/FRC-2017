package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.Victor;

public class Climber extends Module {
    private final Victor motor = new Victor(Ports.CLIMBER_MOTOR);

    public Climber () {
        this.set(new ElasticController() {{
        	addDefault("Idle", new Action() {
        		public void run (ActionData data) {
        			motor.stopMotor();
        		}
        	});
        	
            add("Run Driver", new Action(new FieldMap () {{
                define("Power", 0D);
            }}) {
                public void run (ActionData data) {
                    motor.set(data.get("Power"));
                }

                public void end (ActionData data){
                    motor.stopMotor();
                } 
            });
            
            add("Run Override", new Action(new FieldMap () {{
                define("Power", 0D);
            }}) {
                public void run (ActionData data) {
                    motor.set(data.get("Power"));
                }

                public void end (ActionData data){
                    motor.stopMotor();
                } 
            });
        }});
    }
}