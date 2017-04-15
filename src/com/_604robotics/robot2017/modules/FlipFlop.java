package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;

public class FlipFlop extends Module {
    //private final DoubleSolenoid solenoid;
    private final Relay pseudoSolenoid;
    private boolean extended;

    public FlipFlop() {
    	pseudoSolenoid = new Relay(Ports.PSEUDO_SOLENOID);
    	extended = false;
        //this.solenoid = new DoubleSolenoid(Ports.FLIPFLOP_SOLENOID_FORWARD,Ports.FLIPFLOP_SOLENOID_REVERSE);
        
        this.set(new TriggerMap() {{
        	add("Extended", () -> extended);
        }});
        
        this.set(new StateController() {{
            addDefault("Retract", new Action() {
                @Override
                public void begin (ActionData data) {
                    //solenoid.set(Value.kReverse);
                    pseudoSolenoid.set(Relay.Value.kOff);
                	extended = false;
                }
            });

            add("Extend", new Action() {
                @Override
                public void begin (ActionData data) {
                    //solenoid.set(Value.kForward);
                	pseudoSolenoid.set(Relay.Value.kOn);
                    extended = true;
                }
            });
        }});
    }
}