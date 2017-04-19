package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DigitalInput;

public class Blinker extends Module {
	private DigitalInput pew1 = new DigitalInput(Ports.PEW);
	
	public Blinker() {
		this.set(new TriggerMap() {{
			add("Ping", () -> !pew1.get() );
		}});
		this.set(new StateController() {{
			addDefault("Running", new Action() {
				public void run(ActionData data) {
					
				}
			});
		}});
		
	}
}
