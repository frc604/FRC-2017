package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DigitalInput;

public class Boop extends Module {
	private DigitalInput boop2;
	
	public Boop() {
		boop2 = new DigitalInput(9);
		this.set(new TriggerMap() {{
			add("Boop2", () -> boop2.get());
		}});
	}
}
