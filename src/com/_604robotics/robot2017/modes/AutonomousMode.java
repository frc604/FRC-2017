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
import com._604robotics.robotnik.prefabs.trigger.TriggerAlways;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;

public class AutonomousMode extends Coordinator {
	protected void apply (ModuleManager modules) {
    	/* Uncomment below once actual shifter is written */
        //this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear")));
        group(new Group(modules.getModule("Dashboard").getTrigger("Auton On"), new Coordinator() {
            protected void apply (ModuleManager modules) { 
// >>>>>>>> Auton Obstacles Options <<<<<<<< //
                group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Fail Safe", new Step(new TriggerMeasure(new TriggerNot(TriggerAlways.getInstance())), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Time", Calibration.KINEMATIC_TIMEM));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe 2"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Fail Safe 2", new Step(new TriggerMeasure(new TriggerNot(TriggerAlways.getInstance())), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER2));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Time", Calibration.KINEMATIC_TIMELR));
                    		}
                    	}));
                	}
                }));
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
