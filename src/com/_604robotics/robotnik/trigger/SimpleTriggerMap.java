package com._604robotics.robotnik.trigger;

import java.util.HashMap;

public class SimpleTriggerMap {
	private HashMap<String, Trigger> triggerMap;
	private HashMap<String, Boolean> booleanMap;
	
	public class SimpleTrigger implements Trigger {
		private boolean state;
		private String key;
		public SimpleTrigger(String key) {
			this.key = key;
			state = false;
		}
		public boolean get() {
			update();
			return state;
		}
		public void update() {
			this.state = booleanMap.get(key);
		}
	}
	public SimpleTriggerMap() {
		triggerMap = new HashMap<String, Trigger>();
		booleanMap = new HashMap<String, Boolean>();
	}
	public void add( String key ) {
		booleanMap.put(key, false);
		triggerMap.put(key, new SimpleTrigger(key));
	}
	public void set( String key, boolean value ) {
		booleanMap.put(key, value);
	}
	public boolean getBoolean( String key ) {
		return booleanMap.get(key);
	}
	public Trigger getTrigger( String key ) {
		return triggerMap.get(key);
	}
	
}
