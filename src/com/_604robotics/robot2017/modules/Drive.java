package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.ArcadeDrivePIDOutput;
import com._604robotics.robotnik.prefabs.devices.TankDrivePIDOutput;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
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
	
    private final Encoder encoderLeft = new Encoder(
            Ports.DRIVE_ENCODER_LEFT_A,
            Ports.DRIVE_ENCODER_LEFT_B,
            true, CounterBase.EncodingType.k4X);
    private final Encoder encoderRight = new Encoder(
            Ports.DRIVE_ENCODER_RIGHT_A,
            Ports.DRIVE_ENCODER_RIGHT_B,
            false, CounterBase.EncodingType.k4X);

    private final ArcadeDrivePIDOutput pidOutput = new ArcadeDrivePIDOutput(drive);
    
	private final AnalogGyro horizGyro = new AnalogGyro(Ports.HORIZONTAL_GYRO);
    
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
    private final Timer timer = new Timer();

    public Drive () {
        encoderLeft.setPIDSourceType(PIDSourceType.kDisplacement);
        encoderRight.setPIDSourceType(PIDSourceType.kDisplacement);
        pidMove.setOutputRange(-Calibration.DRIVE_MOVE_PID_MAX, Calibration.DRIVE_MOVE_PID_MAX);
        pidRotate.setOutputRange(-Calibration.DRIVE_ROTATE_PID_MAX, Calibration.DRIVE_ROTATE_PID_MAX);
        horizGyro.calibrate();
        pidMove.setAbsoluteTolerance(Calibration.DRIVE_MOVE_PID_TOLERANCE);
        pidRotate.setAbsoluteTolerance(Calibration.DRIVE_ROTATE_PID_TOLERANCE);

        SmartDashboard.putData("Drive Move PID", pidMove);
        SmartDashboard.putData("Drive Rotate PID", pidRotate);

        this.set(new DataMap() {{
            add("Left Drive Clicks", encoderLeft::get);
            add("Right Drive Clicks", encoderRight::get);

            add("Left Drive Rate", encoderLeft::getRate);
            add("Right Drive Rate", encoderRight::getRate);

            add("Move PID Error", pidMove::getAvgError);
            add("Rotate PID Error", pidRotate::getAvgError);
            
            add("Horizonal Gyro Angle", horizGyro::getAngle);

        }});

        this.set(new TriggerMap() {{
            add("At Move Servo Target", () ->
                pidMove.isEnabled() && pidMove.onTarget());
            add("At Rotate Servo Target", () ->
                pidRotate.isEnabled() && pidRotate.onTarget());
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
            /*
            add("Stick Drive", new Action(new FieldMap () {{
                define("throttle", 0D);
                define("turn", 0D);
            }}) {
                public void run (ActionData data) {
                    // double throttle = data.is("Throttled") ? 0.5 : 1;
                	drive.arcadeDrive(data.get("throttle"), data.get("turn"));
                }

                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            */
            /*
            add("Servo Move", new Action(new FieldMap() {{
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
            });
            */
            add("Servo Move", new Action(new FieldMap() {{
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
                    if (encoderLeft.get() != data.get("Clicks")) {
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
                    	horizGyro.reset();
                        pidRotate.reset();

                        pidRotate.setSetpoint(data.get("Angle"));
                        pidRotate.enable();
                    }

                }

                public void end (ActionData data) {
                    pidRotate.reset();
                }
            });
        }});
    }
}