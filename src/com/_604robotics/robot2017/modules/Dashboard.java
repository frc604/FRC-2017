package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Calibration;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.data.sources.DashboardData;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
    	this.set(new DataMap() {{
    		add("Forward Time", new DashboardData("Forward Time", Calibration.FORWARD_TIME));
    		add("Forward Power", new DashboardData("Forward Power", Calibration.FORWARD_POWER));
    		add("Turning Time", new DashboardData("Turning Time", Calibration.TURNING_TIME));
    	}});
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveOn = new DashboardTriggerChoice("Drive On");
            add("Drive On", driveOn.addDefault("Drive On"));
            add("Drive Off", driveOn.add("Drive Off"));
            
            final DashboardTriggerChoice autonMode = new DashboardTriggerChoice("Auton Mode");
            add("Center Peg", autonMode.addDefault("Center Peg"));
            add("Left Peg", autonMode.add("Left Peg"));
            add("Right Peg", autonMode.add("Right Peg"));
            add("Turn Right", autonMode.add("Turn Right"));
            add("Turn Left", autonMode.add("Turn Left"));
            
            final DashboardTriggerChoice autonOn = new DashboardTriggerChoice("Auton Switch");
            add("Auton On", autonOn.addDefault("Auton On"));
            add("Auton Off", autonOn.add("Auton Off"));
            
            final DashboardTriggerChoice debugOn = new DashboardTriggerChoice("Debug On");
            add("Debug Off", debugOn.addDefault("Debug Off"));
            add("Debug On", debugOn.add("Debug On"));  
            
            final DashboardTriggerChoice driveMode = new DashboardTriggerChoice("Drive Mode");
            add("Dynamic Drive", driveMode.addDefault("Dynamic Drive"));
            add("Toggle Drive", driveMode.add("Toggle Drive"));
            add("Tank Drive", driveMode.add("Tank Drive"));
            add("Arcade Drive", driveMode.add("Arcade Drive"));
            add("Ultra Drive", driveMode.add("Ultra Drive"));
        }});
    }
}
