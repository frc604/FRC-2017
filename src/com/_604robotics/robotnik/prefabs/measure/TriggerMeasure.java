package com._604robotics.robotnik.prefabs.measure;

import com._604robotics.robotnik.coordinator.steps.Measure;
import com._604robotics.robotnik.trigger.Trigger;

/**
 * Measures a trigger, reporting completion when triggered.
 */
public class TriggerMeasure extends Measure {
    private final Trigger trigger;

    /**
     * Creates a trigger measure.
     * @param trigger Trigger to measure.
     */
    public TriggerMeasure(Trigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public boolean complete () {
        return trigger.get();
    }
}
