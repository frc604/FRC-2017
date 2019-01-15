package com._604robotics.robot2017;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robot2017.modes.AutonomousMode;
import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robot2017.modules.Climber;
import com._604robotics.robot2017.modules.Dashboard;
import com._604robotics.robot2017.modules.Drive;
import com._604robotics.robot2017.modules.DynamicToggle;
import com._604robotics.robot2017.modules.Intake;
import com._604robotics.robot2017.modules.PrototypeSolenoid;
import com._604robotics.robot2017.modules.RumbleControl;
import com._604robotics.robot2017.modules.SpikeLight;
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
            add("RumbleControl", new RumbleControl());
            add("ProtoypeSolenoid", new PrototypeSolenoid(Ports.PROTOTYPE_SOLENOID_FORWARD, Ports.PROTOTYPE_SOLENOID_REVERSE));
            add("ProtoypeSolenoid_1", new PrototypeSolenoid(Ports.PROTOTYPE_SOLENOID_1_FORWARD, Ports.PROTOTYPE_SOLENOID_1_REVERSE));
        	//add("FlipFlop", new FlipFlop());
        	//add("Intake", new Intake());
            //add("Climber", new Climber());
            add("Dashboard", new Dashboard());
            add("Drive", new Drive());
            add("DynamicToggle", new DynamicToggle());
            //add("GearShifter", new GearShifter(Ports.SHIFTER_SOLENOID_FORWARD, Ports.SHIFTER_SOLENOID_REVERSE));
            add("Regulator", new Regulator(Ports.COMPRESSOR));
            //add("Vision", new Vision());
            add("XboxFlip", new XboxFlip());
            add("SpikeLight", new SpikeLight());
        }});

        this.set(new CoordinatorList() {{
            add(new DashboardSystem());
        }});
    }
}
