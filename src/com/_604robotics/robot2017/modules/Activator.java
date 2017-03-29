package com._604robotics.robot2017.modules;

import java.util.HashMap;

import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robotnik.trigger.SimpleTriggerMap;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;

public class Activator extends Module {
	private int step;
	private boolean testBoolean;
	
	private SimpleTriggerMap stm;
	
	public Activator() {
		stm = new SimpleTriggerMap();
		step = 0;
		testBoolean = true;
		stm.add("Test");
		this.set(new TriggerMap() {{
			add("Test Trigger", stm.getTrigger("Test"));
			add("Step One", () -> step == 1);
			add("Step Two", () -> step == 2);
			add("Step Three", () -> step == 3);
		}});
		
		this.set(new StateController() {{
			addDefault("Step Three Finished", new Action() {
				public void begin(ActionData data) {
					step = 0;
				}
			});
			add("Initiate", new Action() {
				public void begin(ActionData data) {
					step = 1;
				}
			});
			add("Step One Finished", new Action() {
				public void begin(ActionData data) {
					step = 2;
				}
			});
			add("Step Two Finished", new Action() {
				public void begin(ActionData data) {
					step = 3;
				}
			});
		}});
	}
}
