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
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", 2800));
                            }
                        }));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Testing 1"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", 660));
                            }
                        }));
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe 2"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("NorthEast")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		
                		System.out.println("Finished rotating");
                		System.out.print("Gyro is now measuring: ");
                		System.out.println(modules.getModule("Drive").getData("Horizontal Gyro Angle").get());
                		
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS_ALT));
                            }
                        }));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Mid Step"), new Coordinator() {
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
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                            }
                        }));
                		/*
                		step("Ultra S2", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Past Ultra Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                    	*/
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Right After"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS/2));
                            }
                        }));
                		step("Ultra S2", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Past Ultra Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                		step("Wait", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
                    		}
                    	}));
                		step("Backward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.BKWD_CLICKS));
                            }
                        }));
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("East")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS*2));
                            }
                        }));
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("North")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Long Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                            }
                        }));	
                }}));
                group(new Group(modules.getModule("Dashboard").getTrigger("Left After"), new Coordinator() {
                	protected void apply(ModuleManager modules) {
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS/2));
                            }
                        }));
                		step("Ultra S2", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Past Ultra Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                		step("Wait", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Timer Setpoint")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
                    		}
                    	}));
                		step("Backward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.BKWD_CLICKS));
                            }
                        }));
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("West")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS*2));
                            }
                        }));
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("North")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Long Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.LONG_FWD_CLICKS));
                            }
                        }));	
                }}));
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
                                        "Clicks", Calibration.FWD_CLICKS_ALT));
                            }
                        }));
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("NorthEast")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		
                		
                		/*
                		step("Align", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Aligned")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Align Left")));
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
                            }
                        }));
                		/*
                		step("Ultra S2", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Past Ultra Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                    	*/
                		/*
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS_ALT));
                            }
                        }));
                        */
                	}
                }));
                
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
                                        "Clicks", Calibration.FWD_CLICKS_ALT));
                            }
                        }));
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("NorthWest")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		/*
                		step("Align", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Aligned")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Align Right")));
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
                            }
                        }));
                		
                    	/*
                		step("Ultra S2", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Past Ultra Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Ultra Straight 2")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Ultra Straight 2"), "inches", Calibration.ULTRA_TARGET));
                    		}
                    	}));
                    	*/
                    	/*
                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
                                modules.getModule("Drive").getTrigger("At Move Servo Target")
                        )), new Coordinator() {
                            protected void apply (ModuleManager modules) {
                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks", Calibration.FWD_CLICKS_ALT));
                            }
                        }));
                        */
                	}
                }));
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
