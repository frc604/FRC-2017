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
	    bind(new Binding(modules.getModule("Vision").getAction("Record")));
	    
    	/* Uncomment below once actual shifter is written */
        this.bind(new Binding(modules.getModule("GearShifter").getAction("Low Gear")));
        group(new Group(modules.getModule("Dashboard").getTrigger("Auton On"), new Coordinator() {
            protected void apply (ModuleManager modules) { 
// >>>>>>>> Auton Obstacles Options <<<<<<<< //
            	group(new Group(modules.getModule("Dashboard").getTrigger("Cal One"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.CAL_ONE));
                            }
                        }));
                	}
                }));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Cal Two"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.CAL_TWO));
                            }
                        }));
                	}
                }));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Cal Three"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.CAL_THREE));
                            }
                        }));
                	}
                }));
            	
            	group(new Group(modules.getModule("Dashboard").getTrigger("Calibrated"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                            	
                            	double target = modules.getModule("Dashboard").getData("Target").get();
                            	double ca1 = modules.getModule("Dashboard").getData("Cal One Dist").get();
                            	double ca2 = modules.getModule("Dashboard").getData("Cal Two Dist").get();
                            	double ca3 = modules.getModule("Dashboard").getData("Cal Three Dist").get();
                            	double clicks = (target / (((ca2-ca1)/200 + (ca3-ca2)/200) / 2));
                                
                            	this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", clicks));
                            }
                        }));
                	}
                }));           	            	
            	            	
            	group(new Group(modules.getModule("Dashboard").getTrigger("Testing"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("PID Turn", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Rotate Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Rotate")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Rotate"), 
                                        "Angle", modules.getModule("Dashboard").getData("Testing Angle")));
                            }
                        }));
                	}
            	}));
            	group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", 2800));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Limit", Math.sqrt(0.8)));
                            }
                        }));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Testing 1"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.ROTATE_TURN_INIT_FOWARD));
                            }
                        }));
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("At Move Servo Target")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.ROTATE_TURN_FINAL_FOWARD));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                			}
                		}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Mid Step"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		
                		/*
                		step("Wall Align", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                				this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                						"Power", Math.sqrt(0.8)));
                				this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                						"Time", 1D));
                			}
                		}));
                		*/
                		
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                            }
                        }));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Wiggle Mid"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                            }
                        }));
                		step("Wait", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("Timer Setpoint")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
                            }
                        }));
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Wiggle Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Wait", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("Timer Setpoint")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
                            }
                        }));
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Wiggle Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                	}
                }));
                /*
                group(new Group(modules.getModule("Dashboard").getTrigger("Two Gear Auto"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		
                		step("Calibrate", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("North")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Calibrate")));
                			}
                		}));
                		
                		//
                		step("Wall Align", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Setpoint")
                		)), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                				this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                						"Power", Math.sqrt(0.8)));
                				this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                						"Time", 1D));
                			}
                		}));
                		//
                		
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(1.0)));
                            }
                        }));
                		
                		step("Spit", new Step(new TriggerMeasure(new TriggerAnd (
                				modules.getModule("FlipFlop").getTrigger("Extended")
                		)), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("FlipFlop").getAction("Extend")));
                			}
                		}));
                		
                		step("Backward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", -Calibration.LONG_FWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(1.0)));
                            }
                        }));
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("East")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		
                		step("Roller On", new Step(new TriggerMeasure(modules.getModule("Intake").getTrigger("Running")), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Intake").getAction("Forward")));
                			}
                		}));
                		
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", 480));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                            }
                        }));
                		
                		step("Backward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", -480));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                            }
                        }));
                		
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("North")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		

                		step("Pickup Up", new Step(new TriggerMeasure(new TriggerNot(modules.getModule("FlipFlop").getTrigger("Extended"))), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("FlipFlop").getAction("Retract")));
                			}
                		}));
                		
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", 1.0));
                            }
                        }));
                	}
                }));
                */
                group(new Group(modules.getModule("Dashboard").getTrigger("Right Step"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Calibrate", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("North")
                		)), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Calibrate")));
                			}
                		}));
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.ROTATE_TURN_INIT_FOWARD));
                            }
                        }));
                		
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("At Move Servo Target")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.ROTATE_TURN_FINAL_FOWARD));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                			}
                		}));
                	}
                }));
                
                group(new Group(modules.getModule("Dashboard").getTrigger("Left Step"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Calibrate", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("North")
                		)), new Coordinator() {
                			protected void apply (ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Calibrate")));
                			}
                		}));
                		
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.ROTATE_TURN_INIT_FOWARD));
                            }
                        }));
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("At Move Servo Target")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.ROTATE_TURN_FINAL_FOWARD));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"),
                                		"Limit", Math.sqrt(0.8)));
                			}
                		}));
                	}
                }));
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
