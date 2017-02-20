package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.Relay;

public class SpikeLight extends Module {

	private final Relay light = new Relay(Ports.SPIKELIGHT);
	
	public SpikeLight(){
		
		this.set(new ElasticController() {{
			addDefault("Off", new Action(){
				public void begin(ActionData data){
					light.set(Relay.Value.kOff);
				}
			});
			add("On", new Action() {
				public void begin(ActionData data) {
					light.set(Relay.Value.kForward);
				}
			});
		}});
	}

}
