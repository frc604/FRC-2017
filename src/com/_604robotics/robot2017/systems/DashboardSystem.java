package com._604robotics.robot2017.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.outputs.DashboardOutput;

public class DashboardSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        /* Drive */
        {
            this.bind(new Binding(modules.getModule("Drive").getAction("Off"), modules.getModule("Dashboard").getTrigger("Drive Off"), true));
            
            this.fill(new DataWire(DashboardOutput.asDouble(), "Left Drive Clicks",
                    modules.getModule("Drive").getData("Left Drive Clicks")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Right Drive Clicks",
                    modules.getModule("Drive").getData("Right Drive Clicks")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Left Drive Rate",
                    modules.getModule("Drive").getData("Left Drive Rate")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Right Drive Rate",
                    modules.getModule("Drive").getData("Right Drive Rate")));

            this.fill(new DataWire(DashboardOutput.asBoolean(), "At Rotate Servo Target",
                    modules.getModule("Drive").getTrigger("At Rotate Servo Target")));
            
            this.fill(new DataWire(DashboardOutput.asDouble(), "Horizontal Gyro Angle",
                	modules.getModule("Drive").getData("Horizontal Gyro Angle")));
            
            this.fill(new DataWire(DashboardOutput.asDouble(), "Timer Seconds",
                	modules.getModule("Drive").getData("Timer Seconds")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Xbox Flipped",
            		modules.getModule("XboxFlip").getTrigger("Xbox Flipped")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Extended",
                        modules.getModule("ProtoypeSolenoid").getTrigger("Extended")));

            this.fill(new DataWire(DashboardOutput.asBoolean(), "Extended_1",
                            modules.getModule("ProtoypeSolenoid_1").getTrigger("Extended_1")));
                            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Running",
            		modules.getModule("Intake").getTrigger("Running")));
            
            this.fill(new DataWire(DashboardOutput.asDouble(), "Left Drive Accel",
                    modules.getModule("Drive").getData("Left Drive Accel")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Right Drive Accel",
                    modules.getModule("Drive").getData("Right Drive Accel")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Gyro Calibrated",
            		modules.getModule("Drive").getTrigger("Gyro Calibrated")));
            /*
            this.fill(new DataWire(DashboardOutput.asBoolean(), "L3",
            		modules.getModule("Drive").getTrigger("L3")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "L2",
            		modules.getModule("Drive").getTrigger("L2")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "L1",
            		modules.getModule("Drive").getTrigger("L1")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "L0",
            		modules.getModule("Drive").getTrigger("L0")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "CC",
            		modules.getModule("Drive").getTrigger("CC")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "R0",
            		modules.getModule("Drive").getTrigger("R0")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "R1",
            		modules.getModule("Drive").getTrigger("R1")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "R2",
            		modules.getModule("Drive").getTrigger("R2")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "R3",
            		modules.getModule("Drive").getTrigger("R3")));
            */
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Ping",
            		modules.getModule("SpikeLight").getTrigger("Ping")));
        }
    }
}
