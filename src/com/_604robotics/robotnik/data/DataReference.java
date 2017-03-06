package com._604robotics.robotnik.data;

import com._604robotics.robotnik.Safety;
import com._604robotics.robotnik.memory.IndexedTable.Slice;

/**
 * A reference to data.
 */
public class DataReference implements Data {
    private final Data dataAcess;
    private final Slice value;
    
    /**
     * Creates a data reference.
     * @param dataAcess Data to refer to.
     * @param value Slice to store the data value in.
     */
    public DataReference (Data dataAcess, Slice value) {
        this.dataAcess = dataAcess;
        this.value = value;
    }
    
    @Override
    public double get () {
        return value.getNumber(0D);
    }
    
    /**
     * Updates the value of data.
     * @param safety Safety mode to operate with.
     */
    public void update (Safety safety) {
        safety.wrap("updating data value", () -> value.putNumber(dataAcess.get()));
    }
}
