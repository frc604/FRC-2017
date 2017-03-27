package com._604robotics.robotnik.trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com._604robotics.robotnik.Safety;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.utils.FuzzyMatcher;

/**
 * Manages triggers.
 */
public class TriggerManager {
    private final Map<String, TriggerReference> triggerTable;
    
    /**
     * Creates a trigger manager.
     * @param triggerMap Map of triggers to manage.
     * @param table Table to store trigger data in.
     */
    public TriggerManager (TriggerMap triggerMap, final IndexedTable table) {
        this.triggerTable = new HashMap<String, TriggerReference>();
        for (Map.Entry<String, Trigger> entry : triggerMap) {
            this.triggerTable.put(entry.getKey(), new TriggerReference(entry.getValue(), table.getSlice(entry.getKey())));
        }
    }
    
    /**
     * Gets a reference to a trigger.
     * @param name Name of the trigger.
     * @return The retrieved trigger reference.
     */
    public TriggerReference getTrigger (String name) {
        final TriggerReference ref = this.triggerTable.get(name);
        if (ref == null) {
        	/* This will make someone #triggered */
        	System.err.println("Trigger \"" + name + "\" does not exist.");
        	ArrayList<String> matching = new ArrayList<String>();
        	for (String trigger: this.triggerTable.keySet()) {
        		if (FuzzyMatcher.match(trigger, name)<(0.3*name.length())) {
        			matching.add(trigger);
        		}
        	}
        	System.err.println("Did you mean:");
        	for (String trigmatch:matching) {
        		System.err.print("    ");
        		System.err.println(trigmatch);
        	}
        	throw new IllegalArgumentException("Trigger \"" + name + "\" does not exist.");
        }
        
        return ref;
    }
    
    /**
     * Updates all triggers.
     * @param safety Safety mode to operate with.
     */
    public void update (Safety safety) {
        for (TriggerReference ref : this.triggerTable.values()) {
            ref.update(safety);
        }
    }
}
