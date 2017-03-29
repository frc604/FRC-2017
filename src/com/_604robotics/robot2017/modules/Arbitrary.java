package com._604robotics.robot2017.modules;

import java.util.HashMap;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.SimpleTriggerMap;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;

public class Arbitrary extends Module {
	private int complete;
	
	private SimpleTriggerMap stm;
	
	public Arbitrary() {
		stm = new SimpleTriggerMap();
		complete = 0;
		stm.add("Test");
		
		this.set(new TriggerMap() {{
			add("Test Trigger", stm.getTrigger("Test"));
			add("Complete One", () -> complete == 1);
			add("Complete Two", () -> complete == 2);
			add("Complete Three", () -> complete == 3);
		}});

		this.set(new ElasticController() {{
			addDefault("Reset", new Action() {
				public void begin(ActionData data) {
					System.out.println("Actions Reset.");
					complete = 0;
				}
			});
			add("Step One", new Action() {
				public void begin(ActionData data) {
					System.out.println("Action One called.");
				}
				public void run(ActionData data) {
					System.out.println("Action One running. Should only print once.");
					complete = 1;
				}
				public void end(ActionData data) {
					System.out.println("Action One complete.");
				}
			});
			add("Step Two", new Action() {
				public void begin(ActionData data) {
					System.out.println("Action Two called.");
				}
				public void run(ActionData data) {
					System.out.println("Action Two running. Should only print once.");
					complete = 2;
				}
				public void end(ActionData data) {
					System.out.println("Action Two complete.");
				}
			});
			add("Step Three", new Action() {
				public void begin(ActionData data) {
					System.out.println("Action Three called.");
				}
				public void run(ActionData data) {
					System.out.println("Action Three running. Should only print once.");
					complete = 3;
				}
				public void end(ActionData data) {
					System.out.println("Action Three complete.");
				}
			});
			add("Test", new Action() {
				public void run(ActionData data) {
					System.out.println("Still testing");
				}
			});
		}});
	}
}
