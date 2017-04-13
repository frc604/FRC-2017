/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Squea
 */
public class ManualRegulator extends Module {
    private final Relay compressorA = new Relay(Ports.COMPRESSOR);

    public ManualRegulator () {
        this.set(new ElasticController () {{
            addDefault("Idle", new Action() {
                public void begin (ActionData data) {
                    compressorA.set(Relay.Value.kOff);
                    // compressor.start();
                }
            });
            
            add("Charge", new Action() {
                public void begin (ActionData data) {
                        compressorA.set(Relay.Value.kOn);
                        // compressor.stop();
                }
            });
        }});
    }
}
