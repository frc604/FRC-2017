package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;

public class PseudoShifter extends Module {
	private double gearRatio;
	
	public PseudoShifter() {
		gearRatio = 1;
		this.set(new DataMap() {{
			add("Gear Ratio", () -> gearRatio);
		}});
		this.set(new ElasticController() {{
			addDefault("High Gear", new Action() {
				public void begin(ActionData data) {
					gearRatio = 1;
				}
			});
			add("Low Gear", new Action() {
				public void begin(ActionData data) {
					gearRatio = 0.5;
				}
			});
		}});
	}
	
}
