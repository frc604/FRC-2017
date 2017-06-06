package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

public class XboxFlip extends Module {
	private boolean isFlipped = false;
	
	public XboxFlip() {
		this.set(new TriggerMap() {{
			add("Xbox Flipped", () -> isFlipped);
		}});
		
		this.set(new ElasticController() {{
			addDefault("Off", new Action());
			add("Flip", new Action() {
				public void begin(ActionData data) {
					isFlipped = !isFlipped;
					TeleopMode.driver.leftStick.X.flipFactor();
					TeleopMode.driver.leftStick.Y.flipFactor();
					TeleopMode.driver.rightStick.Y.flipFactor();
					TeleopMode.override.leftStick.X.flipFactor();
					TeleopMode.override.leftStick.Y.flipFactor();
					TeleopMode.override.rightStick.Y.flipFactor();
				}
			});
		}});
	}
}
