package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveOn = new DashboardTriggerChoice("Drive On");
            add("Drive On", driveOn.addDefault("Drive On"));
            add("Drive Off", driveOn.add("Drive Off"));
            
            final DashboardTriggerChoice autonMode = new DashboardTriggerChoice("Auton Mode");
            add("Fail Safe", autonMode.add("Fail Safe"));
            add("Fail Safe 2", autonMode.add("Fail Safe 2"));
            add("Left Step", autonMode.add("Left Step"));
            add("Mid Step", autonMode.add("Mid Step"));
            add("Right Step", autonMode.add("Right Step"));
            add("Manual Rotate Right", autonMode.add("Manual Rotate Right"));
            add("Manual Rotate Left", autonMode.add("Manual Rotate Left"));
            add("Servo", autonMode.add("Servo"));
            add("Servo Rotate", autonMode.add("Servo Rotate"));
            
            final DashboardTriggerChoice midModifier = new DashboardTriggerChoice("Mid Modifier");
            add("Stay", midModifier.addDefault("Stay"));
            add("Left After", midModifier.add("Left After"));
            add("Right After", midModifier.add("Right After"));
            
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
            add("Test Drive", driveMode.add("Test Drive"));
        }});
    }
}
