package com._604robotics.robotnik.data.sources;

import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.trigger.Trigger;

/**
 * Data supplied by a trigger.
 */
public class DataTriggerAdaptor implements Data {
    private final Trigger trigger;
    
    /**
     * Creates a data trigger adaptor.
     * @param trigger Trigger to supply data with.
     */
    public DataTriggerAdaptor (Trigger trigger) {
        this.trigger = trigger;
    }
    
    @Override
    public double get () {
        return this.trigger.get() ? 1D : 0D;
    }
}
