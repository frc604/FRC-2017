package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DriverStation;

public class GameMode extends Module {
    public GameMode () {
        set(new TriggerMap() {{
            add("Disabled", () -> DriverStation.getInstance().isDisabled());
            add("Autonomous", () -> DriverStation.getInstance().isAutonomous());
            add("Teleop", () -> DriverStation.getInstance().isOperatorControl());
        }});
    }
}