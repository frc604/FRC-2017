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
    		add("Testing Angle", new DashboardData("Testing Angle", Calibration.TESTING_ANGLE));
    		add("Cal One Dist", new DashboardData("Cal One Dist", Calibration.CAL_ONE_DIST));
    		add("Cal Two Dist", new DashboardData("Cal Two Dist", Calibration.CAL_TWO_DIST));
    		add("Cal Three Dist", new DashboardData("Cal Three Dist", Calibration.CAL_THREE_DIST));
    		add("Target", new DashboardData("Target", Calibration.TARGET));
    		add("Rotate Power", new DashboardData("Rotate Power", 0.8));
    		add("Rotate Time", new DashboardData("Rotate Time", 1.5));
    		add("Testing Clicks", new DashboardData("Testing Clicks", 0));
    	}});
    	
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveOn = new DashboardTriggerChoice("Drive On");
            add("Drive On", driveOn.addDefault("Drive On"));
            add("Drive Off", driveOn.add("Drive Off"));
            
            final DashboardTriggerChoice autonMode = new DashboardTriggerChoice("Auton Mode");
            add("Fail Safe", autonMode.add("Fail Safe"));
            //add("Fail Safe 2", autonMode.add("Fail Safe 2"));
            add("Testing 1", autonMode.add("Testing 1"));
            add("Blue Left Step", autonMode.add("Blue Left Step"));
            add("Blue Right Step", autonMode.add("Blue Right Step"));
            add("Red Left Step", autonMode.add("Red Left Step"));
            add("Red Right Step", autonMode.add("Red Right Step"));

            add("Mid Step", autonMode.add("Mid Step"));
            add("Test Mid", autonMode.add("Test Mid"));
            add("Wiggle Mid", autonMode.add("Wiggle Mid"));
            add("Right Step", autonMode.add("Right Step"));
            add("Testing", autonMode.add("Testing"));
            /*
            add("Cal One", autonMode.add("Cal One"));
            add("Cal Two", autonMode.add("Cal Two"));
            add("Cal Three", autonMode.add("Cal Three"));
            add("Calibrated", autonMode.add("Calibrated"));
            */
            add("Kinematic Rotate", autonMode.add("Kinematic Rotate"));
            //add("Two Gear Auto", autonMode.add("Two Gear Auto"));
            
            /*
            final DashboardTriggerChoice midModifier = new DashboardTriggerChoice("Mid Modifier");
            add("Stay", midModifier.addDefault("Stay"));
            add("Left After", midModifier.add("Left After"));
            add("Right After", midModifier.add("Right After"));
            */
            
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
