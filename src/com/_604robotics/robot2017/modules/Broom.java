package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Broom extends Module {
    private final DoubleSolenoid solenoid;
    private boolean isExtended = false;

    public Broom(int forwardSolenoid, int reverseSolenoid) {
        this.solenoid = new DoubleSolenoid(forwardSolenoid, reverseSolenoid);
        
        this.set(new TriggerMap() {{
        	add("Extended", () -> isExtended);
        }});
        
        this.set(new ElasticController() {{
            addDefault("Retract", new Action() {
                @Override
                public void begin (ActionData data) {
                	isExtended = false;
                    solenoid.set(Value.kReverse);
                }
            });

            add("Extend", new Action() {
                @Override
                public void begin (ActionData data) {
                	isExtended = true;
                    solenoid.set(Value.kForward);
                }
            });
        }});
    }
}