package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.Trigger;

/**
 * A toggled trigger, with an on and off state.
 */
public class TriggerToggle {
    private final Trigger trigger;
    private boolean last = false;
    private boolean state;

    private class TriggerState implements Trigger {
        private final boolean which;

        public TriggerState (boolean which) {
            this.which = which;
        }
        
        @Override
        public boolean get () {
            update();
            return state == this.which;
        }
    }

    /**
     * Trigger for the off state.
     */
    public final Trigger off = new TriggerState(false);
    
    /**
     * Trigger for the on state.
     */
    public final Trigger on  = new TriggerState(true);

    /**
     * Creates a trigger toggle.
     * @param trigger Trigger to toggle the state.
     * @param defaultValue Default value of the toggle.
     */
    public TriggerToggle (Trigger trigger, boolean defaultValue) {
        this.trigger = trigger;
        this.state = defaultValue;
    }

    private void update () {
        final boolean now = this.trigger.get();
        
        if (!this.last && now) {
            this.state = !this.state;
        }
        
        this.last = now;
    }
}