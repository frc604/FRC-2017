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
import com._604robotics.robotnik.prefabs.measure.TimeMeasure;
import com._604robotics.robotnik.prefabs.measure.TriggerMeasure;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;

public class AutonomousMode extends Coordinator {
	protected void apply (ModuleManager modules) {
    	/* Uncomment below once actual shifter is written */
        this.bind(new Binding(modules.getModule("GearShifter").getAction("Low Gear")));
        group(new Group(modules.getModule("Dashboard").getTrigger("Auton On"), new Coordinator() {
            protected void apply (ModuleManager modules) { 
// >>>>>>>> Auton Obstacles Options <<<<<<<< //
            	/*
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
                            	this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
                                        "Clicks",950));
                            }
                        }));
                	}
                }));           	            	
            	*/
//            	group(new Group(modules.getModule("Dashboard").getTrigger("Testing"), new Coordinator() {
//                	protected void apply(ModuleManager modules) {
//                		step("PID Turn", new Step(new TriggerMeasure(new TriggerAnd(
//                                modules.getModule("Drive").getTrigger("At Rotate Servo Target")
//                        )), new Coordinator() {
//                            protected void apply (ModuleManager modules) {
//                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Rotate")));
//                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Rotate"), 
//                                        "Angle", modules.getModule("Dashboard").getData("Testing Angle")));
//                            }
//                        }));
//                	}
//            	}));
            	
//            	group(new Group(modules.getModule("Dashboard").getTrigger("Kinematic Rotate"), new Coordinator() {
//                	protected void apply(ModuleManager modules) {
//                		step("PID Turn", new Step(new TriggerMeasure(new TriggerAnd(
//                                modules.getModule("Drive").getTrigger("Timer Setpoint")
//                        )), new Coordinator() {
//                            protected void apply (ModuleManager modules) {
//                                this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Rotate")));
//                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), 
//                                        "Power", modules.getModule("Dashboard").getData("Rotate Power")));
//                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Rotate"), 
//                                        "Time", modules.getModule("Dashboard").getData("Rotate Time")));
//                            }
//                        }));
//                	}
//            	}));
            	
            	group(new Group(modules.getModule("Dashboard").getTrigger("Testing"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Rotate Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Target")), new Coordinator() {
            				protected void apply (ModuleManager modules) {
            					this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
            					this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"),
            							"Power", Calibration.ROTATE_POWER));
            				}
            			}));
            		}
            	}));
            	
            	group(new Group(modules.getModule("Dashboard").getTrigger("Fail Safe"), new Coordinator() {
            		protected void apply(ModuleManager modules) {
            			step("Forward", new Step(new TimeMeasure(Calibration.FAIL_SAFE_TIME), new Coordinator() {
            				protected void apply (ModuleManager modules) {
            					this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive")));
            					this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"),
            							"Left Power", Calibration.FAIL_SAFE_POWER_LEFT));
            					this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"),
            							"Right Power", Calibration.FAIL_SAFE_POWER_RIGHT));
            				}
            			}));
            		}
            	}));

