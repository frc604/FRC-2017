/* 
    Autonomous Mode Macros Needed:
	- Options for each defense
	- Options for location will be needed if we plan on shooting
 */

package com._604robotics.robot2017.modes;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.coordinator.groups.Group;
import com._604robotics.robotnik.coordinator.steps.Step;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.measure.TriggerMeasure;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;

public class AutonomousMode extends Coordinator {
    protected void apply (ModuleManager modules) {
        this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear")));
        group(new Group(modules.getModule("Dashboard").getTrigger("Auton On"), new Coordinator() {
            protected void apply (ModuleManager modules) { 
// >>>>>>>> Auton Obstacles Options <<<<<<<< //
                group(new Group(modules.getModule("Dashboard").getTrigger("Option 1"), new Coordinator() {
                    protected void apply (ModuleManager modules) {
                        step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", 0 /* Calibrated auton value */));
                            }
                        }));
                    }
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Option 2"), new Coordinator() {
                    protected void apply(ModuleManager modules) {
                    	step("Backward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", 0 /* Calibrated auton value */));
                            }
                        }));
                    }
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Option 3"), new Coordinator() {
                    protected void apply(ModuleManager modules) {
                        
                    }
                }));
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
