package com._604robotics.robot2017;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robot2017.modes.AutonomousMode;
import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robot2017.modules.Climber;
import com._604robotics.robot2017.modules.Dashboard;
import com._604robotics.robot2017.modules.Drive;
import com._604robotics.robot2017.modules.DynamicToggle;
import com._604robotics.robot2017.systems.DashboardSystem;
import com._604robotics.robot2017.systems.DynamicDriveSystem;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.CoordinatorList;
import com._604robotics.robotnik.coordinator.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;
import com._604robotics.robotnik.prefabs.devices.Regulator;

public class Robot2017 extends Robot {
    public Robot2017() {
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});

        this.set(new ModuleMap() {{
        	add("Climber", new Climber());
        	add("Dashboard", new Dashboard());
        	add("Drive", new Drive());
        	add("DynamicToggle", new DynamicToggle());
        }});

        this.set(new CoordinatorList() {{
            add(new DashboardSystem());
            add(new DynamicDriveSystem());
        }});
    }
}