//                group(new Group(modules.getModule("Dashboard").getTrigger("Testing 1"), new Coordinator() {
//                	protected void apply(ModuleManager modules) {
//                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Target")), new Coordinator() {
//                    		protected void apply (ModuleManager modules) {
//                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
//                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
//                    		}
//                    	}));
//                	}
//                }));
//                group(new Group(modules.getModule("Dashboard").getTrigger("Mid Step"), new Coordinator() {
//                	protected void apply(ModuleManager modules) {
//                		
//                		/*
//                		step("Wall Align", new Step(new TriggerMeasure(new TriggerAnd(
//                				modules.getModule("Drive").getTrigger("Timer Setpoint")
//                		)), new Coordinator() {
//                			protected void apply (ModuleManager modules) {
//                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
//                				this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
//                						"Power", Math.sqrt(0.8)));
//                				this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
//                						"Time", 1D));
//                			}
//                		}));
//                		*/
//                		
//                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
//                                modules.getModule("Drive").getTrigger("At Move Servo Target")
//                        )), new Coordinator() {
//                            protected void apply (ModuleManager modules) {
//                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
//                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
//                                        "Clicks", Calibration.LONG_FWD_CLICKS));
//                            }
//                        }));
//                	}
//                }));
//                group(new Group(modules.getModule("Dashboard").getTrigger("Test Mid"), new Coordinator() {
//                	protected void apply(ModuleManager modules) {
//                		
//                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
//                                modules.getModule("Drive").getTrigger("At Move Servo Target")
//                        )), new Coordinator() {
//                            protected void apply (ModuleManager modules) {
//                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
//                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
//                                        "Clicks", modules.getModule("Dashboard").getData("Testing Clicks")));
//                            }
//                        }));
//                	}
//                }));
//                group(new Group(modules.getModule("Dashboard").getTrigger("Wiggle Mid"), new Coordinator() {
//                	protected void apply(ModuleManager modules) {
//                		step("Forward", new Step(new TriggerMeasure(new TriggerAnd(
//                                modules.getModule("Drive").getTrigger("At Move Servo Target")
//                        )), new Coordinator() {
//                            protected void apply (ModuleManager modules) {
//                                this.bind(new Binding(modules.getModule("Drive").getAction("Servo Move")));
//                                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo Move"), 
//                                        "Clicks", Calibration.LONG_FWD_CLICKS));
//                            }
//                        }));
//                		step("Pause", new Step(new TimeMeasure(modules.getModule("Dashboard").getData("Wiggle Wait").get()), new Coordinator() {
//            				protected void apply (ModuleManager modules) {
//            					this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
//
//            				}
//            			}));
//                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Wiggle Target")), new Coordinator() {
//                    		protected void apply (ModuleManager modules) {
//                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
//                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
//                    		}
//                    	}));
//                		step("Pause", new Step(new TimeMeasure(3), new Coordinator() {
//            				protected void apply (ModuleManager modules) {
//            					this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
//
//            				}
//            			}));
//                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Wiggle Target")), new Coordinator() {
//                    		protected void apply (ModuleManager modules) {
//                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
//                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
//                    		}
//                    	}));
//                	}
//                }));
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
                group(new Group(modules.getModule("Dashboard").getTrigger("Blue Left Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Blue Left Step")));
                            }
                        }));
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		
                		
                		step("Pause", new Step(new TimeMeasure(modules.getModule("Dashboard").getData("Rotate Wait").get()), new Coordinator() {
            				protected void apply (ModuleManager modules) {
            					this.bind(new Binding(modules.getModule("Drive").getAction("Off")));

            				}
            			}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Forward")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Time", 3));
                			}
                		}));
                	}
                }));
                
                group(new Group(modules.getModule("Dashboard").getTrigger("Blue Right Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Blue Right Step")));
                            }
                        }));
                		
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Pause", new Step(new TimeMeasure(modules.getModule("Dashboard").getData("Rotate Wait").get()), new Coordinator() {
            				protected void apply (ModuleManager modules) {
            					this.bind(new Binding(modules.getModule("Drive").getAction("Off")));
            				}
            			}));
                		
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Forward")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Time", 3));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Power", modules.getModule("Dashboard").getData("Sidestep Power 2").get()));
                			}
                		}));
                	}
                }));
                
                group(new Group(modules.getModule("Dashboard").getTrigger("Red Left Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Red Left Step")));
                            }
                        }));
                		
                		step("Turn Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Right Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Right")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Right"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Pause", new Step(new TimeMeasure(modules.getModule("Dashboard").getData("Rotate Wait").get()), new Coordinator() {
            				protected void apply (ModuleManager modules) {
            					this.bind(new Binding(modules.getModule("Drive").getAction("Off")));

            				}
            			}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Forward")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Time", 3));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Power", modules.getModule("Dashboard").getData("Sidestep Power 2").get()));
                			}
                		}));
                	}
                }));
                
                group(new Group(modules.getModule("Dashboard").getTrigger("Red Right Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Red Right Step")));
                            }
                        }));
                		
                		step("Turn Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("Left Target")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Manual Rotate Left")));
                    			this.fill(new DataWire(modules.getModule("Drive").getAction("Manual Rotate Left"), "Power", Calibration.ROTATE_POWER));
                    		}
                    	}));
                		step("Pause", new Step(new TimeMeasure(modules.getModule("Dashboard").getData("Rotate Wait").get()), new Coordinator() {
            				protected void apply (ModuleManager modules) {
            					this.bind(new Binding(modules.getModule("Drive").getAction("Off")));

            				}
            			}));
                		step("Forward Again", new Step(new TriggerMeasure(new TriggerAnd(
                				modules.getModule("Drive").getTrigger("Timer Forward")
                		)), new Coordinator() {
                			protected void apply(ModuleManager modules) {
                				this.bind(new Binding(modules.getModule("Drive").getAction("Kinematic Drive")));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"), 
                                        "Time", 3));
                                this.fill(new DataWire(modules.getModule("Drive").getAction("Kinematic Drive"),
                                		"Power", modules.getModule("Dashboard").getData("Sidestep Power 2").get()));
                			}
                		}));
                	}
                }));
                
                
                
                
                group(new Group(modules.getModule("Dashboard").getTrigger("Backup Red Left Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Red Left Step")));
                            }
                        }));
                		
                		step("Macro Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("FALSE")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Macro Right")));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Backup Red Right Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Red Right Step")));
                            }
                        }));
                		
                		step("Macro Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("FALSE")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Macro Left")));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Backup Blue Left Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Blue Left Step")));
                            }
                        }));
                		
                		step("Macro Right", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("FALSE")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Macro Right")));
                    		}
                    	}));
                	}
                }));
                group(new Group(modules.getModule("Dashboard").getTrigger("Backup Blue Right Step"), new Coordinator() {
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
                                        "Clicks", modules.getModule("Dashboard").getData("Blue Right Step")));
                            }
                        }));
                		
                		step("Macro Left", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("FALSE")), new Coordinator() {
                    		protected void apply (ModuleManager modules) {
                    			this.bind(new Binding(modules.getModule("Drive").getAction("Macro Left")));
                    		}
                    	}));
                	}
                }));
// >>>>>>>> EO Auton Obstacles Options <<<<<<<< //
            }
        }));
    }
}
