package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.Trigger;

/**
 * A trigger based on whether all of multiple triggers are active.
 */
public class TriggerAnd implements Trigger {
    private final Trigger[] triggers;

    /**
     * Creates a trigger and.
     * @param triggers Triggers to use.
     */
    public TriggerAnd (Trigger... triggers) {
        this.triggers = triggers;
    }
    
    @Override
    public boolean get () {
        boolean value = true;
        
        for (int i = 0; i < this.triggers.length; i++) {
            value = this.triggers[i].get() && value;
        }
        
        return value;
    }
}