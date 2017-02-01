package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.Compressor;

public class Regulator extends Module {
    private final Compressor compressor = new Compressor(Ports.COMPRESSOR);

    public Regulator () {
        compressor.setClosedLoopControl(true);

        set(new DataMap() {{
            add("Compressor Current", compressor::getCompressorCurrent);
        }});

        set(new TriggerMap() {{
            add("Compressor Enabled", compressor::enabled);
            add("Pressure Switch", compressor::getPressureSwitchValue);
        }});
    }
}
