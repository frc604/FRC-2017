package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;

public class RumbleControl extends Module {
	public RumbleControl() {
		this.set(new ElasticController() {{
			addDefault("Off", new Action() {{
				
			}});
			
			add("On", new Action(new FieldMap () {{
				define("High Power Rumble", 0D);
				define("Low Power Rumble", 0D);
			}}) {
				public void begin(ActionData data) {
					TeleopMode.testing.rumble.sendData("enable", 0);
				}
				public void run (ActionData data) {
					TeleopMode.testing.rumble.sendData("high rumble", data.get("Low Power Rumble")); // on purpose
					TeleopMode.testing.rumble.sendData("low rumble", data.get("High Power Rumble"));
				}
			});
		}});
	}
}
