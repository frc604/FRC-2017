package com._604robotics.robotnik.prefabs.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class GearShifter extends Module {
    private final DoubleSolenoid solenoid;

    public GearShifter(int forwardSolenoid, int reverseSolenoid) {
        this.solenoid = new DoubleSolenoid(forwardSolenoid, reverseSolenoid);
        
        this.set(new ElasticController() {{
            addDefault("Low Gear", new Action() {
                @Override
                public void begin (ActionData data) {
                    solenoid.set(Value.kReverse);
                }
            });

            add("High Gear", new Action() {
                @Override
                public void begin (ActionData data) {
                    solenoid.set(Value.kForward);
                }
            });
        }});
    }
}