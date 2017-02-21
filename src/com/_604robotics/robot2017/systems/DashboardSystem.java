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

            this.fill(new DataWire(DashboardOutput.asDouble(), "Ultra Inches",
                	modules.getModule("Drive").getData("Ultra Inches")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Ultra Angle",
                	modules.getModule("Drive").getData("Ultra Angle")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Ultra Difference",
                	modules.getModule("Drive").getData("Ultra Difference")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Ultra Left",
                	modules.getModule("Drive").getData("Ultra Left")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Ultra Right",
                	modules.getModule("Drive").getData("Ultra Right")));
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Past Ultra Target",
                	modules.getModule("Drive").getTrigger("Past Ultra Target")));

            this.fill(new DataWire(DashboardOutput.asDouble(), "Horizontal Gyro Angle",
                	modules.getModule("Drive").getData("Horizontal Gyro Angle")));
            
            this.fill(new DataWire(DashboardOutput.asDouble(), "Timer Seconds",
                	modules.getModule("Drive").getData("Timer Seconds")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Xbox Flipped",
            		modules.getModule("XboxFlip").getTrigger("Xbox Flipped")));
            
        }
    }
}
