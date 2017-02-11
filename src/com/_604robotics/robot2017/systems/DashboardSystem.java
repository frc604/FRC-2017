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
            this.fill(new DataWire(DashboardOutput.asDouble(), "Inches",
           		 modules.getModule("Drive").getData("Inches")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Compressor Current",
            	modules.getModule("Regulator").getData("Compressor Current")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Inches",
                	modules.getModule("Drive").getData("Inches")));
            this.fill(new DataWire(DashboardOutput.asDouble(), "Ultra Angle",
                	modules.getModule("Drive").getData("Ultra Angle")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Past Ultra Target",
            	modules.getModule("Drive").getTrigger("Past Ultra Target")));
            
            this.fill(new DataWire(DashboardOutput.asBoolean(), "Boop",
            	modules.getModule("Climber").getTrigger("Boop")));
        }
    }
}
