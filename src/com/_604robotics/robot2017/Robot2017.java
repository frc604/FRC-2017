package com._604robotics.robot2017;

import com._604robotics.robot2017.modes.AutonomousMode;
import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robot2017.modules.Dashboard;
import com._604robotics.robot2017.modules.Drive;
import com._604robotics.robot2017.modules.DynamicToggle;
import com._604robotics.robot2017.modules.Vision;
import com._604robotics.robot2017.modules.XboxFlip;
import com._604robotics.robot2017.systems.DashboardSystem;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.CoordinatorList;
import com._604robotics.robotnik.coordinator.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;

public class Robot2017 extends Robot {
    public Robot2017() {
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});

        this.set(new ModuleMap() {{
            add("Dashboard", new Dashboard());
            add("Drive", new Drive());
            add("DynamicToggle", new DynamicToggle());
            add("XboxFlip", new XboxFlip());
            add("Vision", new Vision());
        }});

        this.set(new CoordinatorList() {{
            add(new DashboardSystem());
        }});
    }
}
