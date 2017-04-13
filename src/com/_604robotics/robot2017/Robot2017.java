package com._604robotics.robot2017;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robot2017.modes.AutonomousMode;
import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robot2017.modules.Activator;
import com._604robotics.robot2017.modules.Arbitrary;
import com._604robotics.robot2017.modules.Boop;
import com._604robotics.robot2017.modules.Dashboard;
import com._604robotics.robot2017.modules.Drive;
import com._604robotics.robot2017.modules.DynamicToggle;
import com._604robotics.robot2017.modules.RumbleControl;
import com._604robotics.robot2017.modules.Vision;
import com._604robotics.robot2017.modules.XboxFlip;
import com._604robotics.robot2017.systems.DashboardSystem;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.CoordinatorList;
import com._604robotics.robotnik.coordinator.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;
import com._604robotics.robotnik.prefabs.modules.Regulator;

public class Robot2017 extends Robot {
    public Robot2017() {
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});

        this.set(new ModuleMap() {{
        	add("Regulator", new Regulator(Ports.COMPRESSOR));
        	add("Boop", new Boop());
            add("Dashboard", new Dashboard());
            add("Drive", new Drive());
            add("DynamicToggle", new DynamicToggle());
            add("XboxFlip", new XboxFlip());
            add("Vision", new Vision());
            add("RumbleControl", new RumbleControl());
            add("Activator", new Activator());
            add("Arbitrary", new Arbitrary());
        }});

        this.set(new CoordinatorList() {{
            add(new DashboardSystem());
        }});
    }
}
