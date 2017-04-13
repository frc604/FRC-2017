package com._604robotics.robot2017.systems;

import com._604robotics.robot2017.modes.TeleopMode;
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

            this.fill(new DataWire(DashboardOutput.asDouble(), "Horizontal Gyro Angle",
                	modules.getModule("Drive").getData("Horizontal Gyro Angle")));
            
            this.fill(new DataWire(DashboardOutput.asDouble(), "Timer Seconds",
                	modules.getModule("Drive").getData("Timer Seconds")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Xbox Flipped",
            		modules.getModule("XboxFlip").getTrigger("Xbox Flipped")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Extended",
            		modules.getModule("FlipFlop").getTrigger("Extended")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Running",
            		modules.getModule("Intake").getTrigger("Running")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Boop1",
            		modules.getModule("Intake").getTrigger("Boop1")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Boop2",
            		modules.getModule("Intake").getTrigger("Boop2")));
        }
    }
}
