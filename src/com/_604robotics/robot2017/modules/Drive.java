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
    		Calibration.DRIVE_LEFT_PID_P,
    		Calibration.DRIVE_LEFT_PID_I,
    		Calibration.DRIVE_LEFT_PID_D,
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
        horizGyro.calibrate();	

        encoderLeft.setPIDSourceType(PIDSourceType.kDisplacement);
        encoderRight.setPIDSourceType(PIDSourceType.kDisplacement);
        
        horizGyro.setPIDSourceType(PIDSourceType.kDisplacement);
        
        /*
        pidLeft.setOutputRange(-Calibration.DRIVE_LEFT_PID_MAX, Calibration.DRIVE_LEFT_PID_MAX);
        pidRight.setOutputRange(-Calibration.DRIVE_RIGHT_PID_MAX, Calibration.DRIVE_RIGHT_PID_MAX);
        pidLeft.setAbsoluteTolerance(Calibration.DRIVE_LEFT_PID_TOLERANCE);
        pidRight.setAbsoluteTolerance(Calibration.DRIVE_RIGHT_PID_TOLERANCE);
		*/
		
        pidMove.setOutputRange(-Calibration.DRIVE_LEFT_PID_MAX, Calibration.DRIVE_LEFT_PID_MAX);
        pidMove.setAbsoluteTolerance(Calibration.DRIVE_LEFT_PID_TOLERANCE);
        
        pidRotate.setOutputRange(-Calibration.DRIVE_ROTATE_PID_MAX, Calibration.DRIVE_ROTATE_PID_MAX);
        pidRotate.setAbsoluteTolerance(Calibration.DRIVE_ROTATE_PID_TOLERANCE);

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
            
            add("Timer Seconds", timer::get);
            
            add("Horizontal Gyro Angle", horizGyro::getAngle);
        }});

        this.set(new TriggerMap() {{
            add("At Move Servo Target", () -> pidMove.isEnabled() && pidMove.onTarget());
            add("Past Ultra Target", () -> (ultra.getDistance() < Calibration.ULTRA_TARGET) && (ultra.getAngle() < 3));
            add("Aligned", () -> ultra.getAngle() < 3);
            add("Timer Setpoint", () -> timer.get() < Calibration.WAIT);
            add("At Rotate Servo Target", () -> pidRotate.isEnabled() && pidRotate.onTarget());
            add("At Rotate Manual Target A", () -> Calibration.ROTATE_TARGET_A + Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TARGET_A + Calibration.ROTATE_TOLERANCE);
            add("At Rotate Manual Target B", () -> Calibration.ROTATE_TARGET_B + Calibration.ROTATE_TOLERANCE < horizGyro.getAngle() && horizGyro.getAngle() < Calibration.ROTATE_TARGET_B + Calibration.ROTATE_TOLERANCE);
        }});

        this.set(new ElasticController() {{
            addDefault("Off", new Action() {
            	public void begin(ActionData data) {
            		timer.reset();
            		timer.start();
            	}
                public void run (ActionData data) {
                    drive.tankDrive(0, 0);
                }
                public void end(ActionData data) {
                	timer.stop();
                	timer.reset();
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
            
            add("Kinematic Rotate", new Action(new FieldMap () {{
                define("Power", 0D);
                define("Time", 0D);
            }}) {
            	public void begin(ActionData data) {
            		timer.reset();
            		timer.start();
            	}
                public void run (ActionData data) {
                	double time = timer.get();
                	if( time < data.get("Time") )
                	{
                		drive.tankDrive(data.get("Power"), -data.get("Power"), false);
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
            	public void begin(ActionData data)
            	{
            		horizGyro.calibrate();
            	}
                public void run (ActionData data) {
                	if( data.get("Rotate Power") == 0D )
                	{
                    	double angle = horizGyro.getAngle();
                    	double newPower = angle / 90;
                		drive.arcadeDrive(data.get("Move Power"), -newPower);
                	}
                	else
                	{
                		drive.arcadeDrive(data.get("Move Power"), data.get("Rotate Power"));
                	}
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
            add("Servo Rotate", new Action(new FieldMap() {{
                define("Angle", 0D);
            }}) {
                public void begin (ActionData data) {
                    horizGyro.reset();
                    pidOutput.move.pidWrite(0);
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
            
            add("Manual Rotate Right", new Action(new FieldMap () {{
            	define("Power", 0D);
            }}) {
            	public void run(ActionData data) {
            		drive.arcadeDrive(0, data.get("Power"));
            	}
            });
            add("Manual Rotate Left", new Action(new FieldMap () {{
            	define("Power", 0D);
            }}) {
            	public void run(ActionData data) {
            		drive.arcadeDrive(0, -data.get("Power"));
            	}
            });
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
            
            //changed power multiplier to distance from displacement
            add("Ultra Straight 2", new Action(new FieldMap() {{
                define("inches", 0D);
            }}) {
                public void run (ActionData data){
                    if(ultra.inRange()) {
	                	double leftDisplacement = ultra.getLeftDistance(1)- data.get("inches");
	                	double leftDistance = Math.abs(leftDisplacement);
	                	double rightDisplacement = ultra.getRightDistance(1) - data.get("inches");
	                	double rightDistance = Math.abs(rightDisplacement);
	                	
	                	double leftPower = 0;
	                	if (leftDistance > 12) {
	                		leftPower = 0.5;
	               		} else if (leftDistance > 6) {
	               			leftPower = 0.4;
	               		} else if (leftDistance > 3) {
	               			leftPower = 0.3;
	               		} else if (leftDistance > 1) {
	               			leftPower = 0.25;
	               		} else if (leftDistance > 0.5) {
	               			leftPower = 0.2;
	               		}
	                	leftPower *= Math.signum(leftDistance);
	                	
	                	double rightPower = 0;
	                	if (rightDistance > 12) {
	                		rightPower = 0.5;
	               		} else if (rightDistance > 6) {
	               			rightPower = 0.4;
	               		} else if (rightDistance > 3) {
	               			rightPower = 0.3;
	               		} else if (rightDistance > 1) {
	               			rightPower = 0.25;
	               		} else if (rightDistance > 0.5) {
	               			rightPower = 0.2;
	               		}
	                	rightPower *= Math.signum(rightDistance);
	                	
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