package com._604robotics.robotnik.trigger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A map containing triggers.
 */
public class TriggerMap implements Iterable<Map.Entry<String, Trigger>> {
    private final Map<String, Trigger> triggerTable = new HashMap<String, Trigger>();
    
    /**
     * Adds a trigger.
     * @param name Name of the trigger.
     * @param trigger Trigger to add.
     */
    protected void add (String name, Trigger trigger) {
        this.triggerTable.put(name, trigger);
    }

    /**
     * Gets the size of the trigger map.
     * @return The trigger map's size.
     */
    public int size() {
    	return this.triggerTable.size();
    }
    
    @Override
    public Iterator<Map.Entry<String, Trigger>> iterator () {
        return this.triggerTable.entrySet().iterator();
    }
}
