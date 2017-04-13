package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.UltrasonicPair;
import com._604robotics.robotnik.prefabs.devices.AnalogUltrasonic;
import com._604robotics.robotnik.prefabs.devices.ArcadeDrivePIDOutput;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class Drive extends Module {
	/* Copied from 2015; could be different */
    // 19.7 clicks per inch
    // -490/490 is 360 degrees with both wheels driving, 115 is 90 degrees

    // Locking a side: put it 18 in the opposite direction
    // 430 is 180 degrees with one side locked

    // When decreasing angle it needs a little bit less than you'd think
	private boolean calibrated = false;
	private boolean reset = false;
	    
	private final RobotDrive drive = new RobotDrive(
            Ports.DRIVE_FRONT_LEFT_MOTOR,
            Ports.DRIVE_REAR_LEFT_MOTOR,
            Ports.DRIVE_FRONT_RIGHT_MOTOR,
            Ports.DRIVE_REAR_RIGHT_MOTOR);

    private final Encoder encoderLeft = new Encoder(
            Ports.DRIVE_ENCODER_LEFT_A,
            Ports.DRIVE_ENCODER_LEFT_B,
            false, CounterBase.EncodingType.k4X);
    private final Encoder encoderRight = new Encoder(
            Ports.DRIVE_ENCODER_RIGHT_A,
            Ports.DRIVE_ENCODER_RIGHT_B,
            true, CounterBase.EncodingType.k4X);

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
    
	private final AnalogGyro horizGyro = new AnalogGyro(Ports.HORIZGYRO);

    private final ArcadeDrivePIDOutput pidOutput = new ArcadeDrivePIDOutput(drive);
    private final PIDController pidMove = new PIDController(
    		Calibration.DRIVE_MOVE_PID_P,
    		Calibration.DRIVE_MOVE_PID_I,
    		Calibration.DRIVE_MOVE_PID_D,
    		encoderLeft,
    		pidOutput.move);
    private final PIDController pidRotate = new PIDController(
            Calibration.DRIVE_ROTATE_PID_P,
            Calibration.DRIVE_ROTATE_PID_I,
            Calibration.DRIVE_ROTATE_PID_D,
            horizGyro,
            pidOutput.rotate);
    
    //private final AnalogGyro horizGyro = new AnalogGyro(Ports.HORIZGYRO);
    //private final AnalogUltrasonic ultra = new AnalogUltrasonic(0);
    
    private final Timer timer = new Timer();
    private double timerWait;
    private boolean timerOn;
    
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
    	timerWait = 0;
    	timerOn = false;
    	{
    		System.out.print("Calibrating Gyro...");
    	}
        horizGyro.calibrate();
        {
        	System.out.println("Done");
        	calibrated = true;
        }

        encoderLeft.setPIDSourceType(PIDSourceType.kDisplacement);
        encoderRight.setPIDSourceType(PIDSourceType.kDisplacement);
        
        horizGyro.setPIDSourceType(PIDSourceType.kDisplacement);
        
        /*
        pidLeft.setOutputRange(-Calibration.DRIVE_LEFT_PID_MAX, Calibration.DRIVE_LEFT_PID_MAX);
        pidRight.setOutputRange(-Calibration.DRIVE_RIGHT_PID_MAX, Calibration.DRIVE_RIGHT_PID_MAX);
        pidLeft.setAbsoluteTolerance(Calibration.DRIVE_LEFT_PID_TOLERANCE);
        pidRight.setAbsoluteTolerance(Calibration.DRIVE_RIGHT_PID_TOLERANCE);
		*/
		
        pidMove.setOutputRange(-Calibration.DRIVE_MOVE_PID_MAX, Calibration.DRIVE_MOVE_PID_MAX);
        pidMove.setAbsoluteTolerance(Calibration.DRIVE_MOVE_PID_TOLERANCE);
        
        pidRotate.setOutputRange(-Calibration.DRIVE_ROTATE_PID_MAX, Calibration.DRIVE_ROTATE_PID_MAX);
        pidRotate.setAbsoluteTolerance(Calibration.DRIVE_ROTATE_PID_TOLERANCE);

        this.set(new DataMap() {{
            add("Left Drive Clicks", encoderLeft::get);
            add("Right Drive Clicks", encoderRight::get);

            add("Left Drive Rate", encoderLeft::getRate);
            add("Right Drive Rate", encoderRight::getRate);

            //add("Left PID Error", pidLeft::getAvgError);
            //add("Right PID Error", pidRight::getAvgError);
            
            add("Move PID Error", pidMove::getAvgError);
            add("Rotate PID Error", pidRotate::getAvgError);
            
            add("Timer Seconds", timer::get);
            
            add("Horizontal Gyro Angle", horizGyro::getAngle);
        }});

        this.set(new TriggerMap() {{
        	add("Gyro Calibrated", () -> calibrated);
        	add("Reset", () -> reset);
        	add("Forward Again", () -> true);
            add("At Move Servo Target", () -> pidMove.isEnabled() && pidMove.onTarget());
            
            add("Timer Setpoint", () -> timer.get() > timerWait && timerOn);
            
            add("At Rotate Servo Target", () -> pidRotate.isEnabled() && pidRotate.onTarget());
            add("North", () -> -Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TOLERANCE);
            add("East", () -> Calibration.ROTATE_TARGET_A - Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TARGET_A + Calibration.ROTATE_TOLERANCE);
            add("West", () -> -Calibration.ROTATE_TARGET_A- Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < -Calibration.ROTATE_TARGET_A + Calibration.ROTATE_TOLERANCE);
            add("South", () -> Calibration.ROTATE_TARGET_A * 2 -Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TARGET_A * 2 + Calibration.ROTATE_TOLERANCE);
            add("South Neg", () -> Calibration.ROTATE_TARGET_A * -2 -Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < -Calibration.ROTATE_TARGET_A * 2 + Calibration.ROTATE_TOLERANCE);
            add("NorthWest", () -> -Calibration.ROTATE_TARGET_B-Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < -Calibration.ROTATE_TARGET_B + Calibration.ROTATE_TOLERANCE);
            add("NorthEast", () -> Calibration.ROTATE_TARGET_B-Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TARGET_B + Calibration.ROTATE_TOLERANCE);

            add("Left Target", () -> -Calibration.ROTATE_TURN_TARGET-Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < -Calibration.ROTATE_TURN_TARGET + Calibration.ROTATE_TOLERANCE);
            add("Right Target", () -> Calibration.ROTATE_TURN_TARGET-Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TURN_TARGET + Calibration.ROTATE_TOLERANCE);

        }});

        this.set(new ElasticController() {{
            addDefault("Off", new Action() {
            	public void begin (ActionData data) {
            		timer.reset();
            		timer.start();
            	}
            	
                public void run (ActionData data) {
                    drive.tankDrive(0, 0);
                }
                
                public void end (ActionData data) {
                	timer.stop();
                	timer.reset();
                }
            });
            
            add("Tank Drive", new Action(new FieldMap () {{
                define("Left Power", 0D);
                define("Right Power", 0D);
                define("Gear Ratio", 1D);
            }}) {
                public void run (ActionData data) {
                	drive.tankDrive(data.get("Left Power")*data.get("Gear Ratio"), data.get("Right Power")*data.get("Gear Ratio"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            
            add("Kinematic Drive", new Action(new FieldMap () {{
                define("Power", 0D);
                define("Time", 0D);
            }}) {
            	public void begin (ActionData data) {
            		timer.reset();
            		timer.start();
            		timerWait = data.get("Time");
            		timerOn = true;
            	}
            	
                public void run (ActionData data) {
                	if (timer.get() < data.get("Time")) {
                		drive.tankDrive(data.get("Power"), data.get("Power"), false);
                	}
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                    
                    timer.stop();
                    timer.reset();
                    
                    timerWait = 0;
                    timerOn = false;
                }
            });
            
            add("Kinematic Rotate", new Action(new FieldMap () {{
                define("Power", 0D);
                define("Time", 0D);
            }}) {
            	public void begin (ActionData data) {
            		timer.reset();
            		timer.start();
            		timerOn = true;
            	}
            	
                public void run (ActionData data) {
                	if (timer.get() < data.get("Time")) {
                		drive.tankDrive(data.get("Power"), -data.get("Power"), false);
                	}
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                    
                    timerOn = false;
                    timerWait = 0;
                    timer.stop();
                    timer.reset();
                }
            });
            
            add("Arcade Drive", new Action(new FieldMap () {{
                define("Move Power", 0D);
                define("Rotate Power", 0D);
                define("Gear Ratio", 1D);
            }}) {
                public void run (ActionData data) {
                	drive.arcadeDrive(data.get("Move Power")*data.get("Gear Ratio"), data.get("Rotate Power")*data.get("Gear Ratio"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });

            /*
            add("Straight Drive", new Action(new FieldMap () {{
                define("Move Power", 0D);
            }}) {
                public void run (ActionData data) {
                    // double throttle = data.is("Throttled") ? 0.5 : 1;
//                	System.out.print("Move");
//                	System.out.println(data.get("Move Power"));
//                	System.out.print("Rotate");
//                	System.out.println(data.get("Rotate Power"));
                	drive.arcadeDrive(data.get("Move Power"), -horizGyro.getAngle());
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            */
            
            add("Servo Move", new Action(new FieldMap() {{
                define("Clicks", 0D);
                define("Limit", Calibration.DRIVE_MOVE_PID_MAX);
            }}) {
                public void begin (ActionData data) {
                	/* Get current value */
                	pidMove.setOutputRange(-data.get("Limit"), data.get("Limit"));
                    encoderLeft.reset();
                    encoderRight.reset();
                    //horizGyro.reset();
                    
                    pidMove.setSetpoint(data.get("Clicks"));
                    pidMove.enable();
                    //pidRotate.setSetpoint(0);
                    //pidRotate.enable();
                }
                
                public void run (ActionData data){
                    if (pidMove.getSetpoint() != data.get("Clicks")) {
                        pidMove.reset();
                        
                        encoderLeft.reset();
                        encoderRight.reset();
                        
                        pidMove.setSetpoint(data.get("Clicks"));
                        pidMove.enable();
                    }
                    /*
                    if (pidRotate.getSetpoint() != 0) {
                        pidRotate.reset();
                        
                        horizGyro.reset();
                        
                        pidRotate.setSetpoint(0);
                        pidRotate.enable();
                    }
                    */
                }
                
                public void end (ActionData data) {
                    pidMove.reset();
                    pidMove.disable();
                    /* Should be normal default; pidMove does not have getter for output range */
                    pidMove.setOutputRange(-Calibration.DRIVE_MOVE_PID_MAX, Calibration.DRIVE_MOVE_PID_MAX);
                    //pidRotate.reset();
                }
            });
            
            add("Servo Rotate", new Action(new FieldMap() {{
                define("Angle", 0D);
            }}) {
                public void begin (ActionData data) {
                    horizGyro.reset();
                    
                    pidRotate.setSetpoint(data.get("Angle"));
                    pidRotate.enable();
                }
                
                public void run (ActionData data){
                    if (pidRotate.getSetpoint() != data.get("Angle")) {
                        pidRotate.reset();
                        
                        horizGyro.reset();

                        pidRotate.setSetpoint(data.get("Angle"));
                        pidRotate.enable();
                    }
                }
                
                public void end (ActionData data) {
                    pidRotate.reset();
                }
            });
            add("Calibrate", new Action() {
            	public void begin (ActionData data) {
            		reset = false;
            		encoderLeft.reset();
            		encoderRight.reset();
            		horizGyro.reset();
            	}
            	public void end (ActionData data) {
            		reset = true;
            	}
            });
            
            add("Manual Rotate Right", new Action(new FieldMap () {{
            	define("Power", 0D);
            }}) {
            	public void run (ActionData data) {
            		drive.arcadeDrive(0, -data.get("Power"));
            	}
            });
            
            add("Manual Rotate Left", new Action(new FieldMap () {{
            	define("Power", 0D);
            }}) {
            	// begin: calibrate gyro
            	public void run (ActionData data) {
            		drive.arcadeDrive(0, data.get("Power"));
            	}
            });
        }});
    }
}