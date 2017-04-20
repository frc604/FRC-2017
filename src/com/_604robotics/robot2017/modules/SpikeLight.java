package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class SpikeLight extends Module {
	private final Relay light = new Relay(Ports.SPIKELIGHT);
	private DigitalInput pew1 = new DigitalInput(Ports.PEW);

	public SpikeLight() {
		this.set(new TriggerMap() {{
			add("Ping", () -> !pew1.get() );
		}});
		
		this.set(new ElasticController() {{
			addDefault("Off", new Action() {
				public void begin (ActionData data) {
					if( pew1.get() ) {
						light.set(Value.kOn);
					} else {
						light.set(Value.kOff);
					}
				}
			});
			
			add("On", new Action() {
				public void begin (ActionData data) {
					light.set(Value.kForward);
				}
			});
		}});
	}
}
