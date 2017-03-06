package com._604robotics.robotnik.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com._604robotics.robotnik.logging.Logger;

/**
 * A map containing data.
 */
public class DataMap implements Iterable<Map.Entry<String, Data>> {
    private final Map<String, Data> dataTable = new HashMap<String, Data>();

    /**
     * Adds data to the map.
     * @param name Name of the data.
     * @param dataAcess Data to add.
     */
    protected void add (String name, Data dataAcess) {
    	if (this.dataTable.containsKey(name)) {
    		Logger.warnTrace("Overwriting data \"" + name + "\".");
    	}
    	
        this.dataTable.put(name, dataAcess);
    }

    /**
     * Gets the size of the data map.
     * @return The data map's size.
     */
    public int size () {
    	return this.dataTable.size();
    }

    @Override
    public Iterator<Map.Entry<String, Data>> iterator () {
        return this.dataTable.entrySet().iterator();
    }
}
