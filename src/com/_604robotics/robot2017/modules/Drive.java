package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.UltrasonicPair;
import com._604robotics.robotnik.prefabs.devices.AnalogUltrasonic;
import com._604robotics.robotnik.prefabs.devices.ArcadeDrivePIDOutput;
import com._604robotics.robotnik.prefabs.devices.TankDrivePIDOutput;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Module {
    // 19.6 to 18.6 inches per 100 ticks
    // -490/490 is 360 degrees with both wheels driving, 115 is 90 degrees

    // Locking a side: put it 18 in the opposite direction
    // 430 is 180 degrees with one side locked

    // When decreasing angle it needs a little bit less than you'd think
	    
	private final RobotDrive drive = new RobotDrive(
            Ports.DRIVE_FRONT_LEFT_MOTOR,
            Ports.DRIVE_REAR_LEFT_MOTOR,
            Ports.DRIVE_FRONT_RIGHT_MOTOR,
            Ports.DRIVE_REAR_RIGHT_MOTOR);
	
	//private final RobotDrive drive = new RobotDrive(1, 3, 6, 8);
	//private final RobotDrive extraAf = new RobotDrive(2, 4, 5, 7);			
	
    private final Encoder encoderLeft = new Encoder(
            Ports.DRIVE_ENCODER_LEFT_A,
            Ports.DRIVE_ENCODER_LEFT_B,
            true, CounterBase.EncodingType.k4X);
    private final Encoder encoderRight = new Encoder(
            Ports.DRIVE_ENCODER_RIGHT_A,
            Ports.DRIVE_ENCODER_RIGHT_B,
            false, CounterBase.EncodingType.k4X);

    /*
    private final TankDrivePIDOutput pidOutput = new TankDrivePIDOutput(drive);
    private final PIDController pidLeft = new PIDController(
            Calibration.DRIVE_LEFT_PID_P,
            Calibration.DRIVE_LEFT_PID_I,
            Calibration.DRIVE_LEFT_PID_D,
            encoderLeft,
            pidOutput.left);
    private final PIDController pidRight = new PIDController(
            Calibration.DRIVE_RIGHT_PID_P,
            Calibration.DRIVE_RIGHT_PID_I,
            Calibration.DRIVE_RIGHT_PID_D,
            encoderRight,
            pidOutput.right);
    */
    private final ArcadeDrivePIDOutput pidOutput = new ArcadeDrivePIDOutput(drive);
    private final PIDController pidMove = new PIDController(
    		Calibration.DRIVE_LEFT_PID_P,
    		Calibration.DRIVE_LEFT_PID_I,
    		Calibration.DRIVE_LEFT_PID_D,
    		encoderLeft,
    		pidOutput.move);
    
    //private final AnalogGyro horizGyro = new AnalogGyro(Ports.HORIZGYRO);
    //private final AnalogUltrasonic ultra = new AnalogUltrasonic(0);
    private final UltrasonicPair ultra = new UltrasonicPair(new AnalogUltrasonic(Ports.ULTRASONIC_LEFT), new AnalogUltrasonic(Ports.ULTRASONIC_RIGHT), Calibration.ULTRA_SEPARATION);
    
    private final Timer timer = new Timer();
    
    /*
    private double pid_power_cap = 0.6;
    private double PIDUltraOut = 0D;
    private final PIDController pidUltra = new PIDController(Calibration.DRIVE_ULTRA_PID_P, 
    		Calibration.DRIVE_ULTRA_PID_I, Calibration.DRIVE_LEFT_PID_D, null, new PIDOutput () {
    	public void pidWrite (double output) {
    		if( output > 0 ) PIDUltraOut = (output > pid_power_cap) ? pid_power_cap : output;
    		else PIDUltraOut = (output < -pid_power_cap) ? -pid_power_cap : output;
    	}
    });
    */

    public Drive () {
        encoderLeft.setPIDSourceType(PIDSourceType.kDisplacement);
        encoderRight.setPIDSourceType(PIDSourceType.kDisplacement);
        /*
        pidLeft.setOutputRange(-Calibration.DRIVE_LEFT_PID_MAX, Calibration.DRIVE_LEFT_PID_MAX);
        pidRight.setOutputRange(-Calibration.DRIVE_RIGHT_PID_MAX, Calibration.DRIVE_RIGHT_PID_MAX);
        pidLeft.setAbsoluteTolerance(Calibration.DRIVE_LEFT_PID_TOLERANCE);
        pidRight.setAbsoluteTolerance(Calibration.DRIVE_RIGHT_PID_TOLERANCE);
		*/
        pidMove.setOutputRange(-Calibration.DRIVE_LEFT_PID_MAX, Calibration.DRIVE_LEFT_PID_MAX);
        pidMove.setAbsoluteTolerance(Calibration.DRIVE_LEFT_PID_TOLERANCE);

        /*
        SmartDashboard.putData("Drive Move PID", pidLeft);
        SmartDashboard.putData("Drive Rotate PID", pidRight);
         */
        this.set(new DataMap() {{
            add("Left Drive Clicks", encoderLeft::get);
            add("Right Drive Clicks", encoderRight::get);

            add("Left Drive Rate", encoderLeft::getRate);
            add("Right Drive Rate", encoderRight::getRate);

            //add("Move PID Error", pidLeft::getAvgError);
            //add("Rotate PID Error", pidRight::getAvgError);

            add("Ultra Inches", ultra::getDistance);
            add("Ultra Angle", ultra::getAngle);
            add("Ultra Difference", ultra::getDifference);
            
            add("Ultra Left", ultra::getLeftDistance);
            add("Ultra Right", ultra::getRightDistance);
            
            //add("Horizonal Gyro Angle", horizGyro::getAngle);
        }});

        this.set(new TriggerMap() {{
            //add("At Move Servo Target", () -> pidLeft.isEnabled() && pidLeft.onTarget());
            add("At Move Servo Target", () -> pidMove.isEnabled() && pidMove.onTarget());
            add("Past Ultra Target", () -> ultra.getDistance() < Calibration.ULTRA_TARGET);
        }});

        this.set(new ElasticController() {{
            addDefault("Off", new Action() {
                public void run (ActionData data) {
                    drive.tankDrive(0, 0);
                }
            });
            
            add("Tank Drive", new Action(new FieldMap () {{
                define("Left Power", 0D);
                define("Right Power", 0D);
            }}) {
                public void run (ActionData data) {
                    // double throttle = data.is("Throttled") ? 0.5 : 1;
                	drive.tankDrive(data.get("Left Power"), data.get("Right Power"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            
            add("Kinematic Drive", new Action(new FieldMap () {{
                define("Time", 0D);
                define("Power", 0D);
            }}) {
            	public void begin(ActionData data) {
            		timer.reset();
            		timer.start();
            	}
                public void run (ActionData data) {
                	double time = timer.get();
                	if( time < data.get("Time") )
                	{
                		drive.tankDrive(data.get("Power"), data.get("Power"), false);
                	}
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                    timer.stop();
                    timer.reset();
                }
            });
            
            add("Arcade Drive", new Action(new FieldMap () {{
                define("Move Power", 0D);
                define("Rotate Power", 0D);
            }}) {
                public void run (ActionData data) {
                    // double throttle = data.is("Throttled") ? 0.5 : 1;
//                	System.out.print("Move");
//                	System.out.println(data.get("Move Power"));
//                	System.out.print("Rotate");
//                	System.out.println(data.get("Rotate Power"));
                	drive.arcadeDrive(data.get("Move Power"), data.get("Rotate Power"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            
            add("Test Drive", new Action(new FieldMap () {{
                define("Move Power", 0D);
                define("Rotate Power", 0D);
            }}) {
                public void run (ActionData data) {
                    // double throttle = data.is("Throttled") ? 0.5 : 1;
                	drive.arcadeDrive(data.get("Move Power"), data.get("Rotate Power"));
                	//extraAf.arcadeDrive(data.get("Move Power"), data.get("Rotate Power"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                    //extraAf.stopMotor();
                }
            });
            
            add("Servo Move", new Action(new FieldMap() {{
                define("Clicks", 0D);
            }}) {
                public void begin (ActionData data) {
                    encoderLeft.reset();
                    encoderRight.reset();
                    pidMove.setSetpoint(data.get("Clicks"));
                    pidMove.enable();
                }

                public void run (ActionData data){
                    if (pidMove.getSetpoint() != data.get("Clicks")) {
                        pidMove.reset();
                        encoderLeft.reset();
                        encoderRight.reset();
                        
                        pidMove.setSetpoint(data.get("Clicks"));
                        pidMove.enable();
                    }

                }

                public void end (ActionData data) {
                    pidMove.reset();
                }
            });
            /*
            add("Servo Move", new Action(new FieldMap() {{
                define("ClickLeft", 0D);
                define("ClickRight",0D);
            }}) {
                public void begin (ActionData data) {
                    encoderLeft.reset();
                    encoderRight.reset();
//                    pidOutput.left.pidWrite(0);
//                    pidOutput.right.pidWrite(0);
                    pidLeft.setSetpoint(data.get("ClickLeft"));
                    pidLeft.enable();
                    pidRight.setSetpoint(data.get("ClickRight"));
                    pidRight.enable();
                }

                public void run (ActionData data){
                    if (encoderLeft.get() != data.get("ClickLeft")) {
                        pidLeft.reset();
                        encoderLeft.reset();
                        
                        pidLeft.setSetpoint(data.get("ClickLeft"));
                        pidLeft.enable();
                    }
                    
                    if (encoderRight.get() != data.get("ClickRight")) {
                        pidRight.reset();
                        encoderRight.reset();
                        
                        pidRight.setSetpoint(data.get("ClickRight"));
                        pidRight.enable();
                    }
                }

                public void end (ActionData data) {
                    pidLeft.reset();
                    pidRight.reset();
                }
            });
            */
            add("Ultra Orient", new Action() {
            	public void run (ActionData data) {
            		if( ultra.inRange() )
            			{
            			double angle = ultra.getAngle(1);
            			double power = 0;
            			if (angle > 30) {
            				power = 0.5;
            			}
            			else if (angle > 15) {
            				power = 0.4;
            			}
            			else if (angle > 7) {
            				power = 0.3;
            			}
            			else if (angle > 3.5) {
            				power = 0.2;
            			}
            			power *= Math.signum(angle);
            			if(power != 0) {
	                	    drive.tankDrive(power, -power, false);
	                	} else {
	                	    drive.stopMotor();
	                	}
            		}
            	}
            	public void end (ActionData data) {
            		drive.stopMotor();
            	}
            });
            
            /* add("Ultra Match", new Action() {
            	public void run (ActionData data){
            		if (ultra.getAngle(1) > horizGyro.getAngle() + 7) {
            			drive.tankDrive(-0.15, 0.15);
            		}
            		
            		if (ultra.getAngle(1) < horizGyro.getAngle() - 7) {
            			drive.tankDrive(0.15, -0.15);
            		}
            	}
            }); */
            
            add("Ultra Oscil", new Action(new FieldMap() {{
                define("inches", 0D);
            }}) {
                public void run (ActionData data){
                    if(ultra.inRange()) {
	                	double displacement = ultra.getDistance(1) - data.get("inches");
	                	double distance = Math.abs(displacement);
	                	
	                	double power = 0;
	                	if (distance > 48) {
	                	    power = 0.5;
	                	} else if (distance > 24) {
	                		power = 0.4;
	                	} else if (distance > 12) {
	                		power = 0.3;
	                	} else if (distance > 6) {
	                		power = 0.25;
	                	} else if (distance > 3) {
	                		power = 0.2;
	                	}
	                	
	                	power *= Math.signum(displacement);
	                	
	                	if(power != 0) {
	                	    drive.tankDrive(power, power, false);
	                	} else {
	                	    drive.stopMotor();
	                	}
                    }
                    else
                    {
                    	drive.stopMotor();
                    }
                }
                
                public void end (ActionData data) {
                	drive.stopMotor();
                }
            });
            add("Ultra Crude", new Action(new FieldMap() {{
                define("inches", 0D);
            }}) {
                public void run (ActionData data){
                    if(ultra.inRange()) {
	                	double distance = ultra.getDistance();
	                	if( distance > data.get("inches") )
	                	{
	                		drive.tankDrive(0.5, 0.5, false);
	                	}
                    }
                    else
                    {
                    	drive.stopMotor();
                    }
                }
                
                public void end (ActionData data) {
                	drive.stopMotor();
                }
            });
            
            add("Ultra Align", new Action(new FieldMap() {{
            }}) {
                public void run (ActionData data){
                    if(ultra.inRange()) {
	                	double difference = ultra.getDifference(1);
	                	
	                	if( difference > 1 ) {
	                		drive.tankDrive(-0.3, 0.3, false);
	                	}
	                	else if( difference < -1 ) {
	                		drive.tankDrive(0.3, -0.3, false);
	                	}
	                	else {
	                		drive.stopMotor();
	                	}
                    }
                }
                public void end (ActionData data) {
                	drive.stopMotor();
                }
            });
            add("Ultra Straight", new Action(new FieldMap() {{
                define("inches", 0D);
            }}) {
                public void run (ActionData data){
                    if(ultra.inRange()) {
	                	double displacement = ultra.getDistance(1) - data.get("inches");
	                	double distance = Math.abs(displacement);
	                	double difference = ultra.getDifference(1);
	                	
	                	if( difference > 3 ) {
	                		drive.tankDrive(-0.4, 0.4, false);
	                	}
	                	else if( difference < -3 ) {
	                		drive.tankDrive(0.4, -0.4, false);
	                	}
	                	else {
	                		double power = 0;
	                		if (distance > 24) {
	                			power = 0.5;
	                		} else if (distance > 24) {
	                			power = 0.4;
	                		} else if (distance > 12) {
	                			power = 0.3;
	                		} else if (distance > 6) {
	                			power = 0.25;
	                		} else if (distance > 3) {
	                			power = 0.2;
	                		}
	                	
	                		power *= Math.signum(displacement);
	                	
	                		if(power != 0) {
	                			drive.tankDrive(power, power, false);
	                		} else {
	                			drive.stopMotor();
	                		}
	                	}
                    }
                }
                public void end (ActionData data) {
                	drive.stopMotor();
                }
            });
            add("Ultra Straight 2", new Action(new FieldMap() {{
                define("inches", 0D);
            }}) {
                public void run (ActionData data){
                    if(ultra.inRange()) {
	                	double leftDisplacement = ultra.getLeftDistance(1) - data.get("inches");
	                	double leftDistance = Math.abs(leftDisplacement);
	                	double rightDisplacement = ultra.getRightDistance(1) - data.get("inches");
	                	double rightDistance = Math.abs(rightDisplacement);
	                	
	                	double leftPower = 0;
	                	if (leftDistance > 24) {
	                		leftPower = 0.5;
	               		} else if (leftDistance > 12) {
	               			leftPower = 0.4;
	               		} else if (leftDistance > 6) {
	               			leftPower = 0.3;
	               		} else if (leftDistance > 3) {
	               			leftPower = 0.25;
	               		} else if (leftDistance > 1) {
	               			leftPower = 0.2;
	               		}
	                	leftPower *= Math.signum(leftDisplacement);
	                	
	                	double rightPower = 0;
	                	if (rightDistance > 24) {
	                		rightPower = 0.5;
	               		} else if (rightDistance > 12) {
	               			rightPower = 0.4;
	               		} else if (rightDistance > 6) {
	               			rightPower = 0.3;
	               		} else if (rightDistance > 3) {
	               			rightPower = 0.25;
	               		} else if (rightDistance > 1) {
	               			rightPower = 0.2;
	               		}
	                	rightPower *= Math.signum(rightDisplacement);
	                	
	                	if( leftPower != 0 && rightPower != 0 )
	                	{
	                		drive.tankDrive(leftPower, rightPower, false);
	                	}
	                	else
	                	{
	                		drive.stopMotor();
	                	}
                    }    
                }
                public void end (ActionData data) {
                	drive.stopMotor();
                }
            });
        }});
    }
}