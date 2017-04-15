/* 
    Autonomous Mode Macros Needed:
	- Options for each defense
	- Options for location will be needed if we plan on shooting
 */

package com._604robotics.robot2017.modes;

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
    	/* Uncomment below once actual shifter is written */
        //this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear")));
        group(new Group(modules.getModule("Dashboard").getTrigger("Auton On"), new Coordinator() {
            protected void apply (ModuleManager modules) { 
// >>>>>>>> SO Auton Obstacles Options <<<<<<<< //
            	group(new Group(modules.getModule("Dashboard").getTrigger("Center Peg"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Kinematic Forward", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Power", modules.getModule("Dashboard").getData("Forward Power")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Time", modules.getModule("Dashboard").getData("Forward Time")));
                			}
                		}));
            		}
            	}));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Left Peg"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Kinematic Forward", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Power", modules.getModule("Dashboard").getData("Forward Power")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Time", modules.getModule("Dashboard").getData("Forward Time")));
                			}
                		}));
            			step("Kinematic Turn", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), 
                                        "Power", 0.7));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"),
                                		"Time", modules.getModule("Dashboard").getData("Turning Time")));
                			}
                		}));
            			step("Kinematic Forward", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Power", modules.getModule("Dashboard").getData("Forward Power")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Time", modules.getModule("Dashboard").getData("Forward Time")));
                			}
                		}));
            		}
            	}));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Right Peg"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Kinematic Forward", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Power", modules.getModule("Dashboard").getData("Forward Power")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Time", modules.getModule("Dashboard").getData("Forward Time")));
                			}
                		}));
            			step("Kinematic Turn", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), 
                                        "Power", -0.7));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"),
                                		"Time", modules.getModule("Dashboard").getData("Turning Time")));
                			}
                		}));
            			step("Kinematic Forward", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Power", modules.getModule("Dashboard").getData("Forward Power")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Time", modules.getModule("Dashboard").getData("Forward Time")));
                			}
                		}));
            		}
            	}));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Turn Right"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Kinematic Turn", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                                this.fill(new DataWire( modules.getModule("Drive").getAction("Kinematic Rotate"), 
                                        "Power", -0.7));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"),
                                		"Time", modules.getModule("Dashboard").getData("Turning Time")));
                			}
                		}));
            		}
            	}));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Turn Left"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Kinematic Turn", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), 
                                        "Power", 0.7));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"),
                                		"Time", modules.getModule("Dashboard").getData("Turning Time")));
                			}
                		}));
            		}
            	}));
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
