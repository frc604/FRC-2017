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
	/*  BIG IMPORTANT NOTE  */
	/*-=-=-=-=-=-=-=-=-=-=-*/
    /* ENCODER VALUES ARE REVERSED */
	/* so yeah put values in negative pls */
	protected void apply (ModuleManager modules) {
    	/* Uncomment below once actual shifter is written */
        //this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear")));
        group(new Group(modules.getModule("Dashboard").getTrigger("Auton On"), new Coordinator() {
            protected void apply (ModuleManager modules) { 
// >>>>>>>> Auton Obstacles Options <<<<<<<< //
                /*
            	group(new Group(modules.getModule("Dashboard").getTrigger("Forward"), new Coordinator() {
                    protected void apply (ModuleManager modules) {
                        step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "ClickLeft", Calibration.FWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "ClickRight", Calibration.FWD_CLICKS));
                            }
                        }));
                    }
                }));
                */
            	
            	group(new Group(modules.getModule("Dashboard").getTrigger("Servo"), new Coordinator() {
                    protected void apply (ModuleManager modules) {
                        step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS));
                            }
                        }));
                    }
                }));
            	/*
                group(new Group(modules.getModule("Dashboard").getTrigger("Backward"), new Coordinator() {
                    protected void apply(ModuleManager modules) {
                    	step("Backward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "ClickLeft", Calibration.BKWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "ClickRight", Calibration.BKWD_CLICKS));
                            }
                        }));
                    }
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Rotate"), new Coordinator() {
                    protected void apply(ModuleManager modules) {
                    	step("Rotate", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                    "ClickLeft", Calibration.ROT_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "ClickRight", -Calibration.ROT_CLICKS));
                            }
                        }));
                    }
                }));
                */
                // WORKS
                group(new Group(modules.getModule("Dashboard").getTrigger("Ultra Oscil"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Moth PID", new Step(new TriggerMeasure(new TriggerNot(TriggerAlways.getInstance())), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Oscil")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Oscil"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                	}
                }));
                // WORKS (only turns right)
                group(new Group(modules.getModule("Dashboard").getTrigger("Ultra Straight 2"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerNot(TriggerAlways.getInstance())), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                	}
                }));
                // WORKS wait this calls tank drive though lemme look into that
                group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Fail Safe", new Step(new TriggerMeasure(new TriggerNot(TriggerAlways.getInstance())), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe 2"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Fail Safe 2", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint LR1")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER2));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Kinematic Rotate"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Turn", new Step(new TriggerMeasure(new TriggerNot(modules.getModule("Drive").getTrigger("Timer Setpoint Rotate"))), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Mid"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Rough Forward", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint M1")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER));
                    		}
                    	}));
                		step("Ultra Forward", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Past Ultra Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                		step("Wait", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint M2")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
                    		}
                    	}));
                		step("Rough Backward", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint M3")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", -Calibration.KINEMATIC_POWER));
                    		}
                    	}));
                		step("Turn Right", new Step(new TriggerMeasure(new TriggerNot(modules.getModule("Drive").getTrigger("Timer Setpoint M4"))), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Rough Forward", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint M5")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER));
                    		}
                    	}));
                		step("Turn Left", new Step(new TriggerMeasure(new TriggerNot(modules.getModule("Drive").getTrigger("Timer Setpoint M6"))), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Long Forward", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint M7")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), "Power", Calibration.KINEMATIC_POWER2));
                    		}
                    	}));
                	}
                }));
                // BROKEN
                /*
                group(new Group(modules.getModule("Dashboard").getTrigger("Orient"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Orient", new Step(new TriggerMeasure(new TriggerNot(TriggerAlways.getInstance())), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Orient")));
                    		}
                    	}));
                	}
                }));*/
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
