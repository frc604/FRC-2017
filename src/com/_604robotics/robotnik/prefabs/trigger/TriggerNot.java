package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.Trigger;

/**
 * A trigger that outputs the inverse of another.
 */
public class TriggerNot implements Trigger {
    private final Trigger trigger;

    /**
     * Creates a trigger not.
     * @param trigger Trigger to invert.
     */
    public TriggerNot (Trigger trigger) {
        this.trigger = trigger;
    }
    
    @Override
    public boolean get () {
        return !this.trigger.get();
    }
}