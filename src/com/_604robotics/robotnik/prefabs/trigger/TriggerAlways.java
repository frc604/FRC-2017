package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.Trigger;

/**
 * An always-active trigger.
 */
public class TriggerAlways implements Trigger {
    private TriggerAlways () {}

    private static final Trigger INSTANCE = new TriggerAlways();

    /**
     * Gets the TriggerAlways instance.
     * @return The TriggerAlways instance.
     */
    public static Trigger getInstance () {
        return INSTANCE;
    }
    
    @Override
    public boolean get () {
        return true;
    }
}