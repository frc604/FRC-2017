package com._604robotics.robotnik.prefabs.devices;

import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.Compressor;

public class Regulator extends Module {
    private final Compressor compressor;

    public Regulator (int port) {
    	compressor = new Compressor(port);
    	/* Shouldn't technically be necessary */
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
