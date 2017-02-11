package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shifter extends Module {
    private final DoubleSolenoid solenoid = new DoubleSolenoid(Ports.SHIFTER_SOLENOID_FORWARD, Ports.SHIFTER_SOLENOID_REVERSE);

    public Shifter() {
        this.set(new ElasticController() {{
            addDefault("Low Gear", new Action() {
                @Override
                public void begin (ActionData data) {
                    solenoid.set(Calibration.SHIFTER_LOW_GEAR);
                }
            });

            add("High Gear", new Action() {
                @Override
                public void begin (ActionData data) {
                    solenoid.set(Calibration.SHIFTER_HIGH_GEAR);
                }
            });
        }});
    }
}