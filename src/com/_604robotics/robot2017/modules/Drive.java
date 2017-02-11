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
import com._604robotics.robotnik.prefabs.devices.TankDrivePIDOutput;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
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
	
    private final Encoder encoderLeft = new Encoder(
            Ports.DRIVE_ENCODER_LEFT_A,
            Ports.DRIVE_ENCODER_LEFT_B,
            true, CounterBase.EncodingType.k4X);
    private final Encoder encoderRight = new Encoder(
            Ports.DRIVE_ENCODER_RIGHT_A,
            Ports.DRIVE_ENCODER_RIGHT_B,
            false, CounterBase.EncodingType.k4X);

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
    
    //private final AnalogGyro horizGyro = new AnalogGyro(Ports.HORIZGYRO);
    //private final AnalogUltrasonic ultra = new AnalogUltrasonic(0);
    private final UltrasonicPair ultra = new UltrasonicPair(new AnalogUltrasonic(Ports.ULTRASONIC_LEFT), new AnalogUltrasonic(Ports.ULTRASONIC_RIGHT), Calibration.ULTRA_SEPARATION);
    
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
        pidLeft.setOutputRange(-Calibration.DRIVE_LEFT_PID_MAX, Calibration.DRIVE_LEFT_PID_MAX);
        pidRight.setOutputRange(-Calibration.DRIVE_RIGHT_PID_MAX, Calibration.DRIVE_RIGHT_PID_MAX);
        pidLeft.setAbsoluteTolerance(Calibration.DRIVE_LEFT_PID_TOLERANCE);
        pidRight.setAbsoluteTolerance(Calibration.DRIVE_RIGHT_PID_TOLERANCE);

        SmartDashboard.putData("Drive Move PID", pidLeft);
        SmartDashboard.putData("Drive Rotate PID", pidRight);

        this.set(new DataMap() {{
            add("Left Drive Clicks", encoderLeft::get);
            add("Right Drive Clicks", encoderRight::get);

            add("Left Drive Rate", encoderLeft::getRate);
            add("Right Drive Rate", encoderRight::getRate);

            add("Move PID Error", pidLeft::getAvgError);
            add("Rotate PID Error", pidRight::getAvgError);

            add("Ultra Inches", ultra::getDistance);
            add("Ultra Angle", ultra::getAngle);
            
            //add("Horizonal Gyro Angle", horizGyro::getAngle);
        }});

        this.set(new TriggerMap() {{
            add("At Move Servo Target", () -> pidLeft.isEnabled() && pidLeft.onTarget());
            add("Past Ultra Target", () -> ultra.getDistance() < 50.0);
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
            
            add("Arcade Drive", new Action(new FieldMap () {{
                define("Move Power", 0D);
                define("Rotate Power", 0D);
            }}) {
                public void run (ActionData data) {
                    // double throttle = data.is("Throttled") ? 0.5 : 1;
                	drive.arcadeDrive(data.get("Move Power"), data.get("Rotate Power"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            
            /* add("Servo Move", new Action(new FieldMap() {{
                define("Clicks", 0D);
            }}) {
                public void begin (ActionData data) {
                    encoderLeft.reset();
                    encoderRight.reset();
                    pidOutput.rotate.pidWrite(0);
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
            }); */
            
            add("Servo Move", new Action(new FieldMap() {{
                define("ClickLeft", 0D);
                define("ClickRight",0D);
            }}) {
                public void begin (ActionData data) {
                    encoderLeft.reset();
                    encoderRight.reset();
                    pidOutput.left.pidWrite(0);
                    pidOutput.right.pidWrite(0);
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
            
            add("Ultra Orient", new Action() {
            	public void run (ActionData data) {
            		if (ultra.getAngle(1) > 7) {
            			drive.tankDrive(-0.15, 0.15);
            		}
            		
            		if (ultra.getAngle(1) < 7) {
            			drive.tankDrive(0.15, -0.15);
            		}
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
                	// at half distance, half power
                	// so like
                	// > 24 inches, 0.8 power
                	// > 12 inches, 0.4 power
                	// > 6 inches, 0.2 power
                	// > 3 inches, 0.1 power
                	// > 1 inches, 0.05 power
                    
                	double displacement = ultra.getDistance(1) - data.get("inches");
                	double distance = Math.abs(displacement);
                	
                	double power = 0;
                	if (distance > 24) {
                	    power = 0.5;
                	} else if (distance > 12) {
                		power = 0.4;
                	} else if (distance > 6) {
                		power = 0.3;
                	} else if (distance > 3) {
                		power = 0.25;
                	} else if (distance > 1) {
                		power = 0.2;
                	}
                	
                	power *= Math.signum(displacement);
                	
                	if(power != 0) {
                	    drive.tankDrive(power, power);
                	} else {
                	    drive.stopMotor();
                	}
                }
                
                public void end (ActionData data) {
                	drive.stopMotor();
                }
            });
        }});
    }
}